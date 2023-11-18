package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entities.Origin;

@Dao
public interface OriginDAO
{
    @Insert(onConflict = 2)
    public void insert(Origin origin);
    @Update
    public void update(Origin origin);
    @Delete
    public void delete(Origin origin);

    @Query("SELECT * FROM Origin")
    List<Origin> getAllOrigins();

    @Query("SELECT * FROM Origin WHERE id = :id")
    LiveData<Origin> getOriginAt(long id);
}