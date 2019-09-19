package parse;

import common.Constants;
import utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LENOVO
 * @description 生成入库文件
 * @date 2019-09-19 13:46
 */
public class CreateInsertFile {
    /**
     * 换行符
     */
    private static final String CHANGE_LINE = "\r\n";

    /**
     * 解析文件路径
     */
    private static final String inFilePath = "E:/资料/工作/卡司收单财务自动化/生产/log/20190917/";

    private static final List<String> fileList = Arrays.asList("bt_bmp_inf.TXT", "bt_buf_chg.TXT", "bt_buf_dsp.TXT", "bt_con_inf.TXT", "bt_msg_inf.TXT");

    public static void main(String[] args) {
        for (String file : fileList) {
            String tital = null;
            String[] dataType = Constants.TableColumnType.get(file.replace(".TXT","")).split(",");

            File inFile = new File(inFilePath + file);

            BufferedReader br = null;

            int lineNum = 0;// 行号
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
                String lineMsg;

                List<String> result = new ArrayList<>();
                while ((lineMsg = br.readLine()) != null) {
                    lineNum++;
                    if (lineNum == 1) {
                        tital = lineMsg;
                    } else {
                        StringBuilder sb = new StringBuilder(tital);
                        String[] arr = lineMsg.split(",");
                        for (int i = 0; i < arr.length; i++) {
                            if ("C".equals(dataType[i])) {
                                sb.append("'").append(arr[i]).append("'");
                            } else {
                                sb.append(arr[i]);
                            }
                            if (i != arr.length - 1) {
                                sb.append(",");
                            } else {
                                sb.append(");").append(CHANGE_LINE);
                            }
                        }

                        result.add(sb.toString());
                    }
                }

                writFile(inFilePath + "insert_" + file, result);
            } catch (Exception e) {
                System.out.println("--------------------file值=" + file + "," + "当前类=CreateInsertFile.main()");
                System.out.println("--------------------lineNum值=" + lineNum + "," + "当前类=CreateInsertFile.main()");
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private static void writFile(String fileName, List<String> data) {
        BufferedOutputStream out = null;
        if (StringUtil.isNotEmpty(fileName) && !data.isEmpty()) {
            try {
                File outFile = new File(fileName.replace(".TXT",".SQL"));
                out = new BufferedOutputStream(new FileOutputStream(outFile));
                for (int i = 0; i < data.size(); i++) {
                    out.write(data.get(i).getBytes());
                    if (i % 50 == 0) {
                        out.flush();
                    }
                }
                out.flush();
                data.clear();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
