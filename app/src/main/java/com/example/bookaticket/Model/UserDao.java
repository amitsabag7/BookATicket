package com.example.bookaticket.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("select * from  User where email = :email")
    User getUserByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);
}
