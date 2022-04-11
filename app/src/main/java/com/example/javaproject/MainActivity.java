package com.example.javaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    BiometricPrompt bPrompt;
    BiometricPrompt.PromptInfo bproInfo;
    ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.mainLayout);

        BiometricManager bManager = BiometricManager.from(this);
        int i = bManager.canAuthenticate();
        if (i == bManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            Toast.makeText(getApplicationContext(), "Device no fingerprint", Toast.LENGTH_SHORT).show();
        } else if (i == bManager.BIOMETRIC_ERROR_HW_UNAVAILABLE) {
            Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
        } else if (i == bManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            Toast.makeText(getApplicationContext(), "No fringerprint assigned", Toast.LENGTH_SHORT).show();
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        bPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                mLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        bproInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("JavaProjekti")
                .setDescription("User Fringerprint to login").setDeviceCredentialAllowed(true).build();

        bPrompt.authenticate(bproInfo);
    }
}