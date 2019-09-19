package parse;

import utils.ParseUtils;
import utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LENOVO
 * @description 解析查询出的数据
 * @date 2019-09-19 13:17
 */
public class ParseSelectLog {
    /**
     * 换行符
     */
    private static final String CHANGE_LINE = "\r\n";

    /**
     * 解析文件全路径
     */
    private static final String inFilePath = "E:/资料/工作/卡司收单财务自动化/生产/log/20190917/0001_debug_20190917.log";

    /**
     * 输出文件路径
     */
    private static final String outFilePath = "E:/资料/工作/卡司收单财务自动化/生产/log/20190917/";

    public static void main(String[] args) {

        File inFile = new File(inFilePath);

        BufferedReader br = null;

        int lineNum = 0;// 行号
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
            String lineMsg;
            String beferLineMsg = null;

            boolean changTitle = false;

            String tableName = null;
            List<String> result = new ArrayList<>();
            List<String> lenList = new ArrayList<>();
            int index;
            while ((lineMsg = br.readLine()) != null) {
                lineNum++;
                System.out.println("--------------------lineNum值=" + lineNum + "," + "当前类=Main.main()");

                String tempMsg = lineMsg.replace(" ", "");
                if (StringUtil.isNotEmpty(tempMsg) && tempMsg.length() > 10) {
                    if (StringUtil.isInteger(tempMsg.substring(0, 3)) && !tempMsg.contains("selected")) {
                        changTitle = false;

                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < lenList.size(); i++) {
                            String[] arr = lenList.get(i).split("_");
                            String msg = StringUtil.trim(lineMsg.substring(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])), ' ');

                            if (i == lenList.size() - 1) {
                                sb.append(msg);
                            } else {
                                sb.append(msg).append(",");
                            }
                        }

                        result.add( sb.append(CHANGE_LINE).toString());

                    } else if ((index = ParseUtils.indexOf(lineMsg, "select&*&from", 0)) != -1) {
                        changTitle = true;
                        tableName = lineMsg.substring(index).replaceAll("[ ;]", "");
                    } else if (ParseUtils.indexOf(lineMsg, "rows&selected", 0) != -1) {
                        writFile(tableName, result);
                        lenList.clear();
                    } else if (ParseUtils.indexOf(lineMsg, "-----------", 0) != -1) { //组装INSERT INTO
                        if (changTitle && StringUtil.isNotEmpty(beferLineMsg) && StringUtil.isNotEmpty(tableName)) {
                            String indexMsg = lineMsg.trim();
                            int startIndex = 0;
                            int len;
                            int endIndex = 0;
                            while (true) {
                                if((len = indexMsg.indexOf(" ", startIndex)) != -1){
                                    endIndex = len+1;
                                    lenList.add(startIndex + "_" + endIndex);
                                    startIndex = endIndex;
                                }else{
                                    lenList.add(startIndex + "_" + indexMsg.length());
                                    break;
                                }

                            }

                            String[] arr = beferLineMsg.split(" ");
                            StringBuilder sb;
                            sb = new StringBuilder("INSERT INTO ").append(tableName).append("(");
                            for (int i = 0; i < arr.length; i++) {
                                if (StringUtil.isNotEmpty(arr[i])) {
                                    if (i == arr.length - 1) {
                                        sb.append(arr[i]).append(")").append("VALUES(").append(CHANGE_LINE);
                                    } else {
                                        sb.append(arr[i]).append(",");
                                    }
                                }
                            }
                            result.add(sb.toString());
                        }
                    }
                    beferLineMsg = lineMsg;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

    private static void writFile(String FileName, List<String> data) {
        BufferedOutputStream out = null;
        if (StringUtil.isNotEmpty(FileName) && !data.isEmpty()) {
            try {
                File outFile = new File(outFilePath + FileName + ".TXT");
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
