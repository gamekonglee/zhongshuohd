/**
 * Copyright 2018 bejson.com
 */
package bc.juhaohd.com.bean;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Auto-generated: 2018-01-19 11:53:18
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */

public class AttrBean {

    private Long aid;

    private String filter_attr_name;
    private int index;
    private List<Attr_list> attr_list;
    public void setFilter_attr_name(String filter_attr_name) {
        this.filter_attr_name = filter_attr_name;
    }
    public String getFilter_attr_name() {
        return filter_attr_name;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public void setAttr_list(List<Attr_list> attr_list) {
        this.attr_list = attr_list;
    }
    public List<Attr_list> getAttr_list() {
        return attr_list;
    }

    /**
     * Copyright 2018 bejson.com
     */
    /**
     * Auto-generated: 2018-01-19 11:53:18
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

}