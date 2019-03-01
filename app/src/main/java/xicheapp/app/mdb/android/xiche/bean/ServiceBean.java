package xicheapp.app.mdb.android.xiche.bean;

/**
 * 店铺评价
 */
public class ServiceBean {

    /**
     * data : {"comment_r":"1.5","comment_t":"1.5","grade":1.5}
     * message : 请求成功
     * state : 1
     */

    private DataBean data;
    private String message;
    private int state;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * comment_r : 1.5
         * comment_t : 1.5
         * grade : 1.5
         */

        private double comment_r;
        private double comment_t;
        private double grade;

        public double getComment_r() {
            return comment_r;
        }

        public void setComment_r(double comment_r) {
            this.comment_r = comment_r;
        }

        public double getComment_t() {
            return comment_t;
        }

        public void setComment_t(double comment_t) {
            this.comment_t = comment_t;
        }

        public double getGrade() {
            return grade;
        }

        public void setGrade(double grade) {
            this.grade = grade;
        }
    }
}
