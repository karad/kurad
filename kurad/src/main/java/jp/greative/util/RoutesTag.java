package jp.greative.util;

import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.RoutesTagTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Route tag utility
 */
public class RoutesTag {

    /**
     * Include model route in  routes file
     * @param modelPath
     * @param routesData
     * @return
     */
    public static Boolean isIncludeModel(String modelPath, String routesData, CrudSetting crudSetting) {
        Pattern regPat2 = Pattern.compile(RoutesTagTemplate.getRoutesTagReg(modelPath, crudSetting), Pattern.MULTILINE);
        Matcher regMat2 = regPat2.matcher(routesData);
        return regMat2.find();
    }

    /**
     * get include model route
     * @param modelPath
     * @param routesData
     * @return
     */
    public static List<String> getIncludeModel(String modelPath, String routesData, CrudSetting crudSetting) {
        List<String> result = new ArrayList<String>();
        Pattern regPat2 = Pattern.compile(RoutesTagTemplate.getRoutesTagReg(modelPath, crudSetting), Pattern.MULTILINE);
        Matcher regMat2 = regPat2.matcher(routesData);
        while(regMat2.find()) {
            result.add(regMat2.group());
        }
        return result;
    }

    /**
     * get delete route tag
     * @param modelPath
     * @param routesData
     * @param crudSetting
     * @return
     */
    public static String deleteRouteTag(String modelPath, String routesData, CrudSetting crudSetting) {
        String pattern = RoutesTagTemplate.getRoutesTagReg(modelPath, crudSetting);
        String escapedPattern = pattern.replaceAll("/", "\\\\/");
        Pattern regPat2 = Pattern.compile(escapedPattern, Pattern.MULTILINE);
        Matcher regMat2 = regPat2.matcher(routesData);
        return regMat2.replaceAll("");
    }

}
