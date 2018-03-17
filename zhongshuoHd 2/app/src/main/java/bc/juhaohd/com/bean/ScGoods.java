package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bocang on 18-2-1.
 */

public class ScGoods implements Parcelable {

    private int id;
    private int amount;
    private String property;
    private String price;
    private int attr_stock;
    private String attrs;
    private ScProduct product;

    public ScGoods() {

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    public String getProperty() {
        return property;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setAttr_stock(int attr_stock) {
        this.attr_stock = attr_stock;
    }
    public int getAttr_stock() {
        return attr_stock;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
    public String getAttrs() {
        return attrs;
    }

    public void setProduct(ScProduct product) {
        this.product = product;
    }
    public ScProduct getProduct() {
        return product;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeInt(amount);
        dest.writeString(property);
        dest.writeString(price);
        dest.writeString(attrs);
        dest.writeInt(attr_stock);
        dest.writeParcelable(product,flags);

    }

    public static final Parcelable.Creator<ScGoods> CREATOR = new Parcelable.Creator<ScGoods>()
    {
        public ScGoods createFromParcel(Parcel in)
        {

            return new ScGoods(in);
        }

        public ScGoods[] newArray(int size)
        {
            return new ScGoods[size];
        }
    };

    public ScGoods(Parcel in)
    {
        id = in.readInt();
        amount=in.readInt();
        property=in.readString();
        price=in.readString();
        attr_stock=in.readInt();
        attrs=in.readString();
        product=in.readParcelable(ScProduct.class.getClassLoader());


    }

}