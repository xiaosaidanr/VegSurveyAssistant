package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = "point_picture",
        foreignKeys = @ForeignKey(
                entity = SamplepointEntity.class,
                parentColumns = "point_id",
                childColumns = "owner_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("owner_id"),
                @Index(value = "picture_id", unique = true)
        }
)
public class PointPictureEntity extends PictureEntity {
}
