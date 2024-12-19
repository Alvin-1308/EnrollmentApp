package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToEnrollment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi tombol
        btnGoToEnrollment = findViewById(R.id.btnGoToEnrollment);

        // Listener untuk membuka halaman EnrollmentActivity
        btnGoToEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnrollmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
