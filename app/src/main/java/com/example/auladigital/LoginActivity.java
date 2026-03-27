package com.example.auladigital;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    TextView tvIrARegistro;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvIrARegistro = findViewById(R.id.tvIrARegistro);
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            if (!isValidEmail(email)) {
                etLoginEmail.setError("Email inválido");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etLoginPassword.setError("Contraseña requerida");
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Bienvenido/a", Toast.LENGTH_SHORT).show();
                            // Ir a la pantalla principal
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        tvIrARegistro.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
