package com.example.bookaticket.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentsDao {
    @Query("select * from Comments")
    List<Comment> getAll();

    @Query("select * from  Comments where bookInfoID = :bookInfoID")
    List<Comment> getCommentsBybookInfoID(String bookInfoID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Comment... comments);
}
