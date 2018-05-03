package bc.juhaohd.com.bean;

/**
 * Created by bocang on 18-2-5.
 */

public class FangAnBean {
//    {"fangan":{"id":22,"path":"\/data\/fanganimg\/fangan1517812090.jpg"},"error_code":0,"debug_id":"5a77f97aa553f"}
    int error_code;
    String debug_id;

    FangAn fangan;
    public class FangAn{
        int id;
        String path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getDebug_id() {
        return debug_id;
    }

    public void setDebug_id(String debug_id) {
        this.debug_id = debug_id;
    }

    public FangAn getFangan() {
        return fangan;
    }

    public void setFangan(FangAn fangan) {
        this.fangan = fangan;
    }
}

