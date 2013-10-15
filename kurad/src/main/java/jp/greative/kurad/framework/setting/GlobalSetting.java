package jp.greative.kurad.framework.setting;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jp.greative.util.OptionUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static play.libs.F.*;

/**
 * Global settings
 */
public class GlobalSetting implements Setting {

    private static final GlobalSetting instance = new GlobalSetting();

    private Map<String, String> settings = new HashMap<String, String>();

    private Config configs;

    private GlobalSetting() {}

    public static Setting getInstance() {
        return instance;
    }

    private static final String PROPERTY_PATH = "resources/conf/kurad.conf";
    private static final String PROPERTY_PATH_CUSTOMIZE = "kurad/templates/conf/kurad.conf";

    /**
     * get HOCON file
     */
    @Override
    public Option<Config> loadSettings() {
        Option<File> file = getFile(PROPERTY_PATH_CUSTOMIZE, PROPERTY_PATH);
        if(file.isDefined()) {
            Config conf = ConfigFactory.load(ConfigFactory.parseFileAnySyntax(file.get()));
            configs = conf;
            return OptionUtil.apply(conf);
        } else {
            return new None<Config>();
        }

    }

    /**
     * get file
     * @param loadPath
     * @param fallbackPath
     * @return
     */
    public Option<File> getFile(String loadPath, String fallbackPath) {
        File file = new File(loadPath);
        if(file.exists()) {
            return OptionUtil.apply(file);
        } else {
            File fallbackFile = new File(fallbackPath);
            if(fallbackFile.exists()) {
                return OptionUtil.apply(fallbackFile);
            } else {
                return new None<File>();
            }
        }
    }

    /**
     * set settings
     * @param key
     * @param value
     */
    @Override
    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    /**
     * get setting
     * @param key
     * @return
     */
    @Override
    public Option<String> getSetting(String key) {
        if(settings.containsKey(key)) {
            return new Some<String>(settings.get(key));
        } else {
            return new None<String>();
        }
    }

    public Config getConfigs() {
        return configs;
    }

    public void setConfigs(Config configs) {
        this.configs = configs;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

}
