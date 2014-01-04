package jp.greative.kurad.app.controller;

import com.typesafe.config.Config;
import jp.greative.kurad.app.controller.writer.*;
import jp.greative.kurad.app.model.mode.Mode;
import jp.greative.kurad.app.model.result.CrudResult;
import jp.greative.kurad.app.setting.Attach;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.framework.controller.DefaultController;
import jp.greative.kurad.framework.model.Method;
import jp.greative.kurad.framework.setting.FileLocalSetting;
import jp.greative.kurad.framework.setting.GlobalSetting;
import jp.greative.kurad.framework.setting.Setting;
import jp.greative.kurad.app.template.element.Controller;
import jp.greative.util.FileUtil;
import jp.greative.util.ModelUtil;
import java.lang.reflect.Field;
import java.util.*;
import static play.libs.F.*;

/**
 * Crud controller
 */
public class CrudController extends DefaultController {

    /**
     * execute method
     * @param mode
     * @param model
     * @return
     */
    @Override
    public String apply(String mode, String model) {
        return apply(Mode.is(mode), model);
    }

    /**
     * execute method
     * @param mode
     * @param model
     * @return
     */
    public String apply(Mode mode, String model) {

        // read setting
        Setting globalSetting = GlobalSetting.getInstance();
        Option<Config> config = globalSetting.loadSettings();
        CrudSetting crudSettingBase;
        if (config.isDefined()) {
            crudSettingBase = new CrudSetting().initFromConfig(config.get());
        } else {
            crudSettingBase = new CrudSetting();
        }

        // init setting
        crudSettingBase.setModelClass(model);
        crudSettingBase.setControllerClass(model + crudSettingBase.getControllerSuffix());
        crudSettingBase.setModelNameWithPackage(crudSettingBase.getModelPackage() + ModelUtil.PACKAGE_SEPARATOR + crudSettingBase.getModelClass());
        crudSettingBase.setServiceClass(model);
        CrudSetting crudSetting = Attach.it(crudSettingBase);
        CrudResult result = new CrudResult();

        try {
            if(!ModelUtil.isEbeanModel(crudSetting.getModelPackage(), model)) {
                return model + " is not Ebean Model." + FileUtil.LINE_SEPARATOR;
            }
            LinkedHashMap<String, LinkedHashMap<String, Field>> modelsAndFields = ModelUtil.getFormList(crudSetting.getModelPackage(), crudSetting.getModelClass());
            if(modelsAndFields.containsKey(crudSetting.getModelNameWithPackage())) {
                switch (mode) {
                    case ALL:        result.addAll(all       (crudSetting).get()); break;
                    case SERVICE:    result.addAll(service   (crudSetting).get()); break;
                    case CONTROLLER: result.addAll(controller(crudSetting).get()); break;
                    case VIEW:       result.addAll(view      (crudSetting).get()); break;
                    case OTHER:      result.addAll(other     (crudSetting).get()); break;
                    case ROUTE:      result.addAll(route     (crudSetting).get()); break;
                    case NONE:       result.addAll(none      (crudSetting).get()); break;
                    default:         result.addAll(none      (crudSetting).get()); break;
                }
            }
        } catch (ClassNotFoundException e) {
            return model + " class couldn't found." + FileUtil.LINE_SEPARATOR;
        }
        return result.report();
    }

