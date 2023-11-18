package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Lake", foreignKeys = {@ForeignKey(entity = Place.class, parentColumns = "id", childColumns = "place_id"), @ForeignKey(entity = Origin.class, parentColumns = "id", childColumns = "origin_id"),
        @ForeignKey(entity = Salinity.class, parentColumns = "id", childColumns = "salinity_id"), @ForeignKey(entity = WaterBalance.class, parentColumns = "id", childColumns = "water_balance_id")},
        indices = {@Index(value = {"place_id"}), @Index(value = {"origin_id"}), @Index(value = {"water_balance_id"}), @Index(value = {"salinity_id"})})

public class Lake
{
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "place_id")
    public int placeId;

    @ColumnInfo(name = "origin_id")
    public int originId;

    @ColumnInfo(name = "water_balance_id")
    public int waterBalanceId;

    @ColumnInfo(name = "salinity_id")
    public int salinityId;

    @ColumnInfo(name = "depth")
    public int depth;

    @ColumnInfo(name = "square")
    public int square;

    public Lake(long id)
    {
    }
}
