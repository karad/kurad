package jp.greative.kurad.app.model.mode;

import java.util.Arrays;
import java.util.List;

/**
 * Mode enum
 */
public enum Mode {

    ALL("all"),
    SERVICE("service"),
    CONTROLLER("controller"),
    VIEW("view"),
    OTHER("other"),
    ROUTE("route"),
    NONE("");

    /**
     * matching enum by string
     * @param mode
     * @return
     */
    public static Mode is(String mode) {
        return getEnum(mode);
    }

    private String name;
    private Mode(String s) {
        this.name = s;
    }


    public static Mode getEnum(String str) {
        List<Mode> list = Arrays.asList(Mode.values());
        for(Mode e : list) {
            if (str.equals(e.name)){
                return e;
            }
        }
        return NONE;
    }

    @Override
    public String toString() {
        return name;
    }
}
