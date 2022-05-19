package ru.pervukhin.gamelist;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import ru.pervukhin.gamelist.db.AppDataBase;

public class App extends Application {

    public static App instance;
    private AppDataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDataBase.class, "database.db")
                .build();

    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDatabase() {
        return database;
    }
}
