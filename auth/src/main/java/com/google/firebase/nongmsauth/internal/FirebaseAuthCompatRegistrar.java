package com.google.firebase.nongmsauth.internal;

import androidx.annotation.Keep;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.nongmsauth.FirebaseAuthCompat;

import java.util.Arrays;
import java.util.List;

@Keep
public final class FirebaseAuthCompatRegistrar implements ComponentRegistrar {
    @Override
    public List<Component<?>> getComponents() {
        return Arrays.asList(Component
                .builder(DefaultFirebaseAuthCompat.class, FirebaseAuthCompat.class,
                        InternalAuthProvider.class)
                .add(Dependency.required(FirebaseApp.class))
                .factory(container -> new DefaultFirebaseAuthCompat(
                        container.get(FirebaseApp.class)))
                .build());
    }
}
