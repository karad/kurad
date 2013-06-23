package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.controller.writer.DefaultWriter;
import jp.greative.kurad.framework.model.AddLogic;
import jp.greative.kurad.framework.model.FetchField;
import jp.greative.kurad.framework.model.Method;
import jp.greative.util.ModelUtil;
import jp.greative.util.OptionUtil;
import play.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static play.libs.F.Option;

/**
 * Admin Writer
 */
public class AdminWriter extends DefaultWriter {

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
        Option<String> baseNameOps = OptionUtil.apply(baseName);
        Option<String> pathOps = OptionUtil.apply(path);
        Option<String> templateNameOps = OptionUtil.apply(templateName);
        ArrayList<String> result = new ArrayList<String>();
        if(baseNameOps.isDefined() && pathOps.isDefined() && templateNameOps.isDefined()) {
            try {
                result.add(Colors.green("[kurad : created file] ") + saveFileWithContent(baseName, view(templateName, crudSetting), path, crudSetting));
            } catch (IOException e) {
                Logger.error("kurad error" + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * template view
     * @param templateName
     * @param crudSetting
     * @return
     */
    public String view(String templateName, CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

}
