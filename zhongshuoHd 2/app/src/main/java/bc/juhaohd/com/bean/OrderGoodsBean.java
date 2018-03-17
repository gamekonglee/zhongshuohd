package bc.juhaohd.com.bean;

/**
 * Created by bocang on 18-2-3.
 */

/**
 * Copyright 2018 bejson.com
 */
        import java.util.List;

/**
 * Auto-generated: 2018-02-03 16:19:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OrderGoodsBean {

    private List<Orders> orders;
    private Paged paged;
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
    public List<Orders> getOrders() {
        return orders;
    }

    public void setPaged(Paged paged) {
        this.paged = paged;
    }
    public Paged getPaged() {
        return paged;
    }
/**
 * Copyright 2018 bejson.com
 */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Consignee {

        private String address;
        private String regions;
        private String zip_code;
        private String tel;
        private String mobile;
        private String name;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setRegions(String regions) {
            this.regions = regions;
        }
        public String getRegions() {
            return regions;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }
        public String getZip_code() {
            return zip_code;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
        public String getTel() {
            return tel;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getMobile() {
            return mobile;
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
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Product {

        private String price;
        private int id;
        private String name;
        private List<ScPhotos> photos;
        public void setPrice(String price) {
            this.price = price;
        }
        public String getPrice() {
            return price;
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

        public void setScPhotos(List<ScPhotos> photos) {
            this.photos = photos;
        }
        public List<ScPhotos> getScPhotos() {
            return photos;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Payment {

        private String name;
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
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Goods {

        private int total_amount;
        private String property;
        private int id;
        private String original_price;
        private boolean is_reviewed;
        private String product_price;
        private String total_price;
        private Product product;
        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }
        public int getTotal_amount() {
            return total_amount;
        }

        public void setProperty(String property) {
            this.property = property;
        }
        public String getProperty() {
            return property;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }
        public String getOriginal_price() {
            return original_price;
        }

        public void setIs_reviewed(boolean is_reviewed) {
            this.is_reviewed = is_reviewed;
        }
        public boolean getIs_reviewed() {
            return is_reviewed;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }
        public String getProduct_price() {
            return product_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }
        public String getTotal_price() {
            return total_price;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
        public Product getProduct() {
            return product;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Shipping {

        private String address;
        private String consignee;
        private String action_user;
        private String invoice_no;
        private String shipping_name;
        private int user_id;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }
        public String getConsignee() {
            return consignee;
        }

        public void setAction_user(String action_user) {
            this.action_user = action_user;
        }
        public String getAction_user() {
            return action_user;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }
        public String getInvoice_no() {
            return invoice_no;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }
        public String getShipping_name() {
            return shipping_name;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
        public int getUser_id() {
            return user_id;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Cashgift {

        private int price;
        public void setPrice(int price) {
            this.price = price;
        }
        public int getPrice() {
            return price;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */
    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Promos {

        private String promo;
        private String price;
        public void setPromo(String promo) {
            this.promo = promo;
        }
        public String getPromo() {
            return promo;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getPrice() {
            return price;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Score {

        private String score;
        public void setScore(String score) {
            this.score = score;
        }
        public String getScore() {
            return score;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Avatar {

        private String avatar;
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getAvatar() {
            return avatar;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Use_score {

        private int score;
        public void setScore(int score) {
            this.score = score;
        }
        public int getScore() {
            return score;
        }

    }
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Orders {

        private long shipping_at;
        private Invoice invoice;
        private Consignee consignee;
        private Payment payment;
        private String discount;
        private List<Goods> goods;
        private int shipping_id;
        private Shipping shipping;
        private int status;
        private int id;
        private Cashgift cashgift;
        private String user_name;
        private int level;
        private List<Promos> promos;
        private Score score;
        private String postscript;
        private Avatar avatar;
        private String sn;
        private double total;
        private long paied_at;
        private long created_at;
        private Use_score use_score;
        private int user_id;
        public void setShipping_at(long shipping_at) {
            this.shipping_at = shipping_at;
        }
        public long getShipping_at() {
            return shipping_at;
        }

        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }
        public Invoice getInvoice() {
            return invoice;
        }

        public void setConsignee(Consignee consignee) {
            this.consignee = consignee;
        }
        public Consignee getConsignee() {
            return consignee;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }
        public Payment getPayment() {
            return payment;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
        public String getDiscount() {
            return discount;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }
        public List<Goods> getGoods() {
            return goods;
        }

        public void setShipping_id(int shipping_id) {
            this.shipping_id = shipping_id;
        }
        public int getShipping_id() {
            return shipping_id;
        }

        public void setShipping(Shipping shipping) {
            this.shipping = shipping;
        }
        public Shipping getShipping() {
            return shipping;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setCashgift(Cashgift cashgift) {
            this.cashgift = cashgift;
        }
        public Cashgift getCashgift() {
            return cashgift;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
        public String getUser_name() {
            return user_name;
        }

        public void setLevel(int level) {
            this.level = level;
        }
        public int getLevel() {
            return level;
        }

        public void setPromos(List<Promos> promos) {
            this.promos = promos;
        }
        public List<Promos> getPromos() {
            return promos;
        }

        public void setScore(Score score) {
            this.score = score;
        }
        public Score getScore() {
            return score;
        }

        public void setPostscript(String postscript) {
            this.postscript = postscript;
        }
        public String getPostscript() {
            return postscript;
        }

        public void setAvatar(Avatar avatar) {
            this.avatar = avatar;
        }
        public Avatar getAvatar() {
            return avatar;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
        public String getSn() {
            return sn;
        }

        public void setTotal(double total) {
            this.total = total;
        }
        public double getTotal() {
            return total;
        }

        public void setPaied_at(long paied_at) {
            this.paied_at = paied_at;
        }
        public long getPaied_at() {
            return paied_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }
        public long getCreated_at() {
            return created_at;
        }

        public void setUse_score(Use_score use_score) {
            this.use_score = use_score;
        }
        public Use_score getUse_score() {
            return use_score;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
        public int getUser_id() {
            return user_id;
        }

    }
    /**
     * Copyright 2018 bejson.com 
     */

    /**
     * Auto-generated: 2018-02-03 16:19:46
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Paged {

        private String size;
        private int total;
        private String page;
        private int more;
        public void setSize(String size) {
            this.size = size;
        }
        public String getSize() {
            return size;
        }

        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

        public void setPage(String page) {
            this.page = page;
        }
        public String getPage() {
            return page;
        }

        public void setMore(int more) {
            this.more = more;
        }
        public int getMore() {
            return more;
        }

    }
}