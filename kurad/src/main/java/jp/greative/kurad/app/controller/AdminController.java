package jp.greative.kurad.app.controller;

import com.typesafe.config.Config;
import jp.greative.kurad.app.controller.writer.AdminWriter;
import jp.greative.kurad.app.model.result.CrudResult;
import jp.greative.kurad.app.setting.Attach;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.framework.controller.DefaultController;
import jp.greative.kurad.framework.setting.GlobalSetting;
import jp.greative.kurad.framework.setting.Setting;
import jp.greative.util.FileUtil;
import jp.greative.util.ModelUtil;
import play.libs.F;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * DESCRIPTION
 *
 * @author harakazuhiro
 * @since 2013/06/20 18:48
 */
public class AdminController extends DefaultController {
    /**
     * execute method
     * @return
     */
    public static String apply() {

        // dummy model
        String dummyModel = "";

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
        crudSettingBase.setModelClass(dummyModel);
        crudSettingBase.setControllerClass(dummyModel + crudSettingBase.getControllerSuffix());
        crudSettingBase.setModelNameWithPackage(crudSettingBase.getModelPackage() + ModelUtil.PACKAGE_SEPARATOR + crudSettingBase.getModelClass());
        crudSettingBase.setServiceClass(dummyModel);
        CrudSetting crudSetting = Attach.it(crudSettingBase);

        CrudResult result = new CrudResult();
        result.addAll(model(crudSetting).get());
        return result.report();
    }

    /**
     * service mode
     * @param crudSetting
     * @return
     */
    public static CrudResult model(CrudSetting crudSetting) {
        CrudResult result = new CrudResult();
        result.add("create model files..." + FileUtil.LINE_SEPARATOR);
        // Model
        result.addAll(new AdminWriter().apply("Admin" + crudSetting.getExtension(), crudSetting.getModelPath(), "defaultModelAdmin", crudSetting, new LinkedHashMap<String, String>()));
        return result;
    }

}