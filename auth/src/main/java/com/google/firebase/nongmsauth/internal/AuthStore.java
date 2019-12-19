package com.google.firebase.nongmsauth.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class AuthStore {
    private final File store;

    public AuthStore(@NonNull FirebaseApp app) {
        File appDir = app.getApplicationContext().getFilesDir();
        store = new File(appDir, "nongmsauth/" + app.getName() + ".json");
    }

    public void save(@NonNull GenericJson data) {
        ensureExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(store))) {
            writer.write(data.toPrettyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public GenericJson restore() {
        if (!store.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(store))) {
            return JacksonFactory.getDefaultInstance().createJsonParser(reader)
                    .parse(GenericJson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureExists() {
        try {
            if (!store.exists()) {
                File parent = store.getParentFile();
                if (parent == null || !parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create file: " + parent);
                }
                if (!store.createNewFile()) {
                    throw new IllegalStateException("Couldn't create file: " + store);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
