package jp.greative.kurad.app.controller;

import com.typesafe.config.Config;
import jp.greative.kurad.Colors;
import jp.greative.kurad.app.controller.writer.AdminWriter;
import jp.greative.kurad.app.model.result.CrudResult;
import jp.greative.kurad.app.setting.Attach;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.framework.controller.DefaultController;
import jp.greative.kurad.framework.setting.GlobalSetting;
import jp.greative.kurad.framework.setting.Setting;
import jp.greative.util.FileUtil;
import jp.greative.util.ModelUtil;
import play.libs.F;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DESCRIPTION
 *
 * @author harakazuhiro
 * @since 2013/06/20 18:48
 */
public class DeleteAllController extends DefaultController {
    /**
     * execute method
     * @return
     */
    public static String apply() {

        // read setting
        Setting globalSetting = GlobalSetting.getInstance();
        F.Option<Config> config = globalSetting.loadSettings();
        CrudSetting crudSettingBase;
        if (config.isDefined()) {
            crudSettingBase = new CrudSetting().initFromConfig(config.get());
        } else {
            crudSettingBase = new CrudSetting();
        }

        // init setting
        CrudSetting crudSetting = Attach.it(crudSettingBase);

        CrudResult result = new CrudResult();
        result.addAll(delete(crudSetting).get());
        return result.report();
    }

    /**
     * service mode
     * @param crudSetting
     * @return
     */
    public static CrudResult delete(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("delete all files..." + FileUtil.LINE_SEPARATOR);
        result.addAll(deleteDirectory(crudSetting.getModelPath()));
        return result;
    }

    public static List<String> deleteDirectory(String path) {
        String rightPath = pathSeparatorEndWith(path);
        File file = new File(rightPath);
        deleteDirectoryOrFile(file);
        ArrayList<String> result = new ArrayList<String>();
        result.add(Colors.green("[kurad : deleted  file] ") + rightPath);
        return result;
    }

    public static String pathSeparatorEndWith(String path) {
        if(path.endsWith("/")) {
            return path;
        } else {
            return path + "/";
        }
    }

    public static void deleteDirectoryOrFile(File directoryOrFile) {
        if (!directoryOrFile.exists()) {
            return;
        }

        if (directoryOrFile.isFile()) {
            directoryOrFile.delete();
        }

        if (directoryOrFile.isDirectory()) {
            File[] files = directoryOrFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            directoryOrFile.delete();
        }
    }

}