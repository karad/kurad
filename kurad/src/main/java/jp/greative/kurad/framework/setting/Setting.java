package jp.greative.kurad.framework.setting;

import com.typesafe.config.Config;
import java.util.Map;
import static play.libs.F.*;

/**
 * Setting interface
 */
public interface Setting {

    public void setSettings(Map<String, String> settings);
    public Map<String, String> getSettings();
    public Option<String> getSetting(String key);
    public void setSetting(String key, String value);
    public Option<Config> loadSettings();

}
