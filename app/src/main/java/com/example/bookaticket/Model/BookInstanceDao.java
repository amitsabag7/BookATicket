package com.example.bookaticket.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookInstanceDao {
    @Query("select * from BookInstance")
    List<BookInstance> getAll();

    @Query("select * from  BookInstance where stationID = :stationID")
    List<BookInstance> getBookInstanceByStationID(String stationID);

    @Query("select * from  BookInstance where userEmail = :userEmail")
    List<BookInstance> getBookInstanceByUserEmail(String userEmail);

    @Query("update BookInstance set stationId ='', userEmail = :userEmail where id= :bookInstanceID")
    void takeBookFromStation(String bookInstanceID, String userEmail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BookInstance... bookInstances);
}
