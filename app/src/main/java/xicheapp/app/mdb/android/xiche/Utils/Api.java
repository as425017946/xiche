package xicheapp.app.mdb.android.xiche.Utils;

public class Api {
    public static String apiurl = "http://39.107.70.80:8080/person";
    public static String ossUrl = "https://washcarimg.oss-cn-beijing.aliyuncs.com/";
//    public static String apiurl = "http://192.168.1.184:8090";
    //手机验证码
    public static String sms = apiurl+"/merchant/getSms";
    //验证验证码
    public static String smsOrlogin = apiurl+"/merchant/checkSms";
    //骑手注册
    public static String zhuce = apiurl+"/merchant/addShop";
    //账号密码登录
    public static String login = apiurl+"/merchant/shopLogin";
    //手机号验证码登录
    public static String login_sms = apiurl+"/merchant/shopSmsLogin";
    //重置密码
    public static String chongzhipassword = apiurl+"/merchant/updateShopPassword";
    //完善资料
    public static String wanshanziliao = apiurl+"/merchant/updateShop";
    //修改银行卡
    public static String bank = apiurl+"/merchant/updateShopCard";
    //查看店铺信息
    public static String selectshopinfo = apiurl+"/merchant/getMerchant";
    //修改头像
    public static String updataheadimg = apiurl+"/merchant/updatePhoto";
    //查看订单
    public static String selectorder = apiurl+"/merchant/getShopOrder";
    //查看订单详情
    public static String selectordermore = apiurl+"/merchant/getShopOrderDetail";
    //查看收益
    public static String selectshouyi = apiurl+"/merchant/getShopEarnings";
    //查看总收益
    public static String selectzongshouyi = apiurl+"/merchant/getShopSumEarnings";
    //查看评分
    public static String selectpingfen = apiurl+"/merchant/getComment";
    //修改登录手机号
    public static String updataphone = apiurl+"/merchant/updateShopMobile";
    //查询银行卡信息
    public static String selectbankmore = apiurl+"/merchant/getShopCard";
    //查询当前订单
    public static String selectdangqianorder = apiurl+"/merchant/getOrder";
    //开始洗车
    public static String starcar  = apiurl+"/merchant/updateOrder";
    //结束洗车
    public static String endcar = apiurl+"/merchant/updateOrderEnd";
    //取消订单
    public static String quxiaoorder = apiurl+"/merchant/deleteOrder";
    //排队订单数
    public static String paiduinum = apiurl+"/merchant/getOrderNum";
    //查看消息列表
    public static String selectmessages = apiurl+"/merchant/getShopPlatformInfo";
    //删除消息
    public static String deletemessages = apiurl+"/merchant/deleteShopPlatformInfo";
    //修改接单状态
    public static String updatestatus = apiurl+"/merchant/updateIsReceive";
    //骑手留言
    public static String liuyan = apiurl+"/merchant/updateServiceManWord";

    //查询有没有下一订单
    public static String nextorder = apiurl+"/merchant/getNewOrderFlag";
    //修改联系手机号
    public static String xiugailianxiphone = apiurl+"/merchant/updateServiceManContractMobile";


}
