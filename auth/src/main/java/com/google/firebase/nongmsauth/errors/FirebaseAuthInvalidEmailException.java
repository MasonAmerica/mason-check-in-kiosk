package com.google.firebase.nongmsauth.errors;

import com.google.firebase.auth.FirebaseAuthException;

public class FirebaseAuthInvalidEmailException extends FirebaseAuthException {
    public FirebaseAuthInvalidEmailException() {
        super("INVALID_EMAIL", "Invalid email address.");
    }
}
