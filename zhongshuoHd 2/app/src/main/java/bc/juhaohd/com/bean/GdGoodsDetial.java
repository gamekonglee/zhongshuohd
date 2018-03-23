package bc.juhaohd.com.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by bocang on 18-3-21.
 */
@Entity
public class GdGoodsDetial {

    @Id
    private Long gdId;

    private String GdJson;

    private int id;

    public String getGdJson() {
        return this.GdJson;
    }

    public void setGdJson(String GdJson) {
        this.GdJson = GdJson;
    }

    public Long getGdId() {
        return this.gdId;
    }

    public void setGdId(Long gdId) {
        this.gdId = gdId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Generated(hash = 1433875074)
    public GdGoodsDetial(Long gdId, String GdJson, int id) {
        this.gdId = gdId;
        this.GdJson = GdJson;
        this.id = id;
    }

    @Generated(hash = 801156551)
    public GdGoodsDetial() {
    }
    
}
