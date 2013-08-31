package jp.greative.kurad.app.setting;

import com.typesafe.config.Config;
import jp.greative.kurad.framework.model.Method;
import jp.greative.kurad.framework.setting.LocalSetting;

import java.util.*;

/**
 * Setting for crud
 */
public class CrudSetting implements LocalSetting {

    /** character code */
    public String characterCode = "UTF-8";
    /** file extension */
    public String extension = ".java";
    /** application name */
    private String appName = "kurad";
    /** develop host */
    private String devUrl = "http://localhost:9000/";
    /** production host */
    private String prodUrl = "http://localhost:9000/";
    /** model package name */
    private String modelPackage = "models";
    /** model path */
    private String modelPath = "app/models";
    /** model name with package */
    private String modelNameWithPackage = "models.Model";
    // init by args
    /** model name */
    private String modelClass = "Model";
    /** model has created member */
    private Boolean modelHasCreated = true;
    /** model has modified member */
    private Boolean modelHasModified = false;
    /** model instance name */
    private String modelInstance = "model";
    /** prefix for model name */
    private String modelPrefix = "";
    /** suffix for model name */
    private String modelSuffix = "";
    /** model id name. ex id */
    private String modelId = "identifier";
    /** model id upper name. ex Id */
    private String modelIdUpperCase = "Identifier";
    /** service package */
    private String servicePackage = "models.crud";
    /** service path */
    private String servicePath = "app/models/crud";
    /** service name */
    private String serviceClass = "Service";
    /** prefix for service name */
    private String servicePrefix = "";
    /** suffix for service name */
    private String serviceSuffix = "Service";
    /** service template name */
    private String serviceTemplate = "defaultModel";
    /** controller package */
    private String controllerPackage = "controllers.crud";
    /** controller path */
    private String controllerPath = "app/controllers/crud";
    /** controller template name */
    private String controllerTemplate = "defaultController";
    // init by args
    /** controller name */
    private String controllerClass = "ControllerCrudController";
    /** prefix for controller name */
    private String controllerPrefix = "";
    /** suffix for controller name */
    private String controllerSuffix = "CrudController";
    /** controller for top */
    private String controllerTopClass = "TopCrudController";
    /** controller template fot top */
    private String controllerTopTemplate = "defaultTopController";
    /** controller method list */
    private List<Method> controllerMethodList = new ArrayList<Method>();
    /** view package */
    private String viewPackage = "views.crud";
    /** view path */
    private String viewPath = "app/views/crud";
    /** view name for index */
    private String viewIndex = "index";
    /** view name for create */
    private String viewCreate = "create";
    /** view name for update */
    private String viewUpdate = "update";
    /** view name for detail */
    private String viewDetail = "detail";
    /** view name for search */
    private String viewSearch = "search";
    /** view template name for index */
    private String viewIndexTemplate = "defaultViewIndex";
    /** view template name for create */
    private String viewCreateTemplate = "defaultViewCreate";
    /** view template name for update */
    private String viewUpdateTemplate = "defaultViewUpdate";
    /** view template name for detail */
    private String viewDetailTemplate = "defaultViewDetail";
    /** view template name for search */
    private String viewSearchTemplate = "defaultViewSearch";
    /** view layout package */
    private String viewLayoutPackage = "views.crud";
    /** view layout path */
    private String viewLayoutPath = "views/crud";
    /** view tag package */
    private String viewTagPackage = "views.crud.tag";
    /** view tag path */
    private String viewTagPath = "views/crud/tag";
    /** view file extension */
    private String viewExtension = ".scala.html";
    /** route path */
    private String routePath = "conf";
    /** route name */
    private String routeName = "routes";
    /** route file extension */
    private String routeExtension = "";
    /** package separator */
    private String packageSeparator = ".";
    /** prefix for package path */
    private String packagePathPrefix = "";
    /** prefix for static file */
    private String filePrefix = "";
    /** suffix for static file */
    private String fileSuffix = "";
    /** template file extension */
    private String templateExtension = ".mustache";
    /** all template path */
    private String templatePath = "kurad/templates";
    /** custom options */
    private HashMap<String, String> options = new LinkedHashMap<String, String>();
    /** menu. ex ["Contact","User"] */
    private List<String> menus;

