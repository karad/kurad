package jp.greative.kurad.framework.setting;

/**
 * List settings
 */
public class ListSetting {
    private String wrappedElement = "div";
    private String wrappedClass = "";
    private String wrappedId = "";
    private String displayClass = "";

    public String getWrappedElement() {
        return wrappedElement;
    }

    public void setWrappedElement(String wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    public String getWrappedClass() {
        return wrappedClass;
    }

    public void setWrappedClass(String wrappedClass) {
        this.wrappedClass = wrappedClass;
    }

    public String getWrappedId() {
        return wrappedId;
    }

    public void setWrappedId(String wrappedId) {
        this.wrappedId = wrappedId;
    }

    public String getDisplayClass() {
        return displayClass;
    }

    public void setDisplayClass(String displayClass) {
        this.displayClass = displayClass;
    }
}
