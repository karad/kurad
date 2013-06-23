package jp.greative.kurad.framework.controller.writer;

import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.framework.model.result.DefaultResult;
import jp.greative.kurad.framework.setting.FileLocalSetting;
import java.io.*;
import java.util.*;
import jp.greative.util.FileUtil;

/**
 * Default writer
 */
public class DefaultWriter {

    /**
     * execute
     * @param baseName
     * @param path
     * @param fileLocalSetting
     * @param option
     * @return
     */
    public List<String> apply(String baseName, String path, FileLocalSetting fileLocalSetting, LinkedHashMap<String, String> option) {
        DefaultResult defaultResult = new DefaultResult();
        return defaultResult.get();
    }

    /**
     * execute shortcut
     * @param baseName
     * @param path
     * @param fileLocalSetting
     * @return
     */
    public List<String> apply(String baseName, String path, FileLocalSetting fileLocalSetting) {
        return apply(baseName, path, fileLocalSetting, new LinkedHashMap<String, String>());
    }

    /**
     * save file with content
     * @param fileName
     * @param content
     * @param path
     * @param crudSetting
     * @return
     */
    public static String saveFileWithContent(String fileName, String content, String path, CrudSetting crudSetting) throws IOException {
        String filePath = "";
        Boolean hasDir = false;
        if(path == null) {
            path = ".";
        } else {
            hasDir = FileUtil.checkDir(path);
        }
        if(hasDir) {
            filePath = path + FileUtil.DIR_SEPARATOR + fileName;
            File file = new File(filePath);
            OutputStream outputStream = null;
            java.io.Writer writer = null;
            BufferedWriter bufferedWriter = null;
            try {
                outputStream = new FileOutputStream(file);
                writer = new OutputStreamWriter(outputStream, crudSetting.getCharacterCode());
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(content);
            } finally {
                if(bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                if(writer != null) {
                    try {
                        writer.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                if(outputStream != null) {
                    try {
                        outputStream.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filePath;
    }

}
