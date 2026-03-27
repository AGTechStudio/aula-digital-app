package com.example.auladigital;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnRegistrar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!isValidEmail(email)) {
                etEmail.setError("Email inválido");
                return;
            }
            if (password.length() < 6) {
                etPassword.setError("La contraseña debe tener al menos 6 caracteres");
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                            // Ir al login o a la pantalla principal
                            startActivity(new Intent(this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
