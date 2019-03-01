package xicheapp.app.mdb.android.xiche.bean;

/**
 * 当前订单
 */
public class CurrentOrderBean {

    /**
     * data : {"type_name":"小型汽车","orderdetailId":133,"service_name":"普洗","personServiceId":5,"car_color":"红色","order_time":"2018-11-13 08:33:54","type":"1","uuid":"1000000054027251","ordersmainId":120,"carNo":"津A66666","createTime":"2018-11-13 16:33:53","car_image":"1542013554602","tel":"123","brand":"夏利","car_addr":"天津市塘沽区","status":7}
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
         * type_name : 小型汽车
         * orderdetailId : 133
         * service_name : 普洗
         * personServiceId : 5
         * car_color : 红色
         * order_time : 2018-11-13 08:33:54
         * type : 1
         * uuid : 1000000054027251
         * ordersmainId : 120
         * carNo : 津A66666
         * createTime : 2018-11-13 16:33:53
         * car_image : 1542013554602
         * tel : 123
         * brand : 夏利
         * car_addr : 天津市塘沽区
         * status : 7
         */

        private String type_name;
        private int orderdetailId;
        private String service_name;
        private int personServiceId;
        private String car_color;
        private String order_time;
        private String type;
        private String uuid;
        private int ordersmainId;
        private String carNo;
        private String createTime;
        private String car_image;
        private String tel;
        private String brand;
        private String car_addr;
        private int status;
        private String lonLat;

        public String getLonLat() {
            return lonLat;
        }

        public void setLonLat(String lonLat) {
            this.lonLat = lonLat;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getOrderdetailId() {
            return orderdetailId;
        }

        public void setOrderdetailId(int orderdetailId) {
            this.orderdetailId = orderdetailId;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public int getPersonServiceId() {
            return personServiceId;
        }

        public void setPersonServiceId(int personServiceId) {
            this.personServiceId = personServiceId;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
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

        public int getOrdersmainId() {
            return ordersmainId;
        }

        public void setOrdersmainId(int ordersmainId) {
            this.ordersmainId = ordersmainId;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCar_image() {
            return car_image;
        }

        public void setCar_image(String car_image) {
            this.car_image = car_image;
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

        public String getCar_addr() {
            return car_addr;
        }

        public void setCar_addr(String car_addr) {
            this.car_addr = car_addr;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
