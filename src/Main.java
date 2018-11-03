import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Main, 调用Translate
public class Main {
    public static void main(String[] args) {
        String filePath = args[0];
        String newFileName = args[1];
        String newFilePath = args[2];
        File file = new File(filePath);
        File newFile = new File(newFilePath);
        BufferedReader br;
        BufferedWriter bw;
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(newFile);
            br = new BufferedReader(new InputStreamReader(fin));
            bw = new BufferedWriter(new OutputStreamWriter(fout));
            String str = "";
            String num = "";
            String oldStr = "";
            int i=1;
            String first = "SET FOREIGN_KEY_CHECKS=0;\n" +
                    "\n" +
                    "-- ----------------------------\n" +
                    "-- Table structure for "+newFileName+"\n" +
                    "-- ----------------------------\n" +
                    "DROP TABLE IF EXISTS `"+newFileName+"`;\n" +
                    "CREATE TABLE `"+newFileName+"` (\n" +
                    "  `id` int(11) unsigned NOT NULL,\n" +
                    "  `origin_title` varchar(1000) DEFAULT NULL,\n" +
                    "  `trans_title` varchar(1000) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=9317 DEFAULT CHARSET=utf8mb4;\n";
            bw.write(first);
            while((str=br.readLine())!=null){

                if(str.startsWith("INSERT")){
                    String strs[] = str.split("'");
                    num = strs[1];
                    str = strs[3];
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(str);
                    if(m.find()) {
                        oldStr = str;
                        str = Translate.getRes(str);
                        str = str.replaceAll("'","''");
                        String sql = "INSERT INTO `"+newFileName+"`   VALUES ("+"'"+num+"','"+oldStr+"','"+str+"');\n";
                        bw.write(sql);
                        bw.flush();
                    }
                }
            }
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println(newFileName+"输出完成！");
        }

    }
}
