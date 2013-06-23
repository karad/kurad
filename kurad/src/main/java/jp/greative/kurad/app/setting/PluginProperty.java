package jp.greative.kurad.app.setting;

import jp.greative.util.OptionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import static play.libs.F.*;

/**
 * Plugin property utility
 */
public class PluginProperty {

    private static final String PROPERTY_PATH = "resources/plugin.properties";
    private static final String PROPERTY_PATH_CUSTOMIZE = "app/templates/plugin.properties";
    private static final String ENCODING = "UTF-8";
    private static Properties prop;

    /**
     * get property
     * @param key
     * @return
     */
    public static Option<String> getProperty(String key) {
        if(prop == null) {
            Properties p = new Properties();
            try {
                File file = new File(PROPERTY_PATH_CUSTOMIZE);
                if(file.exists()) {
                    p.load(new InputStreamReader(new FileInputStream(PROPERTY_PATH_CUSTOMIZE), ENCODING));
                } else {
                    p.load(new InputStreamReader(new FileInputStream(PROPERTY_PATH), ENCODING));
                }
                prop = p;
            } catch (IOException e) {
                e.printStackTrace();
                return new None<String>();
            }
        }
        return OptionUtil.apply(prop.getProperty(key));
    }
}
