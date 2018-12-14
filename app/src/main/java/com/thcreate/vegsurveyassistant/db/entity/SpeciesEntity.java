package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "species",
        foreignKeys = @ForeignKey(
                entity = SampleplotEntity.class,
                parentColumns = "plot_id",
                childColumns = "plot_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("plot_id"),
                @Index(value = "species_id", unique = true),
                @Index("type")
        }
)
public class SpeciesEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "plot_id")
    public String plotId;//所属样方编号

    @ColumnInfo(name = "species_id")
    public String speciesId;//物种编号

    public String type;//物种类型

    public String name;//物种名

    @ColumnInfo(name = "latin_name")
    public String latinName;//拉丁名

    public String data;//数据

    public String note;//备注

    public SpeciesEntity() {
    }

    @Ignore
    public SpeciesEntity(@NonNull String plotId, @NonNull String speciesId){
        this.plotId = plotId;
        this.speciesId = speciesId;
    }

}
