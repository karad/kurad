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
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import static play.libs.F.*;

/**
 * Service Writer
 */
public class ServiceWriter extends DefaultWriter {

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
        LinkedHashMap<String, LinkedHashMap<String, Field>> modelsAndFields;
        try {
            modelsAndFields = ModelUtil.getFormList(crudSetting.getModelPackage(), crudSetting.getModelClass());
            LinkedHashMap<String, Field> fields = modelsAndFields.get(crudSetting.getModelNameWithPackage());

            /*
             * make model method
             */
            List<String> modelMethods = ModelUtil.getModelMethods(crudSetting.getModelClass(), fields, crudSetting.getModelId(), crudSetting);
            List<String> modelFetchFields = ModelUtil.getModelFetchFields(fields);

            /*
             * ManyToMany
             * (Name, Type)
             */
            Map<String, String> modelAddLogic = ModelUtil.getModelAddLogics(fields);

            if(baseNameOps.isDefined() && pathOps.isDefined() && templateNameOps.isDefined()) {
                try {
                    result.add(Colors.green("[kurad : created file] ") + saveFileWithContent(baseName, view(templateName, crudSetting, modelMethods, modelFetchFields, modelAddLogic), path, crudSetting));
                } catch (IOException e) {
                    Logger.error("kurad error" + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public List<String> apply(String baseName, String path, CrudSetting crudSetting) {
        String templateName = "";
        return apply(baseName + crudSetting.getExtension(), path, crudSetting.getServiceTemplate(), crudSetting, new LinkedHashMap<String, String>());
    }

    /**
     * template view
     * @param templateName
     * @param crudSetting
     * @param modelMethods
     * @param modelFetchFields
     * @param modelAddLogic
     * @return
     */
    public String view(String templateName, CrudSetting crudSetting, List<String> modelMethods, List<String> modelFetchFields, Map<String,String> modelAddLogic) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        List<Method> methodsList = new ArrayList<Method>();
        for(final String method : modelMethods) {
            methodsList.add(new Method(method));
        }
        map.put("methods", methodsList);
        List<FetchField> modelFetchFieldList = new ArrayList<FetchField>();
        for(final String modelFetchField : modelFetchFields) {
            modelFetchFieldList.add(new FetchField(modelFetchField));
        }
        map.put("modelFetchFields", modelFetchFieldList);
        List<AddLogic> modelAddLogicList = new ArrayList<AddLogic>();
        for(final Map.Entry<String, String> logic : modelAddLogic.entrySet()) {
            modelAddLogicList.add(new AddLogic(logic.getKey(), logic.getValue()));
        }
        map.put("modelAddLogic", modelAddLogicList);
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

}
