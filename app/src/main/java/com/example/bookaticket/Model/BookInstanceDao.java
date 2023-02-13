package com.example.bookaticket.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookInstanceDao {
    @Query("select * from BookInstance")
    List<BookInstance> getAll();

    @Query("select * from  BookInstance where stationID = :stationID")
    List<BookInstance> getBookInstanceByStationID(String stationID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BookInstance... bookInstances);
}