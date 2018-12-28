package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = "plot_plot",
        foreignKeys = {
                @ForeignKey(
                        entity = SamplelandEntity.class,
                        parentColumns = "land_id",
                        childColumns = "land_id",
                        onDelete = CASCADE
                ),
//                @ForeignKey(
//                        entity = SampleplotEntity.class,
//                        parentColumns = "plot_id",
//                        childColumns = "child_id",
//                        onDelete = CASCADE
//                )
        },
        indices = {
                @Index("land_id"),
                @Index("parent_id"),
                @Index(value = "child_id", unique = true)
        }
)
public class PlotPlotEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "land_id")
    public String landId;

    @ColumnInfo(name = "parent_id")
    public String parentId;

    @ColumnInfo(name = "parent_type")
    public String parentType;

    @ColumnInfo(name = "child_id")
    public String childId;

    @ColumnInfo(name = "child_type")
    public String childType;

    public PlotPlotEntity() {
    }

    @Ignore
    public PlotPlotEntity(String landId, String parentId, String parentType, String childId, String childType) {
        this.landId = landId;
        this.parentId = parentId;
        this.parentType = parentType;
        this.childId = childId;
        this.childType = childType;
    }
}
