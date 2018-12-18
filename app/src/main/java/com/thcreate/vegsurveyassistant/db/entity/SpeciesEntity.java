package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;

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
    public String plotId;//所属样方ID

    @ColumnInfo(name = "species_id")
    public String speciesId;//物种ID

    public String code;//物种编号

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

    public <T extends BaseSpecies> void initCommonFromSpecies(T data){
        this.id = data.id;
        this.plotId = data.plotId;
        this.speciesId = data.speciesId;
        this.code = data.code;
        this.type = data.type;
        this.name = data.name;
        this.latinName = data.latinName;
        this.note = data.note;
        this.createAt = data.createAt;
        this.updateAt = data.updateAt;
        this.uploadAt = data.uploadAt;
        this.deleteAt = data.deleteAt;
    }

}
