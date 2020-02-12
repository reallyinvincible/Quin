package com.exuberant.quin.data.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.exuberant.quin.data.db.entity.User;

@Database(
        entities = {User.class},
        version = 2,
        exportSchema = false
)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "user_db";
    private static UserDatabase sInstance;

    public static UserDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, UserDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

}
