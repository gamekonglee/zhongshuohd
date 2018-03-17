package bc.juhaohd.com.bean;

import java.util.List;

/**
 * Created by DEMON on 2018/1/23.
 */
public class CollectGoodsBean {

    /**
     * goods_id : 2514
     * goods : {"sales_count":0,"shop":1,"review_rate":"0%","goods_desc":"<p><img src","current_price":"1380.00","score":0,"group_buy":null,"original_img":"images/201801/source_img/2514_G_1516171135919.png","id":2514,"title":"","is_shipping":0,"share_url":"http: //www.juhao.com/goods.php?id=2514","name":"10-B-5001-6","created_at":1516171135,"comment_count":0,"sku":"ECS002514","is_liked":1,"photos":[{"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/thumb_img/2514_thumb_G_1516171135382.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg"},{"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/thumb_img/2514_thumb_P_1516171135523.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_P_1516171135354.png"}],"intro_url":"http: //api.juhao.com/v2/product.intro.2514","category":166,"updated_at":1516176320,"price":"2208.00","default_photo":{"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg"},"good_stock":10000,"brand":0,"app_img":{"img":"http: //www.juhao.com/images/201801/source_img/2514_G_1516171135919.png","ipad_img":"http: //www.juhao.com/data/goodsimg/1516171135341221154.png!400X400.png","phone_img":"http: //www.juhao.com/data/goodsimg/1516171135341221154.png!280X280.png"}}
     * user_id : 5714
     * properties : [{"is_multiselect":false,"id":17,"attrs":[{"is_multiselect":false,"id":47961,"attr_price":0,"attr_name":"W50H55"}],"name":"规格"}]
     * add_time : 1516700119
     * is_attention : 1
     * rec_id : 477
     */
    private int goods_id;
    public GoodsBean goods;
    private int user_id;
    public List<PropertiesEntity> properties;
    private int add_time;
    private int is_attention;
    private int rec_id;

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProperties(List<PropertiesEntity> properties) {
        this.properties = properties;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public int getUser_id() {
        return user_id;
    }

    public List<PropertiesEntity> getProperties() {
        return properties;
    }

    public int getAdd_time() {
        return add_time;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public int getRec_id() {
        return rec_id;
    }

    public class GoodsEntity {
        /**
         * sales_count : 0
         * shop : 1
         * review_rate : 0%
         * goods_desc : <p><img src
         * current_price : 1380.00
         * score : 0
         * group_buy : null
         * original_img : images/201801/source_img/2514_G_1516171135919.png
         * id : 2514
         * title :
         * is_shipping : 0
         * share_url : http: //www.juhao.com/goods.php?id=2514
         * name : 10-B-5001-6
         * created_at : 1516171135
         * comment_count : 0
         * sku : ECS002514
         * is_liked : 1
         * photos : [{"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/thumb_img/2514_thumb_G_1516171135382.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg"},{"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/thumb_img/2514_thumb_P_1516171135523.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_P_1516171135354.png"}]
         * intro_url : http: //api.juhao.com/v2/product.intro.2514
         * category : 166
         * updated_at : 1516176320
         * price : 2208.00
         * default_photo : {"height":null,"width":null,"thumb":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg","large":"http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg"}
         * good_stock : 10000
         * brand : 0
         * app_img : {"img":"http: //www.juhao.com/images/201801/source_img/2514_G_1516171135919.png","ipad_img":"http: //www.juhao.com/data/goodsimg/1516171135341221154.png!400X400.png","phone_img":"http: //www.juhao.com/data/goodsimg/1516171135341221154.png!280X280.png"}
         */
        private int sales_count;
        private int shop;
        private String review_rate;
        private String goods_desc;
        private String current_price;
        private int score;
        private String group_buy;
        private String original_img;
        private int id;
        private String title;
        private int is_shipping;
        private String share_url;
        private String name;
        private int created_at;
        private int comment_count;
        private String sku;
        private int is_liked;
        private List<PhotosEntity> photos;
        private String intro_url;
        private int category;
        private int updated_at;
        private String price;
        private Default_photoEntity default_photo;
        private int good_stock;
        private int brand;
        private App_imgEntity app_img;

        public void setSales_count(int sales_count) {
            this.sales_count = sales_count;
        }

        public void setShop(int shop) {
            this.shop = shop;
        }

        public void setReview_rate(String review_rate) {
            this.review_rate = review_rate;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public void setCurrent_price(String current_price) {
            this.current_price = current_price;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setGroup_buy(String group_buy) {
            this.group_buy = group_buy;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setIs_shipping(int is_shipping) {
            this.is_shipping = is_shipping;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public void setIs_liked(int is_liked) {
            this.is_liked = is_liked;
        }

        public void setPhotos(List<PhotosEntity> photos) {
            this.photos = photos;
        }

        public void setIntro_url(String intro_url) {
            this.intro_url = intro_url;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setDefault_photo(Default_photoEntity default_photo) {
            this.default_photo = default_photo;
        }

        public void setGood_stock(int good_stock) {
            this.good_stock = good_stock;
        }

        public void setBrand(int brand) {
            this.brand = brand;
        }

        public void setApp_img(App_imgEntity app_img) {
            this.app_img = app_img;
        }

        public int getSales_count() {
            return sales_count;
        }

        public int getShop() {
            return shop;
        }

        public String getReview_rate() {
            return review_rate;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public String getCurrent_price() {
            return current_price;
        }

        public int getScore() {
            return score;
        }

        public String getGroup_buy() {
            return group_buy;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getIs_shipping() {
            return is_shipping;
        }

        public String getShare_url() {
            return share_url;
        }

        public String getName() {
            return name;
        }

        public int getCreated_at() {
            return created_at;
        }

        public int getComment_count() {
            return comment_count;
        }

        public String getSku() {
            return sku;
        }

        public int getIs_liked() {
            return is_liked;
        }

        public List<PhotosEntity> getPhotos() {
            return photos;
        }

        public String getIntro_url() {
            return intro_url;
        }

        public int getCategory() {
            return category;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public String getPrice() {
            return price;
        }

        public Default_photoEntity getDefault_photo() {
            return default_photo;
        }

        public int getGood_stock() {
            return good_stock;
        }

        public int getBrand() {
            return brand;
        }

        public App_imgEntity getApp_img() {
            return app_img;
        }

        public class PhotosEntity {
            /**
             * height : null
             * width : null
             * thumb : http: //www.juhao.com/images/201801/thumb_img/2514_thumb_G_1516171135382.jpg
             * large : http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg
             */
            private String height;
            private String width;
            private String thumb;
            private String large;

            public void setHeight(String height) {
                this.height = height;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getHeight() {
                return height;
            }

            public String getWidth() {
                return width;
            }

            public String getThumb() {
                return thumb;
            }

            public String getLarge() {
                return large;
            }
        }

        public class Default_photoEntity {
            /**
             * height : null
             * width : null
             * thumb : http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg
             * large : http: //www.juhao.com/images/201801/goods_img/2514_G_1516171135969.jpg
             */
            private String height;
            private String width;
            private String thumb;
            private String large;

            public void setHeight(String height) {
                this.height = height;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getHeight() {
                return height;
            }

            public String getWidth() {
                return width;
            }

            public String getThumb() {
                return thumb;
            }

            public String getLarge() {
                return large;
            }
        }

        public class App_imgEntity {
            /**
             * img : http: //www.juhao.com/images/201801/source_img/2514_G_1516171135919.png
             * ipad_img : http: //www.juhao.com/data/goodsimg/1516171135341221154.png!400X400.png
             * phone_img : http: //www.juhao.com/data/goodsimg/1516171135341221154.png!280X280.png
             */
            private String img;
            private String ipad_img;
            private String phone_img;

            public void setImg(String img) {
                this.img = img;
            }

            public void setIpad_img(String ipad_img) {
                this.ipad_img = ipad_img;
            }

            public void setPhone_img(String phone_img) {
                this.phone_img = phone_img;
            }

            public String getImg() {
                return img;
            }

            public String getIpad_img() {
                return ipad_img;
            }

            public String getPhone_img() {
                return phone_img;
            }
        }
    }

    public class PropertiesEntity {
        /**
         * is_multiselect : false
         * id : 17
         * attrs : [{"is_multiselect":false,"id":47961,"attr_price":0,"attr_name":"W50H55"}]
         * name : 规格
         */
        private boolean is_multiselect;
        private int id;
        private List<ScAttrs> attrs;
        private String name;

        public void setIs_multiselect(boolean is_multiselect) {
            this.is_multiselect = is_multiselect;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAttrs(List<ScAttrs> attrs) {
            this.attrs = attrs;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIs_multiselect() {
            return is_multiselect;
        }

        public int getId() {
            return id;
        }

        public List<ScAttrs> getAttrs() {
            return attrs;
        }

        public String getName() {
            return name;
        }

        public class AttrsEntity {
            /**
             * is_multiselect : false
             * id : 47961
             * attr_price : 0
             * attr_name : W50H55
             */
            private boolean is_multiselect;
            private int id;
            private int attr_price;
            private String attr_name;

            public void setIs_multiselect(boolean is_multiselect) {
                this.is_multiselect = is_multiselect;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setAttr_price(int attr_price) {
                this.attr_price = attr_price;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public boolean isIs_multiselect() {
                return is_multiselect;
            }

            public int getId() {
                return id;
            }

            public int getAttr_price() {
                return attr_price;
            }

            public String getAttr_name() {
                return attr_name;
            }
        }
    }
}
