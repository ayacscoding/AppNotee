package com.example.appnote;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AppNote.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract NoteDo noteDo();
}