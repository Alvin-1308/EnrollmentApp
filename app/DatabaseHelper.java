package com.example.enrollmentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EnrollmentApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_ENROLLMENT = "enrollment";

    // Columns for Students table
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_PASSWORD = "password";

    // Columns for Subjects table
    public static final String SUBJECT_ID = "id";
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_CREDIT = "credit";

    // Columns for Enrollment table
    public static final String ENROLLMENT_ID = "id";
    public static final String ENROLLMENT_STUDENT_ID = "student_id";
    public static final String ENROLLMENT_SUBJECT_ID = "subject_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Students table
        db.execSQL("CREATE TABLE " + TABLE_STUDENTS + " (" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_NAME + " TEXT, " +
                STUDENT_EMAIL + " TEXT UNIQUE, " +
                STUDENT_PASSWORD + " TEXT)");

        // Create Subjects table
        db.execSQL("CREATE TABLE " + TABLE_SUBJECTS + " (" +
                SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUBJECT_NAME + " TEXT, " +
                SUBJECT_CREDIT + " INTEGER)");

        // Create Enrollment table
        db.execSQL("CREATE TABLE " + TABLE_ENROLLMENT + " (" +
                ENROLLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ENROLLMENT_STUDENT_ID + " INTEGER, " +
                ENROLLMENT_SUBJECT_ID + " INTEGER, " +
                "FOREIGN KEY(" + ENROLLMENT_STUDENT_ID + ") REFERENCES " + TABLE_STUDENTS + "(" + STUDENT_ID + "), " +
                "FOREIGN KEY(" + ENROLLMENT_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECTS + "(" + SUBJECT_ID + "))");

        // Pre-insert some subjects
        ContentValues cv = new ContentValues();
        cv.put(SUBJECT_NAME, "Mathematics");
        cv.put(SUBJECT_CREDIT, 3);
        db.insert(TABLE_SUBJECTS, null, cv);

        cv.put(SUBJECT_NAME, "Physics");
        cv.put(SUBJECT_CREDIT, 4);
        db.insert(TABLE_SUBJECTS, null, cv);

        cv.put(SUBJECT_NAME, "Chemistry");
        cv.put(SUBJECT_CREDIT, 3);
        db.insert(TABLE_SUBJECTS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
        onCreate(db);
    }

    // Add new student
    public boolean registerStudent(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_NAME, name);
        cv.put(STUDENT_EMAIL, email);
        cv.put(STUDENT_PASSWORD, password);
        long result = db.insert(TABLE_STUDENTS, null, cv);
        return result != -1;
    }

    // Login validation
    public boolean loginStudent(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{STUDENT_ID},
                STUDENT_EMAIL + "=? AND " + STUDENT_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        return cursor.getCount() > 0;
    }

    // Fetch all subjects
    public Cursor getAllSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SUBJECTS, null);
    }

    // Add enrollment
    public boolean enrollSubject(int studentId, int subjectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ENROLLMENT_STUDENT_ID, studentId);
        cv.put(ENROLLMENT_SUBJECT_ID, subjectId);
        long result = db.insert(TABLE_ENROLLMENT, null, cv);
        return result != -1;
    }

    // Get total credits for student
    public int getTotalCredits(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + SUBJECT_CREDIT + ") AS totalCredits FROM " +
                TABLE_ENROLLMENT + " e JOIN " + TABLE_SUBJECTS + " s ON e." + ENROLLMENT_SUBJECT_ID + " = s." + SUBJECT_ID +
                " WHERE e." + ENROLLMENT_STUDENT_ID + " = ?", new String[]{String.valueOf(studentId)});
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("totalCredits"));
        }
        return 0;
    }
}
