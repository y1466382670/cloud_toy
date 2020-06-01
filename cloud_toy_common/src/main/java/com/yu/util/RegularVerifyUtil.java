package com.yu.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验
 */
public class RegularVerifyUtil {

    /**
     * 去除空格
     * @param content
     * @return
     */
    public static String trim(String content){
        if (content==null){
            return "";
        }
        return content.trim();
    }

    /**
     * 手机号正则验证
     * @param regular
     * @param content
     * @return
     */
    public static boolean regularExpression(String regular,String content){
        Pattern pattern = Pattern.compile(regular);
        Matcher match = pattern.matcher(content);
        boolean isMatch = match.matches();
        if (isMatch) {
            return true;
        }
        return false;
    }

    /**
     * 验证手机号是否合法
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        mobile= RegularVerifyUtil.trim(mobile);
        if (mobile.length() != 11) {
            return false;
        }

        if(!isNumber(mobile)){
            return false;
        }

        /**
         * 移动号段正则表达式
         */
        String chinaMobile = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
        boolean chinaMobileFlag = RegularVerifyUtil.regularExpression(chinaMobile, mobile);
        if (chinaMobileFlag) {
            return true;
        }

        /**
         * 联通号段正则表达式
         */
        String chinaUnicom = "^((13[0-2])|(145)|(15[5-6])|(166)|(176)|(175)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
        boolean chinaUnicomFlag = RegularVerifyUtil.regularExpression(chinaUnicom, mobile);
        if (chinaUnicomFlag) {
            return true;
        }
        /**
         * 电信号段正则表达式
         */
        String chinaTelecom = "^((133)|(153)|(177)|(173)|(18[0,1,9])|(149)|(19[1,9]))\\d{8}$";
        boolean chinaTelecomFlag = RegularVerifyUtil.regularExpression(chinaTelecom, mobile);
        if (chinaTelecomFlag) {
            return true;
        }

        /**
         * 虚拟运营商正则表达式
         */
        String virtualMobile = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";
        boolean virtualMobileFlag = RegularVerifyUtil.regularExpression(virtualMobile, mobile);
        if (virtualMobileFlag) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否是纯数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证是否有非法字符
     * @param str 可包含数字、中文、下划线、长度最低3位，最长10位
     * @return
     */
    public static boolean isCharacter(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,10}");
        return pattern.matcher(str).matches();
    }
    /**
     * 验证不是汉字
     * @param str 中文
     * @return
     */
    public static boolean isChineseCharacter(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{1,200}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证15位身份证号
     *
     * @param str
     * @return
     */
    public static boolean isIdNumberFive(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证18位身份证号
     *
     * @param str
     * @return
     */
    public static boolean isIdNumberEight(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证邮箱格式
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("\\w+@\\w+(\\.\\w+)+");
        return pattern.matcher(str).matches();
    }

    /**
     * 校验车牌号
     * @param content
     * @return
     */
    public static boolean checkPlateNumberFormat(String content) {
        String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
        return Pattern.matches(pattern, content);
    }

    /**
     * 是否是有效的统一社会信用代码
     * @param socialCreditCode 统一社会信用代码
     * @return
     */
    public static boolean isValidSocialCreditCode(String socialCreditCode) {
        if ((socialCreditCode.equals("")) || socialCreditCode.length() != 18) {
            return false;
        }
        String baseCode = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        char[] baseCodeArray = baseCode.toCharArray();
        Map<Character, Integer> codes = new HashMap<Character, Integer>();
        for (int i = 0; i < baseCode.length(); i++) {
            codes.put(baseCodeArray[i], i);
        }
        char[] businessCodeArray = socialCreditCode.toCharArray();
        Character check = businessCodeArray[17];
        if (baseCode.indexOf(check) == -1) {
            return false;
        }
        int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            Character key = businessCodeArray[i];
            if (baseCode.indexOf(key) == -1) {
                return false;
            }
            sum += (codes.get(key) * wi[i]);
        }
        int value = 31 - sum % 31;
        return value == codes.get(check);
    }

    /**
     * 判断一个数是否为整数或小数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        if(str.indexOf(".")>0){//判断是否有小数点
            if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
                return pattern.matcher(str.replace(".","")).matches();
            }else {
                return false;
            }
        }else {
            return pattern.matcher(str).matches();
        }
    }


    /**
     * 保留指定位数
     * @param str   要保留的值
     * @param number 位数   0 表示一位 00 表示两位  000 表示三位
     * @return
     */
    public static String save(double str ,String number){
        String param = "#."+number;
        DecimalFormat df = new DecimalFormat(param);
        return df.format(str);
    }

    /**
     * 判断是否是整数或者是否保留最多四位的小数
     * @param str
     * @return
     */
    public static boolean judgeFourDecimal(String str){
        boolean flag = false;
        try {
            if(str != null){
                // 判断是否是整数或者是否保留最多四位的小数
                Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,4})?)$");
                if(pattern.matcher(str).matches()){
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 判断字符串是否为 0.0 0.00  0.000 0.0000 ......  不是为 true  是为 false
     * @param str
     * @return
     */
    public static boolean JudgmentString(String str){

        //防止输入的重量为零

       //输入的为整数

        if(str.indexOf(".") == -1){
           if( Integer.valueOf(str)  > 0){
            return true;
           }
           return false;
       }

        //小数点前
        String str1=str.substring(0, str.indexOf("."));

//        System.out.println(str1);

        String str2=str.substring(str1.length()+1, str.length());
//        System.out.println(str2);

        boolean flag = false;

        if(Integer.valueOf(str1) == 0){

            byte[] bytes = str2.getBytes();

            for ( int i = 0 ; i < bytes.length ;  i++){
                if(   bytes[i] != '0' ){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                return false;
            }

        }

        return true;
    }





}


