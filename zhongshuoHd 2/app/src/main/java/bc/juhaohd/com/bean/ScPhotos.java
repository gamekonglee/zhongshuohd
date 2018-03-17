package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bocang on 18-2-2.
 */

public class ScPhotos implements Parcelable{
    private String width;
    private String height;
    private String thumb;
    private String large;

    protected ScPhotos(Parcel in) {
        width = in.readString();
        height = in.readString();
        thumb = in.readString();
        large = in.readString();
    }

    public static final Creator<ScPhotos> CREATOR = new Creator<ScPhotos>() {
        @Override
        public ScPhotos createFromParcel(Parcel in) {
            return new ScPhotos(in);
        }

        @Override
        public ScPhotos[] newArray(int size) {
            return new ScPhotos[size];
        }
    };

    public void setWidth(String width) {
        this.width = width;
    }
    public String getWidth() {
        return width;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    public String getHeight() {
        return height;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    public String getThumb() {
        return thumb;
    }

    public void setLarge(String large) {
        this.large = large;
    }
    public String getLarge() {
        return large;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(thumb);
        dest.writeString(large);
    }
}
