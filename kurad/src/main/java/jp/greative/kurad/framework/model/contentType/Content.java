package jp.greative.kurad.framework.model.contentType;

/**
 * Content interface
 */
public interface Content {

    public String getContentType();
    public void setContentType(String contentType);
    public String getBody();
    public void setBody(String body);
    public String toString();

}
