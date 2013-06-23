package jp.greative.kurad.app.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.util.FileUtil;
import java.io.*;
import java.util.Map;

/**
 * Default template utility
 */
public class DefaultTemplate {

    /**
     * template view
     * @param templateName
     * @param crudSetting
     * @param map
     * @return
     */
    public static String view(String templateName, CrudSetting crudSetting, Map<String, Object> map) {
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        String result = "";
        try {
            Mustache mustache;
            String path = crudSetting.getTemplatePath();
            String templatePath = path + FileUtil.DIR_SEPARATOR + getTemplateNameWithSuffix(path, templateName, crudSetting.getTemplateExtension(), crudSetting.getModelClass()) + crudSetting.getTemplateExtension();
            File file = new File(templatePath);
            if(file.exists()) {
                mustache = mf.compile(new FileReader(templatePath), templateName + crudSetting.getTemplateExtension());
            } else {
                InputStream is = DefaultTemplate.class.getResourceAsStream(FileUtil.DIR_SEPARATOR + templateName + crudSetting.getTemplateExtension());
                mustache = mf.compile(new InputStreamReader(is), templateName + crudSetting.getTemplateExtension());
            }
            mustache.execute(writer, map).flush();
            result = writer.toString();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * template name with suffix
     * @param templatePath
     * @param templateName
     * @param templateExtension
     * @param modelClass
     * @return
     */
    public static String getTemplateNameWithSuffix(String templatePath, String templateName, String templateExtension, String modelClass) {
        String templateNameWithSuffix = templatePath + FileUtil.DIR_SEPARATOR + templateName + FileUtil.TEMPLATE_SEPARATOR + modelClass + templateExtension;
        File file = new File(templateNameWithSuffix);
        if(file.exists()) {
            return templateName + FileUtil.TEMPLATE_SEPARATOR + modelClass;
        } else {
            return templateName;
        }
    }

}
