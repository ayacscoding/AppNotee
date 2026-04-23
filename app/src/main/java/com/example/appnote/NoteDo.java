package com.example.appnote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface NoteDo {
    @Insert
    void insert(AppNote note);

    @Query("SELECT * FROM notes")
    List<AppNote> getAll();

    @Update
    void update(AppNote note); // دالة التعديل الجديدة

    @Delete
    void delete(AppNote note); // دالة الحذف الجديدة
}