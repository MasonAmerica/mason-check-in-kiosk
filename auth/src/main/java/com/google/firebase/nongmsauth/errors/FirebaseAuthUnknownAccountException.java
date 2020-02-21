package com.google.firebase.nongmsauth.errors;

import com.google.firebase.auth.FirebaseAuthException;

public class FirebaseAuthUnknownAccountException extends FirebaseAuthException {
    public FirebaseAuthUnknownAccountException() {
        super("EMAIL_NOT_FOUND", "Account not found.");
    }
}
