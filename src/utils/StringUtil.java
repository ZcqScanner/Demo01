package utils;

import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 字符串是否为空
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String lPad(String source, char ch, int len) {
        String res = "";
        int sourceLen = 0;
        if (isNotEmpty(source)) {
            sourceLen = source.length();
        } else {
            source = "";
        }
        if (sourceLen >= len) {
            res = source.substring(0, len);
        } else {
            String temp = "";
            for (int i = 0; i < len - sourceLen; i++) {
                temp += ch;
            }
            res = temp + source;
        }
        return res;
    }

    public static String rPad(String source, char ch, int len) {
        String res = "";
        int sourceLen = 0;
        if (isNotEmpty(source)) {
            sourceLen = source.length();
        } else {
            source = "";
        }
        if (sourceLen >= len) {
            res = source.substring(0, len);
        } else {
            String temp = "";
            for (int i = 0; i < len - sourceLen; i++) {
                temp += ch;
            }
            res = source + temp;
        }
        return res;
    }

    /**
     *     去除前指定字符     
     *
     * @param args   目标字符串     
     * @param beTrim 要删除的指定字符     
     * @return 删除之后的字符串  </br>
     *     调用示例：System.out.println(ltrim("$$abc $",'$'))  响应:"abc $"   
     */
    public static String ltrim(String args, char beTrim) {
        if (args == null) {
            return "";
        }
        int st = 0;
        int len = args.length();
        char[] val = args.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[st] <= sbeTrim)) {
            st++;
        }
        return ((st > 0) || (len < args.length())) ? args.substring(st, len) : args;
    }

    /**
     *     去除后指定字符     
     *
     * @param args   目标字符串     
     * @param beTrim 要删除的指定字符     
     * @return 删除之后的字符串  </br>
     *     调用示例：System.out.println(rtrim("$$abc $",'$'))  响应:"$$abc"   
     */
    public static String rtrim(String args, char beTrim) {
        if (args == null) {
            return "";
        }
        int st = 0;
        int len = args.length();
        char[] val = args.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[len - 1] <= sbeTrim)) {
            len--;
        }
        return ((st > 0) || (len < args.length())) ? args.substring(st, len) : args;
    }

    /**
     *     去除前后指定字符     
     *
     * @param args   目标字符串     
     * @param beTrim 要删除的指定字符     
     * @return 删除之后的字符串  </br>
     */
    public static String trim(String args, char beTrim) {
        return rtrim(ltrim(args, beTrim), beTrim);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.lPad(null, '0', 1));
        System.out.println(StringUtil.isInteger(null));
    }

}
