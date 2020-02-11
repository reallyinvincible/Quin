package com.exuberant.quin.data.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.exuberant.quin.data.db.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {User.class},
        version = 1,
        exportSchema = false
)
public abstract class UserDatabase extends RoomDatabase {


    public abstract UserDao userDao();

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "user_db";
    private static UserDatabase sInstance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseAccessExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
