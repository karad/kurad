package jp.greative.kurad.framework.model.contentType;

public class Html implements Content{
    
    private String contentType = "text/html";
    private String body = "";
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public Html addBody(String body) {
        this.body = this.body + "\n" + body;
        return new Html(this.body);
    }

    /**
     * Constructor
     */
    public Html() {
        
    }
    
    /**
     * Constructor
     */
    public Html(String body) {
        this.body = body;
    }
    
    /**
     * 
     */
    public String toString() {
        return this.body;
    }

    /**
     * 
     * @param body
     * @return
     */
    public static Html make(String body) {
        return new Html(body);
    }
}
