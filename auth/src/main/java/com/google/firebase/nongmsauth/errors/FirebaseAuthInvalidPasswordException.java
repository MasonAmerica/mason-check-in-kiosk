package com.google.firebase.nongmsauth.errors;

import com.google.firebase.auth.FirebaseAuthException;

public class FirebaseAuthInvalidPasswordException extends FirebaseAuthException {
    public FirebaseAuthInvalidPasswordException() {
        super("INVALID_PASSWORD", "Invalid password.");
    }
}
