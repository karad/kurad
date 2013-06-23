package jp.greative.kurad.framework.model.result;

import java.util.List;

/**
 * Result interface
 */
public interface Result {

    public String response();
    public List<String> get();
    public List<String> add(String result);
    public List<String> addAll(List<String> results);

}
