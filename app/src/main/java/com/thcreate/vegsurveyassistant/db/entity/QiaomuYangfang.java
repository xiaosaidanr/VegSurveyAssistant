package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "qiaomu_yangfang",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id"
                ),
//                @ForeignKey(
//                        entity = Yangdi.class,
//                        parentColumns = "yangdi_code",
//                        childColumns = "yangdi_code",
//                        onDelete = CASCADE
//                )
        },
        indices = {
                @Index("user_id"),
                @Index("yangdi_code"),
                @Index(value = "yangfang_code", unique = true)
        }
)
public class QiaomuYangfang extends BaseYangfang implements Parcelable {

    @ColumnInfo(name = "xiongjing_average")
    public String xiongjingAverage;//平均胸径

    public QiaomuYangfang() {
    }

    @Ignore
    public QiaomuYangfang(@NonNull int userId, @NonNull String yangdiCode, @NonNull String yangfangCode){
        super(userId, yangdiCode, yangfangCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(yangdiCode);
        dest.writeValue(yangfangCode);
        dest.writeValue(qunluoName);
        dest.writeValue(length);
        dest.writeValue(width);
        dest.writeValue(qunluoCoverage);
        dest.writeValue(qunluoHeight);
        dest.writeValue(xiongjingAverage);
        dest.writeValue(longitude);
        dest.writeValue(latitude);
        dest.writeValue(investigator);
        dest.writeValue(investigateDate);
        if (createAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(createAt.getTime());
        }
        if (deleteAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(deleteAt.getTime());
        }
        if (modifyAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(modifyAt.getTime());
        }
        if (uploadAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(uploadAt.getTime());
        }
    }

    public static final Parcelable.Creator<QiaomuYangfang> CREATOR = new Creator<QiaomuYangfang>() {
        @Override
        public QiaomuYangfang createFromParcel(Parcel source) {
            return new QiaomuYangfang(source);
        }

        @Override
        public QiaomuYangfang[] newArray(int size) {
            return new QiaomuYangfang[0];
        }
    };

    @Ignore
    public QiaomuYangfang(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpYangdiCode = source.readValue(getClass().getClassLoader());
        if (tmpYangdiCode != null){
            yangdiCode = (String)tmpYangdiCode;
        }
        Object tmpYangfangCode = source.readValue(getClass().getClassLoader());
        if (tmpYangfangCode != null){
            yangfangCode = (String)tmpYangfangCode;
        }
        Object tmpQunluoName = source.readValue(getClass().getClassLoader());
        if (tmpQunluoName != null){
            qunluoName = (String)tmpQunluoName;
        }
        Object tmpLength = source.readValue(getClass().getClassLoader());
        if (tmpLength != null){
            length = (String)tmpLength;
        }
        Object tmpWidth = source.readValue(getClass().getClassLoader());
        if (tmpWidth != null){
            width = (String)tmpWidth;
        }
        Object tmpQunluoCoverage = source.readValue(getClass().getClassLoader());
        if (tmpQunluoCoverage != null){
            qunluoCoverage = (String)tmpQunluoCoverage;
        }
        Object tmpQunluoHeight = source.readValue(getClass().getClassLoader());
        if (tmpQunluoHeight != null){
            qunluoHeight = (String)tmpQunluoHeight;
        }
        Object tmpXiongjingAverage = source.readValue(getClass().getClassLoader());
        if (tmpXiongjingAverage != null){
            xiongjingAverage = (String)tmpXiongjingAverage;
        }
        Object tmpLongitude = source.readValue(getClass().getClassLoader());
        if (tmpLongitude != null){
            longitude = (String)tmpLongitude;
        }
        Object tmpLatitude = source.readValue(getClass().getClassLoader());
        if (tmpLatitude != null){
            latitude = (String)tmpLatitude;
        }
        Object tmpInvestigator = source.readValue(getClass().getClassLoader());
        if (tmpInvestigator != null){
            investigator = (String)tmpInvestigator;
        }
        Object tmpInvestigateDate = source.readValue(getClass().getClassLoader());
        if (tmpInvestigateDate != null){
            investigateDate = (String)tmpInvestigateDate;
        }
        Object tmpCreateAt = source.readValue(getClass().getClassLoader());
        if (tmpCreateAt != null){
            createAt = new Date((Long)tmpCreateAt);
        }
        Object tmpDeleteAt = source.readValue(getClass().getClassLoader());
        if (tmpDeleteAt != null){
            deleteAt = new Date((Long)tmpDeleteAt);
        }
        Object tmpModifyAt = source.readValue(getClass().getClassLoader());
        if (tmpModifyAt != null){
            modifyAt = new Date((Long)tmpModifyAt);
        }
        Object tmpUploadAt = source.readValue(getClass().getClassLoader());
        if (tmpUploadAt != null){
            uploadAt = new Date((Long)tmpUploadAt);
        }
    }
}
