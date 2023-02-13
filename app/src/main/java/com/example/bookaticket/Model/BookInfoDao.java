package com.example.bookaticket.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookInfoDao {
    @Query("select * from BookInfo")
    List<BookInstance> getAll();

    @Query("select * from  BookInfo where id = :id")
    BookInfo getBookInfoByID(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BookInfo... bookInfos);
}

