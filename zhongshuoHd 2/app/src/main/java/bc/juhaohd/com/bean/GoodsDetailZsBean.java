package bc.juhaohd.com.bean;

/**
 * Created by bocang on 18-1-31.
 */

/**
 * Copyright 2018 bejson.com
 */

import java.util.List;

/**
 * Auto-generated: 2018-01-31 9:47:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GoodsDetailZsBean {

/**
 * Copyright 2018 bejson.com
 */
    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Default_photo {

        private String thumb;
        private String large;
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

    }
    /**
     * Copyright 2018 bejson.com
     */
    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Stock {

        private double id;
        private double goods_id;
        private String stock_number;
        private double goods_attr_price;
        private String goods_attr;
        public void setId(double id) {
            this.id = id;
        }
        public double getId() {
            return id;
        }

        public void setGoods_id(double goods_id) {
            this.goods_id = goods_id;
        }
        public double getGoods_id() {
            return goods_id;
        }

        public void setStock_number(String stock_number) {
            this.stock_number = stock_number;
        }
        public String getStock_number() {
            return stock_number;
        }

        public void setGoods_attr_price(double goods_attr_price) {
            this.goods_attr_price = goods_attr_price;
        }
        public double getGoods_attr_price() {
            return goods_attr_price;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr;
        }
        public String getGoods_attr() {
            return goods_attr;
        }

    }

    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Photos {

        private String thumb;
        private String large;
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

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

    /**
     * Copyright 2018 bejson.com 
     */
    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

        private long upStringd_at;
        private Default_photo default_photo;
        private int sales_count;
        private List<Stock> stock;
        private String goods_desc;
        private int good_stock;
        private String review_rate;
        private double is_shipping;
        private List<Photos> photos;
        private String sku;
        private double category;
        private int id;
        private String share_url;
        private double is_liked;
        private List<String> tags;
        private String doublero_url;
        private List<String> promos;
        private double score;
        private double shop;
        private List<Properties> properties;
        private double order_id;
        private App_img app_img;
        private List<Attachments> attachments;
        private double comment_count;
        private String original_img;
        private String current_price;
        private String price;
        private double brand;
        private String name;
        private long created_at;
        private String goods_video;
        int warn_number;
        String remark;

    public int getWarn_number() {
        return warn_number;
    }

    public void setWarn_number(int warn_number) {
        this.warn_number = warn_number;
    }

    public String getDoublero_url() {
        return doublero_url;
    }

    public void setDoublero_url(String doublero_url) {
        this.doublero_url = doublero_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUpStringd_at(long upStringd_at) {
            this.upStringd_at = upStringd_at;
        }
        public long getUpStringd_at() {
            return upStringd_at;
        }

        public void setDefault_photo(Default_photo default_photo) {
            this.default_photo = default_photo;
        }
        public Default_photo getDefault_photo() {
            return default_photo;
        }

        public void setSales_count(int sales_count) {
            this.sales_count = sales_count;
        }
        public int getSales_count() {
            return sales_count;
        }

        public void setStock(List<Stock> stock) {
            this.stock = stock;
        }
        public List<Stock> getStock() {
            return stock;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }
        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGood_stock(int good_stock) {
            this.good_stock = good_stock;
        }
        public int getGood_stock() {
            return good_stock;
        }

        public void setReview_rate(String review_rate) {
            this.review_rate = review_rate;
        }
        public String getReview_rate() {
            return review_rate;
        }

        public void setIs_shipping(double is_shipping) {
            this.is_shipping = is_shipping;
        }
        public double getIs_shipping() {
            return is_shipping;
        }

        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }
        public List<Photos> getPhotos() {
            return photos;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }
        public String getSku() {
            return sku;
        }

        public void setCategory(double category) {
            this.category = category;
        }
        public double getCategory() {
            return category;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
        public String getShare_url() {
            return share_url;
        }

        public void setIs_liked(double is_liked) {
            this.is_liked = is_liked;
        }
        public double getIs_liked() {
            return is_liked;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
        public List<String> getTags() {
            return tags;
        }

        public void setIntro_url(String doublero_url) {
            this.doublero_url = doublero_url;
        }
        public String getIntro_url() {
            return doublero_url;
        }

        public void setPromos(List<String> promos) {
            this.promos = promos;
        }
        public List<String> getPromos() {
            return promos;
        }

        public void setScore(double score) {
            this.score = score;
        }
        public double getScore() {
            return score;
        }

        public void setShop(double shop) {
            this.shop = shop;
        }
        public double getShop() {
            return shop;
        }

        public void setProperties(List<Properties> properties) {
            this.properties = properties;
        }
        public List<Properties> getProperties() {
            return properties;
        }

        public void setOrder_id(double order_id) {
            this.order_id = order_id;
        }
        public double getOrder_id() {
            return order_id;
        }

        public void setApp_img(App_img app_img) {
            this.app_img = app_img;
        }
        public App_img getApp_img() {
            return app_img;
        }

        public void setAttachments(List<Attachments> attachments) {
            this.attachments = attachments;
        }
        public List<Attachments> getAttachments() {
            return attachments;
        }

        public void setComment_count(double comment_count) {
            this.comment_count = comment_count;
        }
        public double getComment_count() {
            return comment_count;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
        public String getOriginal_img() {
            return original_img;
        }

        public void setCurrent_price(String current_price) {
            this.current_price = current_price;
        }
        public String getCurrent_price() {
            return current_price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getPrice() {
            return price;
        }

        public void setBrand(double brand) {
            this.brand = brand;
        }
        public double getBrand() {
            return brand;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }
        public long getCreated_at() {
            return created_at;
        }

        public void setGoods_video(String goods_video) {
            this.goods_video = goods_video;
        }
        public String getGoods_video() {
            return goods_video;
        }

    public class Attrs {

        private String number;
        private boolean is_multiselect;
        private double attr_price_5;
        private int id;
        private double attr_price_3;
        private double attr_price_2;
        private double attr_price_4;
        private double attr_price_1;
        private String img;
        private String attr_name;
        public void setNumber(String number) {
            this.number = number;
        }
        public String getNumber() {
            return number;
        }

        public void setIs_multiselect(boolean is_multiselect) {
            this.is_multiselect = is_multiselect;
        }
        public boolean getIs_multiselect() {
            return is_multiselect;
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

        public void setAttr_price_3(double attr_price_3) {
            this.attr_price_3 = attr_price_3;
        }
        public double getAttr_price_3() {
            return attr_price_3;
        }

        public void setAttr_price_2(double attr_price_2) {
            this.attr_price_2 = attr_price_2;
        }
        public double getAttr_price_2() {
            return attr_price_2;
        }

        public void setAttr_price_4(double attr_price_4) {
            this.attr_price_4 = attr_price_4;
        }
        public double getAttr_price_4() {
            return attr_price_4;
        }

        public void setAttr_price_1(double attr_price_1) {
            this.attr_price_1 = attr_price_1;
        }
        public double getAttr_price_1() {
            return attr_price_1;
        }

        public void setImg(String img) {
            this.img = img;
        }
        public String getImg() {
            return img;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }
        public String getAttr_name() {
            return attr_name;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
//    public class Attachments {
//
//        private boolean is_multiselect;
//        private List<Attrs> attrs;
//        private double id;
//        private String name;
//        public void setIs_multiselect(boolean is_multiselect) {
//            this.is_multiselect = is_multiselect;
//        }
//        public boolean getIs_multiselect() {
//            return is_multiselect;
//        }
//
//        public void setAttrs(List<Attrs> attrs) {
//            this.attrs = attrs;
//        }
//        public List<Attrs> getAttrs() {
//            return attrs;
//        }
//
//        public void setId(double id) {
//            this.id = id;
//        }
//        public double getId() {
//            return id;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//        public String getName() {
//            return name;
//        }
//
//    }
/**
 * Copyright 2018 bejson.com
 */

    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class App_img {

        private String ipad_img;
        private String phone_img;
        private String img;
        public void setIpad_img(String ipad_img) {
            this.ipad_img = ipad_img;
        }
        public String getIpad_img() {
            return ipad_img;
        }

        public void setPhone_img(String phone_img) {
            this.phone_img = phone_img;
        }
        public String getPhone_img() {
            return phone_img;
        }

        public void setImg(String img) {
            this.img = img;
        }
        public String getImg() {
            return img;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */
    /**
     * Auto-generated: 2018-01-31 9:47:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Properties {

        private boolean is_multiselect;
        private List<Attrs> attrs;
        private double id;
        private String name;
        public void setIs_multiselect(boolean is_multiselect) {
            this.is_multiselect = is_multiselect;
        }
        public boolean getIs_multiselect() {
            return is_multiselect;
        }

        public void setAttrs(List<Attrs> attrs) {
            this.attrs = attrs;
        }
        public List<Attrs> getAttrs() {
            return attrs;
        }

        public void setId(double id) {
            this.id = id;
        }
        public double getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }
}
