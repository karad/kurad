package jp.greative.kurad.framework.setting;

/**
 * Form settings
 */
public class FormSetting {

    /** for multi part form data */
    public final static String MULTIPART_FORM = "multipart/form-data";

    /** form method type */
    private String methodType = "POST";
    /** form action */
    private String action = "";
    /** form encoding type */
    private String enctype = "";

    public static String getMultipartForm() {
        return MULTIPART_FORM;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEnctype() {
        return enctype;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }
}
