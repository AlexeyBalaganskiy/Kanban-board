package com.example.kanbanboard.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Card.class,Comment.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract CardDao cardDao();
    public abstract CommentDao commentDao();
    public static synchronized AppDatabase getInstance(Context context)
    {
      if (instance==null)
      {
          instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database")
                  .fallbackToDestructiveMigration()
                  .build();
      }
      return instance;
    }
}
