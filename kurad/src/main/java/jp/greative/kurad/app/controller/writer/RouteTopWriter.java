package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.RoutesTagTemplate;
import jp.greative.kurad.framework.controller.writer.DefaultWriter;
import jp.greative.util.FileUtil;
import jp.greative.util.RoutesTag;
import play.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Route top writer
 */
public class RouteTopWriter extends DefaultWriter {

    /**
     * execute
     * @param baseName
     * @param path
     * @param crudSetting
     * @param option
     * @return
     */
    public List<String> apply(String baseName, String path, CrudSetting crudSetting, LinkedHashMap<String, String> option) {

        ArrayList<String> result = new ArrayList<String>();
        try {
            result.add(Colors.green("[kurad : edited  file] ") + saveFileWithContent(baseName, view(crudSetting), path, crudSetting));
        } catch (IOException e) {
            Logger.error("kurad error" + e.getMessage());
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
        return apply(baseName, path, crudSetting, new LinkedHashMap<String, String>());
    }


    /**
     * template view
     * @param crudSetting
     * @return
     */
    public static String view(CrudSetting crudSetting) {
        String result = "";
        String routeData = "";

        try {
            routeData = FileUtil.getTextFromFile(crudSetting.getRoutePath() + File.separator + crudSetting.getRouteName() + crudSetting.getRouteExtension(), null);
        } catch(IOException e) {
            e.printStackTrace();
        }

        // target routes delete
        String tmpRouteData = RoutesTag.deleteRouteTag(crudSetting.getControllerPath() + "/" + crudSetting.getControllerClass(), routeData, crudSetting);
        // add crud routes
        String addRouteContent = RoutesTagTemplate.getRoutesTagTop(crudSetting.getControllerPath() + "/" + crudSetting.getControllerClass(), crudSetting.getControllerClass() + crudSetting.getControllerSuffix(), crudSetting.getControllerPackage(), crudSetting.getModelClass(), crudSetting);

        return tmpRouteData + addRouteContent;

    }




}
