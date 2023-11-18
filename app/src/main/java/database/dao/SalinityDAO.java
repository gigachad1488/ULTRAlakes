package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entities.Place;
import database.entities.Salinity;

@Dao
public interface SalinityDAO
{
    @Insert(onConflict = 2)
    public void insert(Salinity salinity);
    @Update
    public void update(Salinity salinity);
    @Delete
    public void delete(Salinity salinity);

    @Query("SELECT * FROM Salinity")
    LiveData<List<Salinity>> getAllSalinities();

    @Query("SELECT * FROM Salinity WHERE id = :id")
    LiveData<Salinity> getSalinityAt(long id);
}