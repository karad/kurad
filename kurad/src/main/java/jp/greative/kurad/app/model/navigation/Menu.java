package jp.greative.kurad.app.model.navigation;

import com.google.common.base.CaseFormat;

/**
 * Menu bean
 */
public class Menu {

    private final String routeName;
    private final String className;

    public Menu(String className) {
        this.className = className;
        this.routeName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, className);
    }

    public String getRouteName() {
        return routeName;
    }

    public String getClassName() {
        return className;
    }

}
