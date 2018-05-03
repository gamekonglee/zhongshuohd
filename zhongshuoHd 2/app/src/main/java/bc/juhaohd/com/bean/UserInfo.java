package bc.juhaohd.com.bean;

import java.util.List;

/**
 * Created by bocang on 18-4-14.
 */

public class UserInfo {
    /**
     * Copyright 2018 bejson.com
     */

    /**
     * Auto-generated: 2018-04-14 16:24:23
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

        private User user;
        private List<Integer> count;
        private int error_code;
        private String debug_id;
        public void setUser(User user) {
            this.user = user;
        }
        public User getUser() {
            return user;
        }

        public void setCount(List<Integer> count) {
            this.count = count;
        }
        public List<Integer> getCount() {
            return count;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }
        public int getError_code() {
            return error_code;
        }

        public void setDebug_id(String debug_id) {
            this.debug_id = debug_id;
        }
        public String getDebug_id() {
            return debug_id;
        }

}
