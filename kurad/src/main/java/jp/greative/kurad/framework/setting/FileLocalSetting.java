package jp.greative.kurad.framework.setting;

/**
 * File local settings
 */
public class FileLocalSetting  {
    public String characterCode = "utf-8";
    public String extension  = ".txt";

    public String templatePath;
    public String templateBasename;

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplateBasename() {
        return templateBasename;
    }

    public void setTemplateBasename(String templateBasename) {
        this.templateBasename = templateBasename;
    }
}
