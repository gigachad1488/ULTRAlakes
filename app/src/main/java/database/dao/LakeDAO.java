package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entities.Lake;

@Dao
public interface LakeDAO
{
    @Insert
    public void insert(Lake lake);
    @Update
    public void update(Lake lake);
    @Delete
    public void delete(Lake lake);

    @Query("SELECT * FROM Lake")
    LiveData<List<Lake>> getAllLakes();
}