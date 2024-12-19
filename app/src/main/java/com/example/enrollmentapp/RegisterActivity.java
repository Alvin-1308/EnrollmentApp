package com.example.enrollmentapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validasi Input
                if (TextUtils.isEmpty(name)) {
                    etName.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }

                // Simpan Data ke Database
                boolean isInserted = dbHelper.insertStudent(name, email, password);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke layar sebelumnya
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
