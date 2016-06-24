package cn.ifreedomer.com.androidguide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author:eavawu
 * @date: 6/23/16.
 * @todo:
 */
public class FileUtil {
    //读文件
    public static String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte[] buffer = new byte[length];
        fis.read(buffer);

        String res = new String(buffer, "UTF-8");

        fis.close();
        return res;
    }
}