    /**
     * all mode
     * @param crudSetting
     * @return
     */
    public static CrudResult all(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing all files..." + FileUtil.LINE_SEPARATOR);

        // make controller method
        List<Method> controllerMethods = Arrays.asList(
                Controller.defaultIndex(crudSetting),
                Controller.defaultView(crudSetting),
                Controller.defaultCreate(crudSetting),
                Controller.defaultUpdate(crudSetting),
                Controller.defaultDelete(crudSetting),
                Controller.defaultSearch(crudSetting)
        );
        crudSetting.setControllerMethodList(controllerMethods);

        // Controller
        result.addAll(new ControllerWriter().apply(crudSetting.getControllerClass() + crudSetting.getExtension(), crudSetting.getControllerPath(), crudSetting));
        result.addAll(new ControllerWriter().apply(crudSetting.getControllerTopClass() + crudSetting.getExtension(), crudSetting.getControllerPath(), crudSetting.getControllerTopTemplate(), crudSetting, new LinkedHashMap<String, String>()));

        // Model
        result.addAll(new ServiceWriter().apply(crudSetting.getModelClass() + crudSetting.getServiceSuffix(), crudSetting.getServicePath(), crudSetting));

        // PagingSetting
        result.addAll(new ServiceWriter().apply("PagingSetting" + crudSetting.getExtension(), crudSetting.getServicePath(), "paginateSetting", crudSetting, new LinkedHashMap<String, String>()));

        // OptionUtil.java
        result.addAll(new ServiceWriter().apply("OptionUtil" + crudSetting.getExtension(), crudSetting.getServicePath(), "optionUtil", crudSetting, new LinkedHashMap<String, String>()));

        // Login.java
        result.addAll(new ServiceWriter().apply("Login" + crudSetting.getExtension(), crudSetting.getServicePath(), "login", crudSetting, new LinkedHashMap<String, String>()));

        // SearchEngine.java
        result.addAll(new ServiceWriter().apply("SearchEngine" + crudSetting.getExtension(), crudSetting.getServicePath(), "searchEngine", crudSetting, new LinkedHashMap<String, String>()));

        // Paging.java
        result.addAll(new ServiceWriter().apply("Paging" + crudSetting.getExtension(), crudSetting.getServicePath(), "paging", crudSetting, new LinkedHashMap<String, String>()));
        // PagingBean.java
        result.addAll(new ServiceWriter().apply("PagingBean" + crudSetting.getExtension(), crudSetting.getServicePath(), "pagingBean", crudSetting, new LinkedHashMap<String, String>()));

        // paginate
        result.addAll(new ServiceWriter().apply("paginate" + crudSetting.getViewExtension(), crudSetting.getViewPath(), "paginate", crudSetting, new LinkedHashMap<String, String>()));
        result.addAll(new ServiceWriter().apply("search_paginate" + crudSetting.getViewExtension(), crudSetting.getViewPath(), "searchPaginate", crudSetting, new LinkedHashMap<String, String>()));

        // DateUtil.java
        result.addAll(new ServiceWriter().apply("DateUtil" + crudSetting.getExtension(), crudSetting.getServicePath(), "dateUtil", crudSetting, new LinkedHashMap<String, String>()));

        // Secured.java
        result.addAll(new ServiceWriter().apply("Secured" + crudSetting.getExtension(), crudSetting.getServicePath(), "defaultSecured", crudSetting, new LinkedHashMap<String, String>()));

        // View
        result.addAll(new ViewWriter().apply("index",  crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("create", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("update", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("detail", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("search", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));

        // statics
        // other files

        // view top
        FileLocalSetting viewTopSetting = new FileLocalSetting();
        viewTopSetting.setTemplatePath("app/templates");
        viewTopSetting.setTemplateBasename("defaultViewTop");
        viewTopSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("top_crud", crudSetting.getViewPath(), viewTopSetting, crudSetting));

        // view main
        FileLocalSetting viewMainSetting = new FileLocalSetting();
        viewMainSetting.setTemplatePath("app/templates");
        viewMainSetting.setTemplateBasename("defaultViewMain");
        viewMainSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("main_crud", crudSetting.getViewPath(), viewMainSetting, crudSetting));

        // view login
        FileLocalSetting viewLoginSetting = new FileLocalSetting();
        viewMainSetting.setTemplatePath("app/templates");
        viewMainSetting.setTemplateBasename("defaultViewLogin");
        viewMainSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("login_crud", crudSetting.getViewPath(), viewMainSetting, crudSetting));

        // routes

        // add route for crud
        result.addAll(new RouteWriter().apply("routes", "conf", crudSetting));
        // add route for crud top page
        crudSetting.setControllerClass("Top");
        crudSetting.setTemplatePath(crudSetting.getTemplatePath());
        result.addAll(new RouteTopWriter().apply("routes", "conf", crudSetting));
        crudSetting.setControllerClass("Admin");
        crudSetting.setTemplatePath(crudSetting.getTemplatePath());
        result.addAll(new RouteLoginWriter().apply("routes", "conf", crudSetting));

        return result;
    }

    /**
     * service mode
     * @param crudSetting
     * @return
     */
    public static CrudResult service(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing service files..." + FileUtil.LINE_SEPARATOR);
        // Model
        result.addAll(new ServiceWriter().apply(crudSetting.getModelClass() + crudSetting.getServiceSuffix(), crudSetting.getServicePath(), crudSetting));
        return result;
    }

    /**
     * controller mode
     * @param crudSetting
     * @return
     */
    public static CrudResult controller(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing controller files..." + FileUtil.LINE_SEPARATOR);
        // make controller method
        List<Method> controllerMethods = Arrays.asList(
                Controller.defaultIndex(crudSetting),
                Controller.defaultView(crudSetting),
                Controller.defaultCreate(crudSetting),
                Controller.defaultUpdate(crudSetting),
                Controller.defaultDelete(crudSetting),
                Controller.defaultSearch(crudSetting)
        );
        crudSetting.setControllerMethodList(controllerMethods);
        // Controller
        result.addAll(new ControllerWriter().apply(crudSetting.getControllerClass() + crudSetting.getExtension(), crudSetting.getControllerPath(), crudSetting));

        return result;
    }

    /**
     * view mode
     * @param crudSetting
     * @return
     */
    public static CrudResult view(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing view files..." + FileUtil.LINE_SEPARATOR);
        // View
        result.addAll(new ViewWriter().apply("index",  crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("create", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("update", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("detail", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        result.addAll(new ViewWriter().apply("search", crudSetting.getViewPath() + FileUtil.DIR_SEPARATOR + crudSetting.getModelClass(), crudSetting));
        return result;
    }

    /**
     * other mode
     * @param crudSetting
     * @return
     */
    public static CrudResult other(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing other files..." + FileUtil.LINE_SEPARATOR);
        // make controller method
        List<Method> controllerMethods = Arrays.asList(
                Controller.defaultIndex(crudSetting),
                Controller.defaultView(crudSetting),
                Controller.defaultCreate(crudSetting),
                Controller.defaultUpdate(crudSetting),
                Controller.defaultDelete(crudSetting),
                Controller.defaultSearch(crudSetting)
        );
        crudSetting.setControllerMethodList(controllerMethods);

        // Controller
        result.addAll(new ControllerWriter().apply(crudSetting.getControllerTopClass() + crudSetting.getExtension(), crudSetting.getControllerPath(), crudSetting.getControllerTopTemplate(), crudSetting, new LinkedHashMap<String, String>()));

        // PagingSetting
        result.addAll(new ServiceWriter().apply("PagingSetting" + crudSetting.getExtension(), crudSetting.getServicePath(), "paginateSetting", crudSetting, new LinkedHashMap<String, String>()));

        // OptionUtil.java
        result.addAll(new ServiceWriter().apply("OptionUtil" + crudSetting.getExtension(), crudSetting.getServicePath(), "optionUtil", crudSetting, new LinkedHashMap<String, String>()));

        // Login.java
        result.addAll(new ServiceWriter().apply("Login" + crudSetting.getExtension(), crudSetting.getServicePath(), "login", crudSetting, new LinkedHashMap<String, String>()));

        // SearchEngine.java
        result.addAll(new ServiceWriter().apply("SearchEngine" + crudSetting.getExtension(), crudSetting.getServicePath(), "searchEngine", crudSetting, new LinkedHashMap<String, String>()));

        // Paging.java
        result.addAll(new ServiceWriter().apply("Paging" + crudSetting.getExtension(), crudSetting.getServicePath(), "paging", crudSetting, new LinkedHashMap<String, String>()));
        // PagingBean.java
        result.addAll(new ServiceWriter().apply("PagingBean" + crudSetting.getExtension(), crudSetting.getServicePath(), "pagingBean", crudSetting, new LinkedHashMap<String, String>()));

        // paginate
        result.addAll(new ServiceWriter().apply("paginate" + crudSetting.getViewExtension(), crudSetting.getViewPath(), "paginate", crudSetting, new LinkedHashMap<String, String>()));
        result.addAll(new ServiceWriter().apply("searchPaginate" + crudSetting.getViewExtension(), crudSetting.getViewPath(), "searchPaginate", crudSetting, new LinkedHashMap<String, String>()));

        // DateUtil.java
        result.addAll(new ServiceWriter().apply("DateUtil" + crudSetting.getExtension(), crudSetting.getServicePath(), "dateUtil", crudSetting, new LinkedHashMap<String, String>()));

        // Secured.java
        result.addAll(new ServiceWriter().apply("Secured" + crudSetting.getExtension(), crudSetting.getServicePath(), "defaultSecured", crudSetting, new LinkedHashMap<String, String>()));

        // statics

        // other files

        // view top
        FileLocalSetting viewTopSetting = new FileLocalSetting();
        viewTopSetting.setTemplatePath("app/templates");
        viewTopSetting.setTemplateBasename("defaultViewTop");
        viewTopSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("top_crud", crudSetting.getViewPath(), viewTopSetting, crudSetting));

        // view main
        FileLocalSetting viewMainSetting = new FileLocalSetting();
        viewMainSetting.setTemplatePath("app/templates");
        viewMainSetting.setTemplateBasename("defaultViewMain");
        viewMainSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("main_crud", crudSetting.getViewPath(), viewMainSetting, crudSetting));

        // view login
        FileLocalSetting viewLoginSetting = new FileLocalSetting();
        viewMainSetting.setTemplatePath("app/templates");
        viewMainSetting.setTemplateBasename("defaultViewLogin");
        viewMainSetting.setExtension(".scala.html");
        result.addAll(new FileWriter().apply("login_crud", crudSetting.getViewPath(), viewMainSetting, crudSetting));

        // routes
        crudSetting.setControllerClass("Top");
        result.addAll(new RouteTopWriter().apply("routes", "conf", crudSetting));


        return result;
    }

    /**
     * route mode
     * @param crudSetting
     * @return
     */
    public static CrudResult route(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("writing route files..." + FileUtil.LINE_SEPARATOR);
        // routes
        // add route for crud
        result.addAll(new RouteWriter().apply("routes", "conf", crudSetting));
        // add route for crud top page
        crudSetting.setControllerClass("Top");
        crudSetting.setTemplatePath(crudSetting.getTemplatePath());
        result.addAll(new RouteTopWriter().apply("routes", "conf", crudSetting));
        crudSetting.setControllerClass("Admin");
        crudSetting.setTemplatePath(crudSetting.getTemplatePath());
        result.addAll(new RouteLoginWriter().apply("routes", "conf", crudSetting));
        return result;
    }

    /**
     * other mode
     * @param crudSetting
     * @return
     */
    public static CrudResult none(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("no mode..." + FileUtil.LINE_SEPARATOR);
        return result;
    }

}
