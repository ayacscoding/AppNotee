package com.example.appnote;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class AppNote {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;

    public AppNote(String title, String content) {
        this.title = title;
        this.content = content;
    }
}