    /**
     * init
     * @param config
     * @return
     */
    public CrudSetting initFromConfig(Config config) {

        setAppName(config.getString("kurad.setting.global.appName"));
        setCharacterCode(config.getString("kurad.setting.global.characterCode"));
        setControllerClass(config.getString("kurad.setting.controller.controllerClass"));
        setControllerMethodList(null);
        setControllerPackage(config.getString("kurad.setting.controller.controllerPackage"));
        setControllerPath(config.getString("kurad.setting.controller.controllerPath"));
        setControllerPrefix(config.getString("kurad.setting.controller.controllerPrefix"));
        setControllerSuffix(config.getString("kurad.setting.controller.controllerSuffix"));
        setControllerTemplate(config.getString("kurad.setting.controller.controllerTemplate"));
        setControllerTopClass(config.getString("kurad.setting.controller.controllerTopClass"));
        setControllerTopTemplate(config.getString("kurad.setting.controller.controllerTopTemplate"));
        setDevUrl(config.getString("kurad.setting.global.devUrl"));
        setExtension(config.getString("kurad.setting.global.extension"));
        setFilePrefix(config.getString("kurad.setting.file.filePrefix"));
        setFileSuffix(config.getString("kurad.setting.file.fileSuffix"));
        setModelClass(config.getString("kurad.setting.model.modelClass"));
        setModelHasCreated(config.getBoolean("kurad.setting.model.modelHasCreated"));
        setModelHasModified(config.getBoolean("kurad.setting.model.modelHasModified"));
        setModelId(config.getString("kurad.setting.model.modelId"));
        setModelIdUpperCase(config.getString("kurad.setting.model.modelIdUpperCase"));
        setModelInstance(config.getString("kurad.setting.model.modelInstance"));
        setModelNameWithPackage(config.getString("kurad.setting.model.modelNameWithPackage"));
        setModelPackage(config.getString("kurad.setting.model.modelPackage"));
        setModelPath(config.getString("kurad.setting.model.modelPath"));
        setModelPrefix(config.getString("kurad.setting.model.modelPrefix"));
        setModelSuffix(config.getString("kurad.setting.model.modelSuffix"));
        setOptions(null);
        setPackagePathPrefix(config.getString("kurad.setting.global.packagePathPrefix"));
        setPackageSeparator(config.getString("kurad.setting.global.packageSeparator"));
        setProdUrl(config.getString("kurad.setting.global.prodUrl"));
        setRouteExtension(config.getString("kurad.setting.route.routeExtension"));
        setRouteName(config.getString("kurad.setting.route.routeName"));
        setRoutePath(config.getString("kurad.setting.route.routePath"));
        setServiceClass(config.getString("kurad.setting.service.serviceClass"));
        setServicePackage(config.getString("kurad.setting.service.servicePackage"));
        setServicePath(config.getString("kurad.setting.service.servicePath"));
        setServicePrefix(config.getString("kurad.setting.service.servicePrefix"));
        setServiceSuffix(config.getString("kurad.setting.service.serviceSuffix"));
        setServiceTemplate(config.getString("kurad.setting.service.serviceTemplate"));
        setTemplateBasename(null);
        setTemplatePath(null);
        setViewCreate(config.getString("kurad.setting.view.viewCreate"));
        setViewCreateTemplate(config.getString("kurad.setting.view.viewCreateTemplate"));
        setViewDetail(config.getString("kurad.setting.view.viewDetail"));
        setViewDetailTemplate(config.getString("kurad.setting.view.viewDetailTemplate"));
        setViewExtension(config.getString("kurad.setting.view.viewExtension"));
        setViewIndex(config.getString("kurad.setting.view.viewIndex"));
        setViewIndexTemplate(config.getString("kurad.setting.view.viewIndexTemplate"));
        setViewLayoutPackage(config.getString("kurad.setting.view.viewLayoutPackage"));
        setViewLayoutPath(config.getString("kurad.setting.view.viewLayoutPath"));
        setViewPackage(config.getString("kurad.setting.view.viewPackage"));
        setViewPath(config.getString("kurad.setting.view.viewPath"));
        setViewTagPackage(config.getString("kurad.setting.view.viewTagPackage"));
        setViewTagPath(config.getString("kurad.setting.view.viewTagPath"));
        setViewUpdate(config.getString("kurad.setting.view.viewUpdate"));
        setViewUpdateTemplate(config.getString("kurad.setting.view.viewUpdateTemplate"));
        setMenus(config.getStringList("kurad.setting.view.menus"));
        setTemplateExtension(config.getString("kurad.setting.template.templateExtension"));
        setTemplatePath(config.getString("kurad.setting.template.templatePath"));
        setViewSearchTemplate(config.getString("kurad.setting.view.viewSearchTemplate"));
        setViewSearch(config.getString("kurad.setting.view.viewSearch"));
        return this;
    }

