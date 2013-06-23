package jp.greative.util;

import jp.greative.kurad.framework.setting.Setting;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * File utility
 */
public class FileUtil {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DIR_SEPARATOR = System.getProperty("file.separator");
    public static final String TEMPLATE_SEPARATOR = "_";
    public static final String CHARSET = "UTF-8";

    /**
     * copy file
     *
     * @param fromPath
     * @param toPath
     * @throws java.io.IOException
     */
    public static Boolean copyFile(String toPath, String fromPath, Setting localSetting) throws IOException {
        FileChannel fromChannel = new FileInputStream(fromPath).getChannel();
        FileChannel toChannel = new FileOutputStream(toPath).getChannel();
        try {
            if(fromChannel != null) {
                fromChannel.transferTo(0, fromChannel.size(), toChannel);
            }
        } finally {
            if(fromChannel != null) {
                fromChannel.close();
            }
            if(toChannel != null) {
                toChannel.close();
            }
        }
        File file = new File(fromPath);
        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * copy file
     *
     * @param fromPath
     * @param toPath
     * @throws java.io.IOException
     */
    public static Boolean copyFileFromResource(String toPath, String fromPath, Setting localSetting) throws IOException {
        InputStream is = FileUtil.class.getResourceAsStream(fromPath);
        ReadableByteChannel fromChannel = Channels.newChannel(is);
        FileChannel toChannel = new FileOutputStream(toPath).getChannel();
        try {
            if(fromChannel != null) {
                ByteBuffer buf = ByteBuffer.allocateDirect(5120);
                while (fromChannel.read(buf) != -1) {
                    buf.flip();
                    toChannel.write(buf);
                    buf.clear();
                }
            }
        } finally {
            if(fromChannel != null) {
                fromChannel.close();
            }
            if(toChannel != null) {
                toChannel.close();
            }
        }
        File file = new File(fromPath);
        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * exists dir
     *
     * @param path
     * @return
     */
    public static Boolean checkDir(String path) {
        Boolean result = false;
        File dirs = new File(path);
        if(!dirs.exists()) {
            dirs.mkdirs();
        }
        if(dirs.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * get text from file
     * @param routesPath
     * @param charset
     * @return
     * @throws IOException
     */
    public static String getTextFromFile(String routesPath, String charset) throws IOException {
        String encoding;
        if(charset == null) {
            encoding = CHARSET;
        } else {
            encoding = charset;
        }
        String textData = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(routesPath);
            isr = new InputStreamReader(fis, encoding);
            br = new BufferedReader(isr);
            String line_buffer;
            while ( null != (line_buffer = br.readLine() ) ) {
                textData += line_buffer + LINE_SEPARATOR;
            }
        } finally {
            if(br != null) try { br.close(); } catch (IOException e) { e.printStackTrace();}
            if(isr != null) try { isr.close(); } catch (IOException e) { e.printStackTrace();}
            if(fis != null) try { fis.close(); } catch (IOException e) { e.printStackTrace();}
        }
        return textData;
    }

}
