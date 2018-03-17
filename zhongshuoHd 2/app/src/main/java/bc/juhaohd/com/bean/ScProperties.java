package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bocang on 18-2-2.
 */

public class ScProperties implements Parcelable{
    private int id;
    private String name;
    private List<ScAttrs> attrs;
    private boolean is_multiselect;

    protected ScProperties(Parcel in) {
        id = in.readInt();
        name = in.readString();
        attrs = in.createTypedArrayList(ScAttrs.CREATOR);
        is_multiselect = in.readByte() != 0;
//        attrs=in.readArrayList(ScAttrs.class.getClassLoader());
    }

    public static final Creator<ScProperties> CREATOR = new Creator<ScProperties>() {
        @Override
        public ScProperties createFromParcel(Parcel in) {
            return new ScProperties(in);
        }

        @Override
        public ScProperties[] newArray(int size) {
            return new ScProperties[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setScAttrs(List<ScAttrs> attrs) {
        this.attrs = attrs;
    }
    public List<ScAttrs> getScAttrs() {
        return attrs;
    }

    public void setIs_multiselect(boolean is_multiselect) {
        this.is_multiselect = is_multiselect;
    }
    public boolean getIs_multiselect() {
        return is_multiselect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(attrs);
        dest.writeByte((byte) (is_multiselect ? 1 : 0));


    }
}
