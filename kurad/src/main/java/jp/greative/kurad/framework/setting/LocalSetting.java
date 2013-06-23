package jp.greative.kurad.framework.setting;

/**
 * Local setting interface
 */
public interface LocalSetting {

    public String getTemplatePath();

    public void setTemplatePath(String templatePath);

    public String getTemplateBasename();

    public void setTemplateBasename(String templateBasename);

    public String getCharacterCode();

    public void setCharacterCode(String characterCode);

    public String getExtension();

    public void setExtension(String extention);

}
