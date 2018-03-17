package bc.juhaohd.com.bean;

/**
 * Created by bocang on 18-2-3.
 */

public class UpLoadBean {
//    {
//        "error_code": 400,
//            "error_desc": "name \u4e0d\u80fd\u4e3a\u7a7a\u3002",
//            "debug_id": "5a75861079a3f"
//    }
    int error_code;
    String error_desc;
    String debug_id;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    public String getDebug_id() {
        return debug_id;
    }

    public void setDebug_id(String debug_id) {
        this.debug_id = debug_id;
    }
}
