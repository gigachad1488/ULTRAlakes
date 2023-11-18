package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Salinity")
public class Salinity
{
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "salinity")
    public String salinity;
}
