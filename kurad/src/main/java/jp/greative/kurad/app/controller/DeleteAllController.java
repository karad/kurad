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
import org.apache.commons.lang3.StringUtils;
import play.libs.F;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
        result.addAll(deleteDirectory(crudSetting.getServicePath()));
        result.addAll(deleteDirectory(crudSetting.getControllerPath()));
        result.addAll(deleteDirectory(crudSetting.getViewPath()));
        return result;
    }

    public static List<String> deleteDirectory(String path) {
        String rightPath = pathSeparatorEndWith(path);
        File file = new File(rightPath);
        ArrayList<String> result = new ArrayList<String>();
        deleteDirectoryOrFile(file, result);
        return result;
    }

    public static String pathSeparatorEndWith(String path) {
        if(path.endsWith("/")) {
            return StringUtils.stripEnd(path, "/");
        } else {
            return path;
        }
    }

    public static List<String> deleteDirectoryOrFile(File directoryOrFile, List<String> result) {
        if (!directoryOrFile.exists()) {
            return result;
        }

        if (directoryOrFile.isFile() && directoryOrFile.delete()) {
            result.add(Colors.green("[kurad : deleted  file] ") + directoryOrFile.getPath());
        }

        File[] tmpFiles = directoryOrFile.listFiles();
        if (directoryOrFile.isDirectory() && tmpFiles != null) {
            List<File> files = Arrays.asList(tmpFiles);
            for (File file : files) {
                deleteDirectoryOrFile(file, result);
            }
            if(directoryOrFile.delete()) {
                result.add(Colors.green("[kurad : deleted  file] ") + directoryOrFile.getPath());
            }
        }
        return result;
    }

}