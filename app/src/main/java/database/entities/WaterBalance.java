package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Water_balance")
public class WaterBalance
{
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "type")
    public String type;
}
