package xicheapp.app.mdb.android.xiche.bean;

public class GerenInfoBean {

    /**
     * data : {"person_img":"1542185921588","id_card_img":"1542097206095,1542097206081,","id_card":"130223199212286666","name":"孟","contact_mobile":"18920629331"}
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
         * person_img : 1542185921588
         * id_card_img : 1542097206095,1542097206081,
         * id_card : 130223199212286666
         * name : 孟
         * contact_mobile : 18920629331
         */

        private String person_img;
        private String id_card_img;
        private String id_card;
        private String name;
        private String contact_mobile;
        private String mail;

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getPerson_img() {
            return person_img;
        }

        public void setPerson_img(String person_img) {
            this.person_img = person_img;
        }

        public String getId_card_img() {
            return id_card_img;
        }

        public void setId_card_img(String id_card_img) {
            this.id_card_img = id_card_img;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact_mobile() {
            return contact_mobile;
        }

        public void setContact_mobile(String contact_mobile) {
            this.contact_mobile = contact_mobile;
        }
    }
}
