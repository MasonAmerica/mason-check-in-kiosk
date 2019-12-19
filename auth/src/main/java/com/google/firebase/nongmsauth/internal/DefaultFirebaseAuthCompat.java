package com.google.firebase.nongmsauth.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.identitytoolkit.IdentityToolkit;
import com.google.api.services.identitytoolkit.IdentityToolkitRequestInitializer;
import com.google.api.services.identitytoolkit.model.IdentitytoolkitRelyingpartyVerifyPasswordRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;
import com.google.firebase.nongmsauth.FirebaseAuthCompat;
import com.google.tokenservice.TokenService;
import com.google.tokenservice.TokenServiceRequestInitializer;
import com.google.tokenservice.model.ExchangeTokenRequest;
import com.google.tokenservice.model.ExchangeTokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class DefaultFirebaseAuthCompat implements FirebaseAuthCompat {
    private static final Object LOCK = new Object();
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private final FirebaseApp app;
    private final IdentityToolkit identityService;
    private final TokenService tokenService;
    private final AuthStore store;

    private final List<IdTokenListener> listeners = new ArrayList<>();

    private Map<String, ?> currentUser;

    public DefaultFirebaseAuthCompat(@NonNull FirebaseApp app) {
        this.app = app;
        identityService = new IdentityToolkit.Builder(new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> {
                })
                .setIdentityToolkitRequestInitializer(
                        new IdentityToolkitRequestInitializer(app.getOptions().getApiKey()))
                .build();
        tokenService = new TokenService.Builder(new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> {
                })
                .setTokenServiceRequestInitializer(
                        new TokenServiceRequestInitializer(app.getOptions().getApiKey()))
                .build();
        store = new AuthStore(app);

        currentUser = store.restore();
    }

    @NonNull
    @Override
    public Task<GetTokenResult> getAccessToken(boolean forceRefresh) {
        return maybeRefreshToken(forceRefresh);
    }

    @Nullable
    @Override
    public String getUid() {
        return currentUser == null ? null : String.valueOf(currentUser.get("user_id"));
    }

    @Override
    public void addIdTokenListener(@NonNull IdTokenListener listener) {
        synchronized (LOCK) {
            listeners.add(listener);
        }
        maybeRefreshToken(/* forceRefresh= */ false);
    }

    @Override
    public void removeIdTokenListener(@NonNull IdTokenListener listener) {
        synchronized (LOCK) {
            listeners.remove(listener);
        }
    }

    @NonNull
    @Override
    public Task<?> signInWithEmailAndPassword(@NonNull String email, @NonNull String password) {
        return Tasks.call(EXECUTOR, () -> {
            IdentitytoolkitRelyingpartyVerifyPasswordRequest request =
                    new IdentitytoolkitRelyingpartyVerifyPasswordRequest();

            request.setEmail(email);
            request.setPassword(password);
            request.setReturnSecureToken(true);

            return identityService.relyingparty()
                    .verifyPassword(request)
                    .setKey(app.getOptions().getApiKey())
                    .execute();
        }).continueWithTask((emailTask) ->
                refreshToken(emailTask.getResult().getRefreshToken()));
    }

    private Task<GetTokenResult> maybeRefreshToken(boolean forceRefresh) {
        return Tasks.call(EXECUTOR, () -> {
            if (currentUser == null) {
                return new GetTokenResult(null, new HashMap<>());
            }

            long ttl = Long.parseLong(String.valueOf(currentUser.get("expires_at")));
            if (forceRefresh || ttl - System.currentTimeMillis() <= 0) {
                refreshTokenSync(String.valueOf(currentUser.get("refresh_token")));
            }

            return createTokenResult(
                    String.valueOf(currentUser.get("access_token")),
                    String.valueOf(currentUser.get("id_token")));
        });
    }

    private Task<?> refreshToken(String refreshToken) {
        return Tasks.call(EXECUTOR, () -> refreshTokenSync(refreshToken));
    }

    private ExchangeTokenResponse refreshTokenSync(String refreshToken) throws java.io.IOException {
        ExchangeTokenRequest tokenRequest = new ExchangeTokenRequest();

        tokenRequest.setRefreshToken(refreshToken);
        tokenRequest.setGrantType("refresh_token");

        ExchangeTokenResponse tokenResponse = tokenService.v1().token(tokenRequest).execute();
        int expiresInSeconds = Integer.parseInt(String.valueOf(tokenResponse.get("expires_in")));
        tokenResponse.set("expires_at", System.currentTimeMillis() + expiresInSeconds * 1_000);

        store.save(tokenResponse);
        currentUser = tokenResponse;
        notifyListeners(new InternalTokenResult(tokenResponse.getAccessToken()));

        return tokenResponse;
    }

    private GetTokenResult createTokenResult(String accessToken, String idToken) {
        JWT jwt = new JWT(idToken);
        Map<String, Object> claims = new HashMap<>();
        for (Entry<String, Claim> entry : jwt.getClaims().entrySet()) {
            Object value;
            if (entry.getKey().equals("exp") ||
                    entry.getKey().equals("auth_time") ||
                    entry.getKey().equals("iat")) {
                value = entry.getValue().asObject(Integer.class);
            } else {
                value = entry.getValue().asObject(Object.class);
            }
            claims.put(entry.getKey(), value);
        }
        return new GetTokenResult(accessToken, claims);
    }

    private void notifyListeners(InternalTokenResult result) {
        synchronized (LOCK) {
            for (IdTokenListener listener : listeners) {
                listener.onIdTokenChanged(result);
            }
        }
    }
}
