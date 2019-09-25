package utils;

public class ParseUtils {

    /**
     * 逐个比较key 都匹配成功则返回最后一个关键字的结束位置，否则返回 -1
     *
     * @param msg 一行记录
     * @param key 关键字以&分隔(必须是连续的且是有序的)
     * @param num 关键字后num位仍为空
     * @return
     */
    public static int indexOf(String msg, String key, int num) {
        int index = -1;
        if (!StringUtil.isEmpty(msg) && !StringUtil.isEmpty(key)) {
            String[] keyArr = key.split("&");
            int start = 0;
            for (int i = 0; i < keyArr.length; i++) {
                start = msg.indexOf(keyArr[i], start);
                if (start == -1) {
                    break;
                }
                if (i == keyArr.length - 1) {// 如果 是最后一个关键字，则记录下标
                    index = start + keyArr[i].length();
                }
            }

            if (index != -1 && ((msg.replaceAll(" ", "")).indexOf(key.replaceAll("&", "")) == -1)) {// 去空格后，关键字连续性全匹配
                index = -1;
            }

            if (index != -1 && num > 0) { //向后取多少位仍然为空
                int end = msg.length();
                if (msg.length() >= index + num) {
                    end = index + num;
                }
                if (StringUtil.isNotEmpty(msg.substring(index, end).trim())) {
                    index = -1;
                }
            }
        }
        return index;
    }

    public static void main(String[] args) {
        String msg = " EOF GRAND TOTAL                                2              1282 D           0                 0 D           2              1282 D";
        String key = "EOF&GRAND&TOTAL";
        int index = ParseUtils.indexOf(msg, key, 5);
        System.out.println("--------------------index值=" + index + "," + "当前类=ParseUtils.main()");

    }

}
