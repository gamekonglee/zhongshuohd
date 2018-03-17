package bc.juhaohd.com.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bocang on 18-2-2.
 */

public class ScProduct implements Parcelable{
    private int sales_count;
    private String goods_desc;
    private String goods_video;
    private String original_img;
    private int is_shipping;
    private ScAppImg app_img;
    private int id;
    private int category;
    private int brand;
    private int shop;
    private String sku;
    private ScDefault_photo default_photo;
    private List<ScPhotos> photos;
    private String name;
    private String price;
    private String current_price;
    private int score;
    private int good_stock;
    private int comment_count;
    private int is_liked;
    private String review_rate;
    private String intro_url;
    private String share_url;
    private long created_at;
    private long updated_at;
    private List<ScProperties> properties;
public  ScProduct(){

}
    public ScProduct(Parcel in) {
        sales_count = in.readInt();
        goods_desc = in.readString();
        goods_video = in.readString();
        original_img = in.readString();
        is_shipping = in.readInt();
        id = in.readInt();
        category = in.readInt();
        brand = in.readInt();
        shop = in.readInt();
        sku = in.readString();
        name = in.readString();
        price = in.readString();
        current_price = in.readString();
        score = in.readInt();
        good_stock = in.readInt();
        comment_count = in.readInt();
        is_liked = in.readInt();
        review_rate = in.readString();
        intro_url = in.readString();
        share_url = in.readString();
        created_at = in.readLong();
        updated_at = in.readLong();
        app_img=in.readParcelable(ScAppImg.class.getClassLoader());
        default_photo=in.readParcelable(ScDefault_photo.class.getClassLoader());
        photos=in.createTypedArrayList(ScPhotos.CREATOR);
        properties=in.createTypedArrayList(ScProperties.CREATOR);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeInt(sales_count);
        dest.writeString(goods_desc);
        dest.writeString(goods_video);
        dest.writeString(original_img);
        dest.writeInt(is_shipping);
        dest.writeInt(id);
        dest.writeInt(category);
        dest.writeInt(brand);
        dest.writeInt(shop);
        dest.writeString(sku);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(current_price);
        dest.writeInt(score);
        dest.writeInt(good_stock);
        dest.writeInt(comment_count);
        dest.writeInt(is_liked);
        dest.writeString(review_rate);
        dest.writeString(intro_url);
        dest.writeString(share_url);
        dest.writeLong(created_at);
        dest.writeLong(updated_at);
        dest.writeParcelable(app_img,flags);
        dest.writeParcelable(default_photo,flags);
        dest.writeTypedList(photos);
        dest.writeTypedList(properties);
    }
    public static final Parcelable.Creator<ScProduct> CREATOR = new Parcelable.Creator<ScProduct>() {
        @Override
        public ScProduct createFromParcel(Parcel in) {
            return new ScProduct(in);
        }

        @Override
        public ScProduct[] newArray(int size) {
            return new ScProduct[size];
        }
    };

    public void setSales_count(int sales_count) {
        this.sales_count = sales_count;
    }
    public int getSales_count() {
        return sales_count;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }
    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_video(String goods_video) {
        this.goods_video = goods_video;
    }
    public String getGoods_video() {
        return goods_video;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }
    public String getOriginal_img() {
        return original_img;
    }

    public void setIs_shipping(int is_shipping) {
        this.is_shipping = is_shipping;
    }
    public int getIs_shipping() {
        return is_shipping;
    }

    public void setApp_img(ScAppImg app_img) {
        this.app_img = app_img;
    }
    public ScAppImg getApp_img() {
        return app_img;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    public int getCategory() {
        return category;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }
    public int getBrand() {
        return brand;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }
    public int getShop() {
        return shop;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getSku() {
        return sku;
    }

    public void setDefault_photo(ScDefault_photo default_photo) {
        this.default_photo = default_photo;
    }
    public ScDefault_photo getDefault_photo() {
        return default_photo;
    }

    public void setPhotos(List<ScPhotos> photos) {
        this.photos = photos;
    }
    public List<ScPhotos> getPhotos() {
        return photos;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setCurrent_price(String current_price) {
        this.current_price = current_price;
    }
    public String getCurrent_price() {
        return current_price;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setGood_stock(int good_stock) {
        this.good_stock = good_stock;
    }
    public int getGood_stock() {
        return good_stock;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
    public int getComment_count() {
        return comment_count;
    }

    public void setIs_liked(int is_liked) {
        this.is_liked = is_liked;
    }
    public int getIs_liked() {
        return is_liked;
    }

    public void setReview_rate(String review_rate) {
        this.review_rate = review_rate;
    }
    public String getReview_rate() {
        return review_rate;
    }

    public void setIntro_url(String intro_url) {
        this.intro_url = intro_url;
    }
    public String getIntro_url() {
        return intro_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
    public String getShare_url() {
        return share_url;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
    public long getCreated_at() {
        return created_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }
    public long getUpdated_at() {
        return updated_at;
    }

    public void setProperties(List<ScProperties> properties) {
        this.properties = properties;
    }
    public List<ScProperties> getProperties() {
        return properties;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