    @Override
    public String getTemplatePath() {
        return this.templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public String getTemplateBasename() {
        return null;
    }

    @Override
    public void setTemplateBasename(String templateBasename) {
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getModelClass() {
        return modelClass;
    }

    public void setModelClass(String modelClass) {
        this.modelClass = modelClass;
    }

    public Boolean getModelHasModified() {
        return modelHasModified;
    }

    public void setModelHasModified(Boolean modelHasModified) {
        this.modelHasModified = modelHasModified;
    }

    public String getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(String modelInstance) {
        this.modelInstance = modelInstance;
    }

    public String getModelPrefix() {
        return modelPrefix;
    }

    public void setModelPrefix(String modelPrefix) {
        this.modelPrefix = modelPrefix;
    }

    public String getModelSuffix() {
        return modelSuffix;
    }

    public void setModelSuffix(String modelSuffix) {
        this.modelSuffix = modelSuffix;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelIdUpperCase() {
        return modelIdUpperCase;
    }

    public void setModelIdUpperCase(String modelIdUpperCase) {
        this.modelIdUpperCase = modelIdUpperCase;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServicePrefix() {
        return servicePrefix;
    }

    public void setServicePrefix(String servicePrefix) {
        this.servicePrefix = servicePrefix;
    }

    public String getServiceSuffix() {
        return serviceSuffix;
    }

    public void setServiceSuffix(String serviceSuffix) {
        this.serviceSuffix = serviceSuffix;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    public String getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(String controllerClass) {
        this.controllerClass = controllerClass;
    }

    public String getControllerPrefix() {
        return controllerPrefix;
    }

    public void setControllerPrefix(String controllerPrefix) {
        this.controllerPrefix = controllerPrefix;
    }

    public String getControllerSuffix() {
        return controllerSuffix;
    }

    public void setControllerSuffix(String controllerSuffix) {
        this.controllerSuffix = controllerSuffix;
    }

    public String getViewPackage() {
        return viewPackage;
    }

    public void setViewPackage(String viewPackage) {
        this.viewPackage = viewPackage;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String getViewIndex() {
        return viewIndex;
    }

    public void setViewIndex(String viewIndex) {
        this.viewIndex = viewIndex;
    }

    public String getViewCreate() {
        return viewCreate;
    }

    public void setViewCreate(String viewCreate) {
        this.viewCreate = viewCreate;
    }

    public String getViewUpdate() {
        return viewUpdate;
    }

    public void setViewUpdate(String viewUpdate) {
        this.viewUpdate = viewUpdate;
    }

    public String getViewDetail() {
        return viewDetail;
    }

    public void setViewDetail(String viewDetail) {
        this.viewDetail = viewDetail;
    }

    public String getViewLayoutPackage() {
        return viewLayoutPackage;
    }

    public void setViewLayoutPackage(String viewLayoutPackage) {
        this.viewLayoutPackage = viewLayoutPackage;
    }

    public String getViewLayoutPath() {
        return viewLayoutPath;
    }

    public void setViewLayoutPath(String viewLayoutPath) {
        this.viewLayoutPath = viewLayoutPath;
    }

    public String getViewTagPackage() {
        return viewTagPackage;
    }

    public void setViewTagPackage(String viewTagPackage) {
        this.viewTagPackage = viewTagPackage;
    }

    public String getViewTagPath() {
        return viewTagPath;
    }

    public void setViewTagPath(String viewTagPath) {
        this.viewTagPath = viewTagPath;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPackageSeparator() {
        return packageSeparator;
    }

    public void setPackageSeparator(String packageSeparator) {
        this.packageSeparator = packageSeparator;
    }

    public String getPackagePathPrefix() {
        return packagePathPrefix;
    }

    public void setPackagePathPrefix(String packagePathPrefix) {
        this.packagePathPrefix = packagePathPrefix;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getRouteExtension() {
        return routeExtension;
    }

    public void setRouteExtension(String routeExtension) {
        this.routeExtension = routeExtension;
    }

    public String getViewIndexTemplate() {
        return viewIndexTemplate;
    }

    public void setViewIndexTemplate(String viewIndexTemplate) {
        this.viewIndexTemplate = viewIndexTemplate;
    }

    public String getViewCreateTemplate() {
        return viewCreateTemplate;
    }

    public void setViewCreateTemplate(String viewCreateTemplate) {
        this.viewCreateTemplate = viewCreateTemplate;
    }

    public String getViewUpdateTemplate() {
        return viewUpdateTemplate;
    }

    public void setViewUpdateTemplate(String viewUpdateTemplate) {
        this.viewUpdateTemplate = viewUpdateTemplate;
    }

    public String getViewDetailTemplate() {
        return viewDetailTemplate;
    }

    public void setViewDetailTemplate(String viewDetailTemplate) {
        this.viewDetailTemplate = viewDetailTemplate;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public void addOptions(String key, String value) {
        this.options.put(key, value);
    }

    public String getViewExtension() {
        return viewExtension;
    }

    public void setViewExtension(String viewExtension) {
        this.viewExtension = viewExtension;
    }

    public String getServiceTemplate() {
        return serviceTemplate;
    }

    public void setServiceTemplate(String serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public String getControllerTemplate() {
        return controllerTemplate;
    }

    public void setControllerTemplate(String controllerTemplate) {
        this.controllerTemplate = controllerTemplate;
    }

    public String getControllerTopClass() {
        return controllerTopClass;
    }

    public void setControllerTopClass(String controllerTopClass) {
        this.controllerTopClass = controllerTopClass;
    }

    public String getControllerTopTemplate() {
        return controllerTopTemplate;
    }

    public void setControllerTopTemplate(String controllerTopTemplate) {
        this.controllerTopTemplate = controllerTopTemplate;
    }

    public List<Method> getControllerMethodList() {
        return controllerMethodList;
    }

    public void setControllerMethodList(List<Method> controllerMethodList) {
        this.controllerMethodList = controllerMethodList;
    }

    public String getModelNameWithPackage() {
        return modelNameWithPackage;
    }

    public void setModelNameWithPackage(String modelNameWithPackage) {
        this.modelNameWithPackage = modelNameWithPackage;
    }

    public Boolean getModelHasCreated() {
        return modelHasCreated;
    }

    public void setModelHasCreated(Boolean modelHasCreated) {
        this.modelHasCreated = modelHasCreated;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDevUrl() {
        return devUrl;
    }

    public void setDevUrl(String devUrl) {
        this.devUrl = devUrl;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public String getTemplateExtension() {
        return templateExtension;
    }

    public void setTemplateExtension(String templateExtension) {
        this.templateExtension = templateExtension;
    }

    public String getViewSearchTemplate() {
        return viewSearchTemplate;
    }

    public void setViewSearchTemplate(String viewSearchTemplate) {
        this.viewSearchTemplate = viewSearchTemplate;
    }

    public String getViewSearch() {
        return viewSearch;
    }

    public void setViewSearch(String viewSearch) {
        this.viewSearch = viewSearch;
    }
}
