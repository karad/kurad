package jp.greative.kurad.framework.controller;

import jp.greative.kurad.framework.model.result.DefaultResult;

/**
 * Default controller
 */
public class DefaultController implements Controller {

    public String apply(String mode, String model) {
        DefaultResult result = new DefaultResult();
        return result.report();
    }

}



