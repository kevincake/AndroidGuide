package cn.ifreedomer.com.androidguide.util;

import android.content.Context;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.InputStream;

import cn.ifreedomer.com.androidguide.GuideApplication;
import cn.ifreedomer.com.androidguide.constants.Constants;

/**
 * @author:eavawu
 * @date: 6/22/16.
 * @todo:
 */
public class ZipUtil {


    public static void UnZipMarkdown() {
        GuideApplication instance = GuideApplication.getInstance();
        try {


            InputStream inputStream = instance.getResources().getAssets().open(Constants.MARKDOWN + ".zip", Context.MODE_WORLD_WRITEABLE);
            String outPutFile = CacheUtil.getAppCacheDir() + Constants.MARKDOWN + ".zip";
            //delete last file
            File outFile = new File(outPutFile);
            if (outFile.exists()){
                outFile.delete();
            }
            File cacheExtraFile = new File(CacheUtil.getAppCacheDir() + Constants.MARKDOWN);
            if (cacheExtraFile.exists()){
                cacheExtraFile.delete();
            }
            FileUtil.copyFile(inputStream, outPutFile);
            UnZipFolder(outPutFile, CacheUtil.getAppCacheDir());
            LogUtil.info("file", "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UnZipFolder(String source, String outPathString) {

        try {
            ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(source);
            zipFile.setFileNameCharset("utf-8");
            if (zipFile.isEncrypted()) {
                zipFile.setPassword("abc");
            }
            zipFile.extractAll(outPathString);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }//end of func


}
