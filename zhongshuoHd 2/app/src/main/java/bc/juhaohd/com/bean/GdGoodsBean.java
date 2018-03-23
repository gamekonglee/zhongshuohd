package bc.juhaohd.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by bocang on 18-3-20.
 */
@Entity
public class GdGoodsBean {
    @Id
    private Long gid;
    private String filter;
    private int id;
    private String name;
    private String currentPrice;
    private String orignalImg;
    private String sortKey;
    private String sortValue;
    
    public String getOrignalImg() {
        return this.orignalImg;
    }
    public void setOrignalImg(String orignalImg) {
        this.orignalImg = orignalImg;
    }
    public String getCurrentPrice() {
        return this.currentPrice;
    }
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Long getGid() {
        return this.gid;
    }
    public void setGid(Long gid) {
        this.gid = gid;
    }
    public String getFilter() {
        return this.filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }
    public String getSortValue() {
        return this.sortValue;
    }
    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }
    public String getSortKey() {
        return this.sortKey;
    }
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    @Generated(hash = 242939104)
    public GdGoodsBean(Long gid, String filter, int id, String name,
            String currentPrice, String orignalImg, String sortKey, String sortValue) {
        this.gid = gid;
        this.filter = filter;
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.orignalImg = orignalImg;
        this.sortKey = sortKey;
        this.sortValue = sortValue;
    }
    @Generated(hash = 1261258339)
    public GdGoodsBean() {
    }

}
