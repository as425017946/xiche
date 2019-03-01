package xicheapp.app.mdb.android.xiche.bean;

import java.util.List;

/**
 * 我的订单
 */
public class MyOrderBean {


    /**
     * data : {"pageInfo":{"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"shop_code":"hGBfu7YL","service_name":"普洗","car_color":"红色","order_time":"2018-11-13 08:33:54","type":"1","uuid":"1000000054027251","money":100,"carNo":"津A66666","createTime":"2018-11-13 16:33:53","car_image":"1.jpg","coupon_money":0,"brand":"夏利","status":2}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}}
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
         * pageInfo : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"shop_code":"hGBfu7YL","service_name":"普洗","car_color":"红色","order_time":"2018-11-13 08:33:54","type":"1","uuid":"1000000054027251","money":100,"carNo":"津A66666","createTime":"2018-11-13 16:33:53","car_image":"1.jpg","coupon_money":0,"brand":"夏利","status":2}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
         */

        private PageInfoBean pageInfo;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public static class PageInfoBean {
            /**
             * endRow : 1
             * firstPage : 1
             * hasNextPage : false
             * hasPreviousPage : false
             * isFirstPage : true
             * isLastPage : true
             * lastPage : 1
             * list : [{"shop_code":"hGBfu7YL","service_name":"普洗","car_color":"红色","order_time":"2018-11-13 08:33:54","type":"1","uuid":"1000000054027251","money":100,"carNo":"津A66666","createTime":"2018-11-13 16:33:53","car_image":"1.jpg","coupon_money":0,"brand":"夏利","status":2}]
             * navigatePages : 1
             * navigatepageNums : [1]
             * nextPage : 0
             * pageNum : 1
             * pageSize : 10
             * pages : 1
             * prePage : 0
             * size : 1
             * startRow : 1
             * total : 1
             */

            private int endRow;
            private int firstPage;
            private boolean hasNextPage;
            private boolean hasPreviousPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private int lastPage;
            private int navigatePages;
            private int nextPage;
            private int pageNum;
            private int pageSize;
            private int pages;
            private int prePage;
            private int size;
            private int startRow;
            private int total;
            private List<ListBean> list;
            private List<Integer> navigatepageNums;

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getFirstPage() {
                return firstPage;
            }

            public void setFirstPage(int firstPage) {
                this.firstPage = firstPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public int getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(int navigatePages) {
                this.navigatePages = navigatePages;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }

            public static class ListBean {
                /**
                 * shop_code : hGBfu7YL
                 * service_name : 普洗
                 * car_color : 红色
                 * order_time : 2018-11-13 08:33:54
                 * type : 1
                 * uuid : 1000000054027251
                 * money : 100.0
                 * carNo : 津A66666
                 * createTime : 2018-11-13 16:33:53
                 * car_image : 1.jpg
                 * coupon_money : 0.0
                 * brand : 夏利
                 * status : 2
                 */

                private String shop_code;
                private String service_name;
                private String car_color;
                private String order_time;
                private String type;
                private String uuid;
                private double money;
                private String carNo;
                private String createTime;
                private String car_image;
                private double coupon_money;
                private String brand;
                private int status;

                public String getType_name() {
                    return type_name;
                }

                public void setType_name(String type_name) {
                    this.type_name = type_name;
                }

                private String type_name;

                public String getShop_code() {
                    return shop_code;
                }

                public void setShop_code(String shop_code) {
                    this.shop_code = shop_code;
                }

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

                public double getMoney() {
                    return money;
                }

                public void setMoney(double money) {
                    this.money = money;
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

                public double getCoupon_money() {
                    return coupon_money;
                }

                public void setCoupon_money(double coupon_money) {
                    this.coupon_money = coupon_money;
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
    }
}
