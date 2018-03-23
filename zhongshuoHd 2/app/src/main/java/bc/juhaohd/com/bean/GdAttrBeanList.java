package bc.juhaohd.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by bocang on 18-3-20.
 */
@Entity
public class GdAttrBeanList {
    @Id
    private Long alid;

    private String attr_value;
    private int id;
    private int attr_bean_index;
    
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAttr_value() {
        return this.attr_value;
    }
    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }
    public Long getAlid() {
        return this.alid;
    }
    public void setAlid(Long alid) {
        this.alid = alid;
    }
    public int getAttr_bean_index() {
        return this.attr_bean_index;
    }
    public void setAttr_bean_index(int attr_bean_index) {
        this.attr_bean_index = attr_bean_index;
    }
    @Generated(hash = 1210397736)
    public GdAttrBeanList(Long alid, String attr_value, int id, int attr_bean_index) {
        this.alid = alid;
        this.attr_value = attr_value;
        this.id = id;
        this.attr_bean_index = attr_bean_index;
    }
    @Generated(hash = 627765814)
    public GdAttrBeanList() {
    }
}
