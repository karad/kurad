package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.model.navigation.Menu;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.controller.writer.DefaultWriter;
import jp.greative.kurad.framework.setting.FileLocalSetting;
import jp.greative.kurad.framework.setting.LocalSetting;
import jp.greative.util.FileUtil;
import jp.greative.util.OptionUtil;
import play.Logger;
import java.io.*;
import java.util.*;
import static play.libs.F.*;

/**
 * File writer
 */
public class FileWriter extends DefaultWriter {

    /**
     * execute
     * @param baseName
     * @param path
     * @param fileLocalSetting
     * @param crudSetting
     * @return
     */
    public List<String> apply(String baseName, String path, FileLocalSetting fileLocalSetting, CrudSetting crudSetting) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            result.add(Colors.green("[kurad : created file] ") + saveFileWithContent(baseName + fileLocalSetting.getExtension(), view(fileLocalSetting, crudSetting), path, fileLocalSetting));
        } catch (IOException e) {
            Logger.error("kurad error" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * template view
     * @param fileLocalSetting
     * @return
     */
    public static String view(FileLocalSetting fileLocalSetting, CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        List<Menu> menuList = new ArrayList<Menu>();
        Option<List<String>> menus = OptionUtil.apply(crudSetting.getMenus());
        if(menus.isDefined()) {
            for(String menu : menus.get()) {
                menuList.add(new Menu(menu));
            }
        }
        map.put("menus", menuList);
        return DefaultTemplate.view(fileLocalSetting.getTemplateBasename(), crudSetting, map);
    }


    /**
     * save file with content
     *
     * @param fileName
     * @param content
     * @param path
     * @param localSetting
     * @return
     */
    public static String saveFileWithContent(String fileName, String content, String path, LocalSetting localSetting) throws IOException {
        String filePath = "";
        Boolean hasDir = false;
        if(path == null) {
            path = ".";
        } else {
            hasDir = FileUtil.checkDir(path);
        }
        if(hasDir) {
            filePath = path + FileUtil.DIR_SEPARATOR + fileName + localSetting.getExtension();
            File file = new File(filePath);
            OutputStream outputStream = null;
            java.io.Writer writer = null;
            BufferedWriter bufferedWriter = null;
            try {
                outputStream = new FileOutputStream(file);
                writer = new OutputStreamWriter(outputStream, localSetting.getCharacterCode());
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(content);
            } finally {
                try {
                    if(bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if(writer != null) {
                        writer.close();
                    }
                    if(outputStream != null) {
                        outputStream.close();
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    /**
     * save file with content
     * @param fileName
     * @param content
     * @param path
     * @param fileLocalSetting
     * @return
     */
    public static String saveFileWithContent(String fileName, String content, String path, FileLocalSetting fileLocalSetting) throws IOException {
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
                writer = new OutputStreamWriter(outputStream, fileLocalSetting.getCharacterCode());
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(content);
            } finally {
                try {
                    if(bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if(writer != null) {
                        writer.close();
                    }
                    if(outputStream != null) {
                        outputStream.close();
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

}
