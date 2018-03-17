package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bocang on 18-2-2.
 */

public class ScAppImg implements Parcelable{
    private String img;
    private String phone_img;
    private String ipad_img;

    protected ScAppImg(Parcel in) {
        img = in.readString();
        phone_img = in.readString();
        ipad_img = in.readString();
    }

    public static final Creator<ScAppImg> CREATOR = new Creator<ScAppImg>() {
        @Override
        public ScAppImg createFromParcel(Parcel in) {
            return new ScAppImg(in);
        }

        @Override
        public ScAppImg[] newArray(int size) {
            return new ScAppImg[size];
        }
    };

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setPhone_img(String phone_img) {
        this.phone_img = phone_img;
    }
    public String getPhone_img() {
        return phone_img;
    }

    public void setIpad_img(String ipad_img) {
        this.ipad_img = ipad_img;
    }
    public String getIpad_img() {
        return ipad_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(phone_img);
        dest.writeString(ipad_img);
    }
}
