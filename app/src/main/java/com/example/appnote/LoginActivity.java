package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.input_email);
        EditText etPassword = findViewById(R.id.input_password);
        Button btnLogin = findViewById(R.id.btnRegister);
        TextView tvGoToRegister = findViewById(R.id.go_to_register);

        btnLogin.setOnClickListener(v -> {
            // الانتقال لصفحة الهوم بعد تسجيل الدخول بنجاح
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        tvGoToRegister.setOnClickListener(v -> finish()); // العودة لصفحة التسجيل
    }
}