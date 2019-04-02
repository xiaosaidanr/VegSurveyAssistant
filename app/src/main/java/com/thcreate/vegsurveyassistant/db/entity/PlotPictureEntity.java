package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import com.thcreate.vegsurveyassistant.db.entity.model.Picture;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = "plot_picture",
        foreignKeys = @ForeignKey(
                entity = SampleplotEntity.class,
                parentColumns = "plot_id",
                childColumns = "owner_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("owner_id"),
                @Index(value = "picture_id", unique = true)
        }
)
public class PlotPictureEntity extends PictureEntity {

        public static PlotPictureEntity getInstance(Picture data){
            PlotPictureEntity plotPictureEntity = new PlotPictureEntity();
            plotPictureEntity.pictureId = data.pictureId;
            plotPictureEntity.ownerId = data.ownerId;
            plotPictureEntity.url = data.url;
            plotPictureEntity.createAt = data.createAt;
            plotPictureEntity.updateAt = data.updateAt;
            plotPictureEntity.uploadAt = data.updateAt;
            return plotPictureEntity;
        }

}
