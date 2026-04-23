package com.example.appnote;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories); // تأكدي أن هذا اسم ملف الـ XML الخاص بكِ

        // برمجة زر الرجوع الموجود في التصميم (btnBack)
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}