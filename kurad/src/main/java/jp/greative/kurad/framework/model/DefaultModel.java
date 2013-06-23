package jp.greative.kurad.framework.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Default model
 */
public class DefaultModel implements Model {

    /**
     * get settings
     * @return
     */
    public static Map<String, String> getSettings() {
        return new HashMap<String, String>();
    }

}
