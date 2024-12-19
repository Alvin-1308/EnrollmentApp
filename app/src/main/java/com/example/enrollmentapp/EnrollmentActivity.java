package com.example.enrollmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnSubmit;
    private SubjectAdapter adapter;
    private List<Subject> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        recyclerView = findViewById(R.id.recyclerView);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Data Subjek
        subjectList = new ArrayList<>();
        subjectList.add(new Subject("Mathematics", false));
        subjectList.add(new Subject("Physics", false));
        subjectList.add(new Subject("Chemistry", false));
        subjectList.add(new Subject("Biology", false));
        subjectList.add(new Subject("History", false));

        // Inisialisasi RecyclerView
        adapter = new SubjectAdapter(subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Submit Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> enrolledSubjects = new ArrayList<>();
                for (Subject subject : subjectList) {
                    if (subject.isChecked()) {
                        enrolledSubjects.add(subject.getName());
                    }
                }

                if (enrolledSubjects.isEmpty()) {
                    Toast.makeText(EnrollmentActivity.this, "No subjects selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnrollmentActivity.this, "Enrolled: " + String.join(", ", enrolledSubjects), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
