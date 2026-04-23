package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    private ImageView imgNoNote;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. ربط العناصر
        imgNoNote = findViewById(R.id.no_note);
        recyclerView = findViewById(R.id.recyclerView);
        Button btnCreateCenter = findViewById(R.id.button);
        Button btnAddIcon = findViewById(R.id.btn_add_note);

        // إعداد الـ RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. تهيئة قاعدة البيانات
        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, "notes_db").build();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.navigation_categories) { // استخدمنا الاسم الموجود في الـ XML
                Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        btnCreateCenter.setOnClickListener(v -> openNewNote());
        btnAddIcon.setOnClickListener(v -> openNewNote());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // جلب البيانات عند كل عودة للصفحة
    }

    private void loadNotes() {
        new Thread(() -> {
            // جلب القائمة من الـ DAO
            List<AppNote> notes = db.noteDo().getAll();

            runOnUiThread(() -> {
                if (notes != null && !notes.isEmpty()) {
                    // إخفاء صورة "لا توجد ملاحظات" وإظهار القائمة
                    imgNoNote.setVisibility(View.GONE);
                    findViewById(R.id.textView4).setVisibility(View.GONE);
                    findViewById(R.id.textView8).setVisibility(View.GONE);
                    findViewById(R.id.button).setVisibility(View.GONE);

                    recyclerView.setVisibility(View.VISIBLE);

                    // تحديث الـ Adapter لعرض الخانات
                    adapter = new NotesAdapter(notes);
                    recyclerView.setAdapter(adapter);

                    // تحديث العداد في الأعلى
                    TextView tvCount = findViewById(R.id.textView6);
                    tvCount.setText(notes.size() + " notes");
                } else {
                    imgNoNote.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
        }).start();
    }

    private void openNewNote() {
        Intent intent = new Intent(HomeActivity.this, NewnoteActivity.class);
        startActivity(intent);
    }
}