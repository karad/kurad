package jp.greative.kurad.framework.controller.writer;

import jp.greative.kurad.framework.setting.LocalSetting;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Writer interface
 */
public interface Writer {

    public List<String> apply(String baseName, String path, LocalSetting localSetting, LinkedHashMap<String, String> option);
    public List<String> apply(String baseName, String path, LocalSetting localSetting);

}
