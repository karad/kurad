package jp.greative.kurad.app.template.element;

import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.model.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller methods
 */
public class Controller {

    /**
     * top page controller
     * @param crudSetting
     * @param methods
     * @return
     */
    public static String defaultTopController(final CrudSetting crudSetting, final List<String> methods) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        List<Method> methodsList = new ArrayList<Method>();
        for (final String method : methods) {
            methodsList.add(new Method(method));
        }
        map.put("methods", methodsList);
        return DefaultTemplate.view("defaultCrudController", crudSetting, map);
    }

    /**
     * default controller
     * @param crudSetting
     * @param methods
     * @return
     */
    public static String defaultController(final CrudSetting crudSetting, final List<String> methods) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        List<Method> methodsList = new ArrayList<Method>();
        for (final String method : methods) {
            methodsList.add(new Method(method));
        }
        map.put("methods", methodsList);
        return DefaultTemplate.view("defaultCrudController", crudSetting, map);
    }

    /**
     * index method
     * @param crudSetting
     * @return
     */
    public static Method defaultIndex(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultIndex", crudSetting, map));
    }

    /**
     * view(detail) method
     * @param crudSetting
     * @return
     */
    public static Method defaultView(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultView", crudSetting, map));
    }

    /**
     * create method
     * @param crudSetting
     * @return
     */
    public static Method defaultCreate(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultCreate", crudSetting, map));
    }

    /**
     * update method
     * @param crudSetting
     * @return
     */
    public static Method defaultUpdate(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultUpdate", crudSetting, map));
    }

    /**
     * delete method
     * @param crudSetting
     * @return
     */
    public static Method defaultDelete(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultDelete", crudSetting, map));
    }

    /**
     * search method
     * @param crudSetting
     * @return
     */
    public static Method defaultSearch(final CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crudSetting", crudSetting);
        return new Method(DefaultTemplate.view("defaultSearch", crudSetting, map));
    }

}
