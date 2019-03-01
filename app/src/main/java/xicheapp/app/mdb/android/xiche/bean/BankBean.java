package xicheapp.app.mdb.android.xiche.bean;

/**
 * 查询银行卡信息
 */
public class BankBean {

    /**
     * data : {"card_no":"3124234234234","card_produce":"中国工商银行","card_owner":"建设银行","card_mobile":"18526468617"}
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
         * card_no : 3124234234234
         * card_produce : 中国工商银行
         * card_owner : 建设银行
         * card_mobile : 18526468617
         */

        private String card_no;
        private String card_produce;
        private String card_owner;
        private String card_mobile;

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getCard_produce() {
            return card_produce;
        }

        public void setCard_produce(String card_produce) {
            this.card_produce = card_produce;
        }

        public String getCard_owner() {
            return card_owner;
        }

        public void setCard_owner(String card_owner) {
            this.card_owner = card_owner;
        }

        public String getCard_mobile() {
            return card_mobile;
        }

        public void setCard_mobile(String card_mobile) {
            this.card_mobile = card_mobile;
        }
    }
}
