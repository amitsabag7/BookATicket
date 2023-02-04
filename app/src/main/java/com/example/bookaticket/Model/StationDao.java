package com.example.bookaticket.Model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StationDao {
    @Query("select * from Station")
    List<Station> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Station... stations);
}





