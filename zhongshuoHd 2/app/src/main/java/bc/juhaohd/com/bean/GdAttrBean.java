package bc.juhaohd.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by bocang on 18-3-20.
 */
@Entity
public class GdAttrBean {
    @Id
    private Long aid;

    private String filter_attr_name;
    private int index;
    private int attr_list_index;
    public int getAttr_list_index() {
        return this.attr_list_index;
    }
    public void setAttr_list_index(int attr_list_index) {
        this.attr_list_index = attr_list_index;
    }
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getFilter_attr_name() {
        return this.filter_attr_name;
    }
    public void setFilter_attr_name(String filter_attr_name) {
        this.filter_attr_name = filter_attr_name;
    }
    public Long getAid() {
        return this.aid;
    }
    public void setAid(Long aid) {
        this.aid = aid;
    }
    @Generated(hash = 1506060092)
    public GdAttrBean(Long aid, String filter_attr_name, int index,
            int attr_list_index) {
        this.aid = aid;
        this.filter_attr_name = filter_attr_name;
        this.index = index;
        this.attr_list_index = attr_list_index;
    }
    @Generated(hash = 1519750236)
    public GdAttrBean() {
    }

}
