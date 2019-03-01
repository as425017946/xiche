package xicheapp.app.mdb.android.xiche.bean;

/**
 * 订单详情
 */
public class OrderMoresBean {

    /**
     * data : {"service_name":"普洗","car_color":"红色","type":"1","uuid":"1000000054027251","orderTime":"2018-11-13 08:33:54","money":100,"createTime":"2018-11-13 16:33:53","carNo":"津A66666","car_image":"1542013554602","coupon_money":0,"tel":"123","brand":"夏利","status":7}
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
         * service_name : 普洗
         * car_color : 红色
         * type : 1
         * uuid : 1000000054027251
         * orderTime : 2018-11-13 08:33:54
         * money : 100.0
         * createTime : 2018-11-13 16:33:53
         * carNo : 津A66666
         * car_image : 1542013554602
         * coupon_money : 0.0
         * tel : 123
         * brand : 夏利
         * status : 7
         */

        private String service_name;
        private String car_color;
        private String type;
        private String uuid;
        private String orderTime;
        private double money;
        private String createTime;
        private String carNo;
        private String car_image;
        private double coupon_money;
        private String tel;
        private String brand;
        private int status;
        private String type_name;

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        private String returnTime;

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCar_image() {
            return car_image;
        }

        public void setCar_image(String car_image) {
            this.car_image = car_image;
        }

        public double getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(double coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
