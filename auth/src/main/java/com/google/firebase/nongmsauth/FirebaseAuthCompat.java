package com.google.firebase.nongmsauth;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;

public interface FirebaseAuthCompat extends InternalAuthProvider {
    @NonNull
    static FirebaseAuthCompat getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @NonNull
    static FirebaseAuthCompat getInstance(@NonNull FirebaseApp app) {
        return app.get(FirebaseAuthCompat.class);
    }

    @NonNull
    Task<?> signInWithEmailAndPassword(@NonNull String email, @NonNull String password);
}
