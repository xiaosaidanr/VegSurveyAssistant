package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = "land_picture",
        foreignKeys = @ForeignKey(
                entity = SamplelandEntity.class,
                parentColumns = "land_id",
                childColumns = "owner_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("owner_id"),
                @Index(value = "picture_id", unique = true)
        }
)
public class LandPictureEntity extends PictureEntity {
}
