package common;
import java.util.Map;
import java.util.HashMap;

/**
 * 功能描述
 * @author zhuchuanqi
 * @date 2019/9/19
 */
public class Constants {
    public static final Map<String,String> TableColumnType = new HashMap<String,String>();

    static {
        TableColumnType.put("bt_bmp_inf","N,N,C,N,C,C");
        TableColumnType.put("bt_buf_chg","N,N,N,N,C,N,C,C");
        TableColumnType.put("bt_buf_dsp","N,N,N,N,N,N,C,C");
        TableColumnType.put("bt_con_inf","N,N,N,N,N,N,N,N,C,N,C,C");
        TableColumnType.put("bt_msg_inf","N,N,N,C,N,N,N,N,N,N,N,N,C,C");
    }

}
