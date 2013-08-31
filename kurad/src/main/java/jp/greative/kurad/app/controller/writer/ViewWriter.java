package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.controller.writer.DefaultWriter;
import jp.greative.kurad.framework.model.contentType.Html;
import jp.greative.kurad.framework.model.form.ModelField;
import jp.greative.util.*;
import play.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import static play.libs.F.*;

/**
 * View writer
 */
public class ViewWriter extends DefaultWriter {



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
     * execute shortcut
     * @param baseName
     * @param path
     * @param crudSetting
     * @return
     */
    public List<String> apply(final String baseName, final String path, final CrudSetting crudSetting) {
        String templateName = "";

        final List<String> result;

        Option<ViewMethod> method = ViewMethod.getEnum(baseName);
        if(method.isDefined()) {
            return method.get().apply(new ViewMethod.Matcher() {
                @Override
                public List<String> caseIsIndex() {
                    return apply(baseName + crudSetting.getViewExtension(), path, crudSetting.getViewIndexTemplate(), crudSetting, new LinkedHashMap<String, String>());
                }
                @Override
                public List<String> caseIsCreate() {
                    return apply(baseName + crudSetting.getViewExtension(), path, crudSetting.getViewCreateTemplate(), crudSetting, new LinkedHashMap<String, String>());
                }
                @Override
                public List<String> caseIsUpdate() {
                    return apply(baseName + crudSetting.getViewExtension(), path, crudSetting.getViewUpdateTemplate(), crudSetting, new LinkedHashMap<String, String>());
                }
                @Override
                public List<String> caseIsDetail() {
                    return apply(baseName + crudSetting.getViewExtension(), path, crudSetting.getViewDetailTemplate(), crudSetting, new LinkedHashMap<String, String>());
                }
                @Override
                public List<String> caseIsSearch() {
                    return apply(baseName + crudSetting.getViewExtension(), path, crudSetting.getViewSearchTemplate(), crudSetting, new LinkedHashMap<String, String>());
                }
            });
        }
        return apply(baseName, path, templateName, crudSetting, new LinkedHashMap<String, String>());
    }

    /**
     * template view
     * @param templateName
     * @param crudSetting
     * @return
     */
    public static String view(String templateName, CrudSetting crudSetting) {

        Html formInputs = new Html();
        Html displayFields = new Html();
        List<ModelField> modelFields = new ArrayList<ModelField>();

        LinkedHashMap<String, LinkedHashMap<String, Field>> modelsAndFields;
        try {
            modelsAndFields = ModelUtil.getFormList(crudSetting.getModelPackage(), crudSetting.getModelClass());
            LinkedHashMap<String, Field> fields = modelsAndFields.get(crudSetting.getModelNameWithPackage());
            Iterable<String> i = fields.keySet();
            for(String key : i) {
                modelFields.add(new ModelField(key));
                formInputs.addBody(FormWriter.getInputFromCode(fields.get(key), crudSetting).getBody());
                displayFields.addBody(FormWriter.getDisplayFieldFromCode(fields.get(key), crudSetting).getBody());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fields", modelFields);
        map.put("inputs", formInputs.getBody());
        map.put("displayFields", displayFields.getBody());
        map.put("crudSetting", crudSetting);

        return DefaultTemplate.view(templateName, crudSetting, map);
    }
}
