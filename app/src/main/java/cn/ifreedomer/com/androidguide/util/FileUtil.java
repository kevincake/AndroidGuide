package cn.ifreedomer.com.androidguide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static void copyFile(InputStream inputStream, String s) {
        OutputStream outputStream = null;
        try {
            File file = new File(s);
            if (!file.exists()){
            }
            outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
