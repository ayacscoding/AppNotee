package com.example.appnote;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
// استيراد المكتبات الضرورية للـ Room
import androidx.room.Room;

public class NewnoteActivity extends AppCompatActivity {

    private EditText etTitle, etContent, etTags;
    private ImageView btnBack, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        // ربط العناصر بالكود
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etTags = findViewById(R.id.etTags);
        btnBack = findViewById(R.id.backBtn);
        btnSave = findViewById(R.id.saveBtn);

        // زر الرجوع
        btnBack.setOnClickListener(v -> finish());

        // زر الحفظ (تم تعديله ليتعامل مع قاعدة البيانات)
        btnSave.setOnClickListener(v -> {
            saveNote(); // استدعاء دالة الحفظ
        });
    }

    // دالة الحفظ التي تستخدم Room
    private void saveNote() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // الحفظ في قاعدة البيانات (في خيط خلفي Background Thread)
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1. إنشاء نسخة من قاعدة البيانات
                MyDatabase db = Room.databaseBuilder(getApplicationContext(),
                        MyDatabase.class, "notes_db").build();

                // 2. إنشاء كائن الملاحظة (باستخدام الكلاس AppNote الذي أنشأناه)
                AppNote newNote = new AppNote(title, content);

                // 3. تنفيذ أمر الحفظ عبر الـ DAO
                db.noteDo().insert(newNote);

                // 4. العودة لواجهة المستخدم لإظهار النتيجة
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewnoteActivity.this, "Note Saved in Database!", Toast.LENGTH_SHORT).show();
                        finish(); // العودة للصفحة الرئيسية
                    }
                });
            }
        }).start();
    }
}