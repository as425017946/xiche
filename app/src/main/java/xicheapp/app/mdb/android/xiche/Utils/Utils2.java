package xicheapp.app.mdb.android.xiche.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Utils2 {
    // 两次点击按钮之间的点击间隔不能少于4000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 4000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /**
     * 验证手机号的正则表达式
     * @param str
     * @return
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    public static boolean isIdCard(String id){
        return Pattern.matches(REGEX_ID_CARD, id);
    }

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static boolean isEmail (String email){
        return Pattern.matches(REGEX_EMAIL,email);
    }

}
