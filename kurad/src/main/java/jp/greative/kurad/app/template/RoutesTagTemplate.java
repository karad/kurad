package jp.greative.kurad.app.template;

import com.google.common.base.CaseFormat;
import jp.greative.kurad.app.setting.CrudSetting;
import java.util.HashMap;
import java.util.Map;

/**
 * Routes tag template
 */
public class RoutesTagTemplate {

    /**
     * regex for routes
     * @param controllerPath
     * @param crudSetting
     * @return
     */
    public static String getRoutesTagReg(final String controllerPath,  CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("controllerPath", controllerPath);
        return DefaultTemplate.view("routesTagReg", crudSetting, map);
    }

    /**
     * part of route
     * @param controllerPath
     * @param className
     * @param packageName
     * @param modelName
     * @param crudSetting
     * @return
     */
    public static String getRoutesTagScaffold(final String controllerPath, final String className, final String packageName, final String modelName,  CrudSetting crudSetting) {
        final String routesName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("controllerPath", controllerPath);
        map.put("className", className);
        map.put("packageName", packageName);
        map.put("modelName", modelName);
        map.put("routesName", routesName);
        return DefaultTemplate.view("routesTagScaffold", crudSetting, map);

    }

    /**
     * part of top page route
     * @param controllerPath
     * @param className
     * @param packageName
     * @param modelName
     * @param crudSetting
     * @return
     */
    public static String getRoutesTagTop(final String controllerPath, final String className, final String packageName, final String modelName, CrudSetting crudSetting) {
        final String routesName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("controllerPath", controllerPath);
        map.put("className", className);
        map.put("packageName", packageName);
        map.put("modelName", modelName);
        map.put("routesName", routesName);
        return DefaultTemplate.view("routesTagTop", crudSetting, map);
    }

    /**
     * part of login page route
     * @param controllerPath
     * @param className
     * @param packageName
     * @param modelName
     * @param crudSetting
     * @return
     */
    public static String getRoutesTagLogin(final String controllerPath, final String className, final String packageName, final String modelName, CrudSetting crudSetting) {
        final String routesName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("controllerPath", controllerPath);
        map.put("className", className);
        map.put("packageName", packageName);
        map.put("modelName", modelName);
        map.put("routesName", routesName);
        return DefaultTemplate.view("routesTagLogin", crudSetting, map);
    }

}
