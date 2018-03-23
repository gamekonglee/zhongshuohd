package bc.juhaohd.com.bean;

import java.util.List;

/**
 * Created by bocang on 18-3-21.
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
