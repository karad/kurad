package jp.greative.kurad.app.controller.writer;

import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import jp.greative.kurad.framework.model.contentType.Html;
import jp.greative.kurad.framework.setting.InputSetting;
import jp.greative.kurad.framework.setting.ListSetting;
import jp.greative.kurad.app.setting.Output;
import jp.greative.util.TextUtil;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Form writer
 */
public class FormWriter {

    /**
     * get form tag
     * @param field
     * @param crudSetting
     * @return
     */
    public static Html getInputFromCode(Field field, CrudSetting crudSetting) {
        String result = "";
        if (field.getGenericType() == String.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is String");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof javax.persistence.Column) {
                    if(((Column) annotation).columnDefinition().equals("TEXT")) {
                        result = getTextAreaFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
                        break;
                    }
                }
            }
        } else if (field.getGenericType() == Long.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Long");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Date.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Date");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Boolean.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Boolean");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Integer.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Integer");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Float.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Float");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Double.class) {
            // System.out.println("[CRUD:Field] " + field.getName() + " is Double");
            result = getInputTextFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof javax.persistence.ManyToOne) {
                    String targetClassName = field.getType().getSimpleName();
                    String declaredClassName = field.getDeclaringClass().getSimpleName();
                    // System.out.println("[CRUD:Field] " + field.getName() + " / " + "ManyToOne");
                    result = getInputTextFromTemplateByTableWithManyToOne(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.ManyToMany) {
                    ParameterizedType parameterizedType  = (ParameterizedType)field.getGenericType();
                    Class paramCls = (Class)parameterizedType.getActualTypeArguments()[0];
                    String targetClassName = paramCls.getSimpleName();
                    String declaredClassName = field.getDeclaringClass().getSimpleName();
                    // System.out.println("[CRUD:Field] " + field.getName() + " / " + "ManyToMany");
                    result = getInputTextFromTemplateByTableWithManyToMany(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.OneToMany) {
                    ParameterizedType parameterizedType  = (ParameterizedType)field.getGenericType();
                    Class paramCls = (Class)parameterizedType.getActualTypeArguments()[0];
                    String targetClassName = paramCls.getSimpleName();
                    String declaredClassName = field.getDeclaringClass().getSimpleName();
                    // System.out.println("[CRUD:Field] " + field.getName() + " / " + "OneToMany");
                    result = getInputTextFromTemplateByTableWithOneToMany(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.OneToOne) {
                    String targetClassName = field.getType().getSimpleName();
                    String declaredClassName = field.getDeclaringClass().getSimpleName();
                    // System.out.println("[CRUD:Field] " + field.getName() + " / " + "OneToOne");
                    result = getInputTextFromTemplateByTableWithOneToOne(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
            }
        }
        return Html.make(result);
    }

    /**
     * get display tag
     * @param field
     * @param crudSetting
     * @return
     */
    public static Html getDisplayFieldFromCode(Field field, CrudSetting crudSetting) {
        String result = "";
        if (field.getGenericType() == String.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Long.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Date.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Boolean.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Integer.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Float.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else if (field.getGenericType() == Double.class) {
            result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
        } else {
            String targetClassName = field.getType().getSimpleName();
            String declaredClassName = field.getDeclaringClass().getSimpleName();
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof javax.persistence.ManyToOne) {
                    result = getDisplayFieldFromTemplateByTableWithManyToOne(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.ManyToMany) {
                    result = getDisplayFieldFromTemplateByTableWithManyToMany(field.getName(), field.getDeclaringClass().getName(), targetClassName, declaredClassName, crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.OneToMany) {
                    result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
                    break;
                }
                if (annotation instanceof javax.persistence.OneToOne) {
                    result = getDisplayFieldFromTemplateByTable(field.getName(), field.getDeclaringClass().getName(), crudSetting);
                    break;
                }
            }
        }
        return Html.make(result);
    }

    /**
     * get input text normal
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplate(String name, String model, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("getName", TextUtil.toUpperCase(name));
        map.put("setName", TextUtil.toUpperCase(name));
        map.put("model", model);
        map.put("inputOption", inputSetting);
        String templateName = "inputNormal";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get input text table
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplateByTable(String name, String model, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("getName", TextUtil.toUpperCase(name));
        map.put("setName", TextUtil.toUpperCase(name));
        map.put("model", model);
        map.put("inputOption", inputSetting);
        String templateName = "inputTable";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get textarea table
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getTextAreaFromTemplateByTable(String name, String model, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("inputOption", inputSetting);
        String templateName = "textareaTable";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get display field
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getDisplayFieldFromTemplateByTable(String name, String model, CrudSetting crudSetting) {
        String result = "";
        ListSetting listSetting = new ListSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("listOption", listSetting);
        String templateName = "listTable";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get display field table many to one
     * @param name
     * @param model
     * @param targetClassName
     * @param declaredClassName
     * @param crudSetting
     * @return
     */
    private static String getDisplayFieldFromTemplateByTableWithManyToOne(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        ListSetting listSetting = new ListSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("listOption", listSetting);
        map.put("modelId", crudSetting.getModelId());
        String templateName = "listTableManyToOne";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get display field table many to many
     * @param name
     * @param model
     * @param targetClassName
     * @param declaredClassName
     * @param crudSetting
     * @return
     */
    private static String getDisplayFieldFromTemplateByTableWithManyToMany(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        ListSetting listSetting = new ListSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("listOption", listSetting);
        map.put("modelId", crudSetting.getModelId());
        String templateName = "listTableManyToMany";
        return DefaultTemplate.view(templateName, crudSetting, map);

    }

    /**
     * get input text table many to one
     * @param name
     * @param model
     * @param targetClassName
     * @param declaredClassName
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplateByTableWithManyToOne(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("inputOption", inputSetting);
        map.put("modelId", crudSetting.getModelId());
        map.put("crudSetting", crudSetting);

        String templateName = "inputTableWithManyToOne";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get input text table many to many
     * @param name
     * @param model
     * @param targetClassName
     * @param declaredClassName
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplateByTableWithManyToMany(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("inputOption", inputSetting);
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("modelId", crudSetting.getModelId());
        map.put("crudSetting", crudSetting);
        String templateName = "inputTableWithManyToMany";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get input text table one to many
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplateByTableWithOneToMany(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("inputOption", inputSetting);
        map.put("modelId", crudSetting.getModelId());
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("crudSetting", crudSetting);
        String templateName = "inputTableWithOneToMany";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

    /**
     * get input text table one to one
     * @param name
     * @param model
     * @param crudSetting
     * @return
     */
    private static String getInputTextFromTemplateByTableWithOneToOne(String name, String model, String targetClassName, String declaredClassName, CrudSetting crudSetting) {
        String result = "";
        InputSetting inputSetting = new InputSetting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("model", model);
        map.put("inputOption", inputSetting);
        map.put("modelId", crudSetting.getModelId());
        map.put("targetClassName", targetClassName);
        map.put("declaredClassName", declaredClassName);
        map.put("crudSetting", crudSetting);
        String templateName = "inputTableWithOneToOne";
        return DefaultTemplate.view(templateName, crudSetting, map);
    }

}
