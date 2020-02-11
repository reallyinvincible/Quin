package com.exuberant.quin.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exuberant.quin.data.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<List<User>> getUserByEmail(String email);

    @Query("DELETE FROM users WHERE id = :id")
    void deleteUser(String id);

}
