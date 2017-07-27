package austin.com.utils;

import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 正则相关工具类
 * </pre>
 */
public class RegexUtil {

    /******************** 正则相关常量 ********************/
    /**
     * 正则：手机号（简单）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：17(0-9)</p>
     */

    //另一种写法：                                    "^((13[0-9])|(15[^4,\\D])|(17[^4,^9,\\D])|(18[0-9])|14[5,7])\\d{8}$"
    public static final String REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0-9])|(18[0-9])|(147))\\d{8}$";
    /**
     * 正则：电话号码
     */
    public static final String REGEX_TEL           = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_IDCARD15      = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_IDCARD18      = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL         = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则：URL
     */
    public static final String REGEX_URL           = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    /**
     * 正则：汉字
     */
    public static final String REGEX_CHZ           = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     */
    public static final String REGEX_USERNAME      = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final String REGEX_DATE          = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 正则：IP地址
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 400电话
     */
    private static final String REGEX_TEL_400 = "^400[016789]\\d{6}$";

    private RegexUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }








    public static boolean is400Tel(String string){
        return isMatch(REGEX_TEL_400, string);
    }

    /**
     * If u want more please visit http://toutiao.com/i6231678548520731137/
     */

    /**
     * 验证手机号（简单）
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(String string) {
        return isMatch(REGEX_MOBILE_SIMPLE, string);
    }

    /**
     * 验证手机号（精确）
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(String string) {
        return isMatch(REGEX_MOBILE_EXACT, string);
    }

    /**
     * 验证电话号码
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(String string) {
        return isMatch(REGEX_TEL, string);
    }

    /**
     * 验证身份证号码15位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(String string) {
        return isMatch(REGEX_IDCARD15, string);
    }

    /**
     * 验证身份证号码18位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(String string) {
        return isMatch(REGEX_IDCARD18, string);
    }

    /**
     * 验证邮箱
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    /**
     * 验证URL
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(String string) {
        return isMatch(REGEX_URL, string);
    }

    /**
     * 验证汉字
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(String string) {
        return isMatch(REGEX_USERNAME, string);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(String string) {
        return isMatch(REGEX_DATE, string);
    }

    /**
     * 验证IP地址
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(String string) {
        return isMatch(REGEX_IP, string);
    }



    /**
     * 是不是车牌号
     * @param s
     * @return
     */
    public static boolean isCarPlate(String s) {
        s = s.trim();
        String substring = s.substring(0, 1);
        String substring2 = s.substring(1, s.length());
        if (!RegexUtil.isChz(substring)) {
            return false;
        }
        if (!RegexUtil.isMatch("[a-zA-Z0-9]{6}", substring2)) {
            return false;
        }
        return true;
    }


    /*public static boolean isBankCard(String number){
        int sumOdd = 0;
        int sumEven = 0;
        int length = number.length();
        int[] wei = new int[length];
        for (int i = 0; i < number.length(); i++) {
            wei[i] = Integer.parseInt(number.substring(length - i - 1, length - i));// 从最末一位开始提取，每一位上的数值
            System.out.println("第" + i + "位数字是：" + wei[i]);
        }
        for (int i = 0; i < length / 2; i++) {
            sumOdd += wei[2 * i];
            if ((wei[2 * i + 1] * 2) > 9)
                wei[2 * i + 1] = wei[2 * i + 1] * 2 - 9;
            else
                wei[2 * i + 1] *= 2;
            sumEven += wei[2 * i + 1];
        }
        System.out.println("奇数位的和是：" + sumOdd);
        System.out.println("偶数位的和是：" + sumEven);
        return (sumOdd + sumEven + wei[wei.length - 1]) % 10 == 0;
    }*/

    /**
     * 是不是银行卡号（仿IOS方法，滴客所用）
     * @param number
     * @return
     */
    public static boolean isBankCard(String number){
        String subedNumber = "";
        int sumOdd = 0;
        int sumEven = 0;
        int allSum = 0;
        int length = number.length();
        int lastNum = Integer.parseInt(number.substring(number.length()-1));
        subedNumber = number.substring(0, number.length()-1);
        for (int i = length - 1; i >=1; i--) {
            int tempVal = Integer.parseInt(subedNumber.substring(i-1, i));
            if(length%2 == 1){
                if((i%2)==0){
                    tempVal*=2;
                    if(tempVal>=10){
                        tempVal-=9;
                    }
                    sumEven += tempVal;
                }else{
                    sumOdd += tempVal;
                }
            }else{
                if((i%2)==1){
                    tempVal*=2;
                    if(tempVal>=10){
                        tempVal-=9;
                    }
                    sumEven += tempVal;
                }else{
                    sumOdd += tempVal;
                }
            }
        }
        allSum = sumEven + sumOdd + lastNum;
        return (allSum % 10) == 0;
    }


    /**
     * string是否匹配regex
     *
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, String string) {
        return !EmptyUtil.isEmpty(string) && Pattern.matches(regex, string);
    }

}