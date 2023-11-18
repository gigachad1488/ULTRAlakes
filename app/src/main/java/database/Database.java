package database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.dao.LakeDAO;
import database.dao.OriginDAO;
import database.dao.PlaceDAO;
import database.dao.SalinityDAO;
import database.dao.WaterBalanceDAO;
import database.entities.Lake;
import database.entities.Origin;
import database.entities.Place;
import database.entities.Salinity;
import database.entities.WaterBalance;

@androidx.room.Database(entities = {Lake.class, Origin.class, Place.class, Salinity.class, WaterBalance.class}, version = 3 )
public abstract class Database extends RoomDatabase
{
    public abstract LakeDAO lakeDao();
    public abstract PlaceDAO placeDao();
    public abstract OriginDAO originDao();
    public abstract SalinityDAO salinityDao();
    public abstract WaterBalanceDAO waterBalanceDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(5);

    public static volatile Database instance;
    public static Database getDatabase(Context context)
    {
        if (instance == null)
        {
            synchronized (Database.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
