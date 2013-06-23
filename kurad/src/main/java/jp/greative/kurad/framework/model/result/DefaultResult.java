package jp.greative.kurad.framework.model.result;

import jp.greative.util.FileUtil;
import org.apache.ivy.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Default result
 */
public class DefaultResult implements Result {

    private List<String> result;

    @Override
    public String response() {
        return "";
    }

    @Override
    public List<String> get() {
        if(result == null) {
            return new ArrayList<String>();
        }
        return result;
    }

    @Override
    public List<String> add(String result) {
        if(this.result == null) {
            this.result = new ArrayList<String>();
        }
        this.result.add(result);
        return this.result;
    }

    @Override
    public List<String> addAll(List<String> results) {
        if(this.result == null) {
            this.result = new ArrayList<String>();
        }
        if(results == null) {
            return this.result;
        }
        this.result.addAll(results);
        return this.result;
    }

    /**
     * report result
     * @return
     */
    public String report() {
        if(this.result == null) {
            return "";
        }
        result.add("--------------------------------------------------------------");
        return StringUtils.join(result.toArray(), FileUtil.LINE_SEPARATOR);
    }

}
