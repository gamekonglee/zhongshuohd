package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bocang on 18-2-2.
 */

public class ScAttrs implements Parcelable{
    private double attr_price_1;
    private double attr_price_2;
    private double attr_price_3;
    private double attr_price_4;
    private double attr_price_5;
    private int id;
    private String attr_name;
    private boolean is_multiselect;
    private String img;
    private String number;

    protected ScAttrs(Parcel in) {
        attr_price_1 = in.readDouble();
        attr_price_2 = in.readDouble();
        attr_price_3 = in.readDouble();
        attr_price_4 = in.readDouble();
        attr_price_5 = in.readDouble();
        id = in.readInt();
        attr_name = in.readString();
        is_multiselect = in.readByte() != 0;
        img = in.readString();
        number = in.readString();
    }

    public static final Creator<ScAttrs> CREATOR = new Creator<ScAttrs>() {
        @Override
        public ScAttrs createFromParcel(Parcel in) {
            return new ScAttrs(in);
        }

        @Override
        public ScAttrs[] newArray(int size) {
            return new ScAttrs[size];
        }
    };

    public void setAttr_price_1(double attr_price_1) {
        this.attr_price_1 = attr_price_1;
    }
    public double getAttr_price_1() {
        return attr_price_1;
    }

    public void setAttr_price_2(double attr_price_2) {
        this.attr_price_2 = attr_price_2;
    }
    public double getAttr_price_2() {
        return attr_price_2;
    }

    public void setAttr_price_3(double attr_price_3) {
        this.attr_price_3 = attr_price_3;
    }
    public double getAttr_price_3() {
        return attr_price_3;
    }

    public void setAttr_price_4(double attr_price_4) {
        this.attr_price_4 = attr_price_4;
    }
    public double getAttr_price_4() {
        return attr_price_4;
    }

    public void setAttr_price_5(double attr_price_5) {
        this.attr_price_5 = attr_price_5;
    }
    public double getAttr_price_5() {
        return attr_price_5;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }
    public String getAttr_name() {
        return attr_name;
    }

    public void setIs_multiselect(boolean is_multiselect) {
        this.is_multiselect = is_multiselect;
    }
    public boolean getIs_multiselect() {
        return is_multiselect;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(attr_price_1);
        dest.writeDouble(attr_price_2);
        dest.writeDouble(attr_price_3);
        dest.writeDouble(attr_price_4);
        dest.writeDouble(attr_price_5);
        dest.writeInt(id);
        dest.writeString(attr_name);
        dest.writeByte((byte) (is_multiselect ? 1 : 0));
        dest.writeString(img);
        dest.writeString(number);
    }
}
