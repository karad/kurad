package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.controller.writer.DefaultWriter;
import jp.greative.util.OptionUtil;
import play.Logger;
import static play.libs.F.*;
import java.io.*;
import java.util.*;

/**
 * Controller writer
 */
public class ControllerWriter extends DefaultWriter {

    /**
     * execute
     * @param baseName
     * @param path
     * @param templateName
     * @param crudSetting
     * @param option
     * @return
     */
    public List<String> apply(String baseName, String path, String templateName, CrudSetting crudSetting, LinkedHashMap<String, String> option) {
        ArrayList<String> result = new ArrayList<String>();
        Option<String> baseNameOps = OptionUtil.apply(baseName);
        Option<String> pathOps = OptionUtil.apply(path);
        Option<String> templateNameOps = OptionUtil.apply(templateName);
        if(baseNameOps.isDefined() && pathOps.isDefined() && templateNameOps.isDefined()) {
            try {
                result.add(Colors.green("[kurad : created file] ") + saveFileWithContent(baseName, view(templateNameOps.get(), crudSetting), pathOps.get(), crudSetting));
            } catch (IOException e) {
                Logger.error("kurad error" + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * shortcut apply
     * @param baseName
     * @param path
     * @param crudSetting
     * @return
     */
    public List<String> apply(String baseName, String path, CrudSetting crudSetting) {
        return apply(baseName, path, crudSetting.getControllerTemplate(), crudSetting, new LinkedHashMap<String, String>());
    }

    /**
     * template view
     * @param templateName
     * @param crudSetting
     * @return
     */
    public static String view(String templateName, CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        map.put("methods", crudSetting.getControllerMethodList());
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

}
