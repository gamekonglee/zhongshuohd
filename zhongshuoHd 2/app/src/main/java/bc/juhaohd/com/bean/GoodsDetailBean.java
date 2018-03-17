package bc.juhaohd.com.bean;

import java.util.List;

/**
 * Created by gamekonglee on 2018/1/30.
 */

public class GoodsDetailBean {

    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-01-30 15:7:41
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
        private long updated_at;
        private Default_photo default_photo;
        private int sales_count;
        private List<String> stock;
        private String goods_desc;
        private int good_stock;
        private String review_rate;
        private int is_shipping;
        private List<Photos> photos;
        private String sku;
        private int category;
        private int id;
        private String share_url;
        private int is_jh;
        private int is_liked;
        private List<String> tags;
        private String intro_url;
        private List<String> promos;
        private int score;
        private int shop;
        private List<Properties> properties;
        private int order_id;
        private App_img app_img;
        private List<Attachments> attachments;
        private int comment_count;
        private String original_img;
        private String current_price;
        private String price;
        private int brand;
        private String name;
        private long created_at;
        private String goods_video;
        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }
        public long getUpdated_at() {
            return updated_at;
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

        public void setStock(List<String> stock) {
            this.stock = stock;
        }
        public List<String> getStock() {
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

        public void setIs_shipping(int is_shipping) {
            this.is_shipping = is_shipping;
        }
        public int getIs_shipping() {
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

        public void setCategory(int category) {
            this.category = category;
        }
        public int getCategory() {
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

        public void setIs_jh(int is_jh) {
            this.is_jh = is_jh;
        }
        public int getIs_jh() {
            return is_jh;
        }

        public void setIs_liked(int is_liked) {
            this.is_liked = is_liked;
        }
        public int getIs_liked() {
            return is_liked;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
        public List<String> getTags() {
            return tags;
        }

        public void setIntro_url(String intro_url) {
            this.intro_url = intro_url;
        }
        public String getIntro_url() {
            return intro_url;
        }

        public void setPromos(List<String> promos) {
            this.promos = promos;
        }
        public List<String> getPromos() {
            return promos;
        }

        public void setScore(int score) {
            this.score = score;
        }
        public int getScore() {
            return score;
        }

        public void setShop(int shop) {
            this.shop = shop;
        }
        public int getShop() {
            return shop;
        }

        public void setProperties(List<Properties> properties) {
            this.properties = properties;
        }
        public List<Properties> getProperties() {
            return properties;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }
        public int getOrder_id() {
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

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }
        public int getComment_count() {
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

        public void setBrand(int brand) {
            this.brand = brand;
        }
        public int getBrand() {
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

    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-01-30 15:7:41
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
     * Auto-generated: 2018-01-30 15:7:41
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
     * Auto-generated: 2018-01-30 15:7:41
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Attrs {

        private boolean is_multiselect;
        private int id;
        private int attr_price;
        private String attr_name;
        public void setIs_multiselect(boolean is_multiselect) {
            this.is_multiselect = is_multiselect;
        }
        public boolean getIs_multiselect() {
            return is_multiselect;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setAttr_price(int attr_price) {
            this.attr_price = attr_price;
        }
        public int getAttr_price() {
            return attr_price;
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
     * Auto-generated: 2018-01-30 15:7:41
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Attachments {

        private boolean is_multiselect;
        private List<Attrs> attrs;
        private int id;
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

    }

    /**
     * Copyright 2018 bejson.com
     */
    /**
     * Auto-generated: 2018-01-30 15:7:41
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
     * Auto-generated: 2018-01-30 15:7:41
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Properties {

        private boolean is_multiselect;
        private List<Attrs> attrs;
        private int id;
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

    }
}
