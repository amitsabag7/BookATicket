package com.example.bookaticket.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookaticket.MyApplication;

@Database(entities = {Station.class, BookInstance.class, BookInfo.class, Comment.class}, version = 11)

abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract StationDao stationDao();
    public abstract BookInstanceDao bookInstanceDao();
    public abstract BookInfoDao bookInfoDao();
    public abstract CommentsDao commentsDao();
}
public class AppLocalDB {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDB(){}
}
