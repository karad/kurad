package jp.greative.util;

import jp.greative.kurad.app.setting.CrudSetting;
import jp.greative.kurad.app.template.DefaultTemplate;
import play.Logger;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Mode utility
 */
public class ModelUtil {

    public final static String PACKAGE_SEPARATOR = ".";

    /**
     * get class list
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    public static LinkedHashMap<String, LinkedHashMap<String, Field>> getFormList(String packageName, String className) throws ClassNotFoundException {
        Hashtable<String, String> packageNames = new Hashtable<String, String>();
        packageNames.put(className, packageName + PACKAGE_SEPARATOR + className);
        Enumeration<String> keys = packageNames.keys();
        LinkedHashMap<String, LinkedHashMap<String, Field>> result = new LinkedHashMap<String, LinkedHashMap<String, Field>>();
        LinkedHashMap<String, Field> tmp;
        while (keys.hasMoreElements()) {
            tmp = new LinkedHashMap<String, Field>();
            String key = keys.nextElement();
            Class<?> targetClass = Class.forName(packageNames.get(key));
            Field[] fields = targetClass.getFields();
            Boolean isField;
            for (Field field : fields) {
                isField = true;
                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation instanceof javax.persistence.Transient) {
                        isField = false;
                    }
                }
                if (isField) {
                    tmp.put(field.getName(), field);
                }
            }
            result.put(packageNames.get(key), tmp);
        }
        return result;

    }

    /**
     * is Class Ebean model
     * @param packageName ex."models"
     * @param className ex."Admin"
     * @return result
     * @throws ClassNotFoundException
     */
    public static Boolean isEbeanModel(String packageName, String className) throws ClassNotFoundException {
        Hashtable<String, String> packageNames = new Hashtable<String, String>();
        packageNames.put(className, packageName + PACKAGE_SEPARATOR + className);
        Enumeration<String> keys = packageNames.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Class<?> targetClass = Class.forName(packageNames.get(key));
            for (Annotation annotation : targetClass.getAnnotations()) {
                if (annotation instanceof javax.persistence.Entity) {
                    return true;
                }
                Logger.info("[annotation:] " + annotation.toString());
            }
        }
        return false;
    }

    /**
     * todo add update for ManyToMany
     *
     * @return
     */
    public static Map<String,String> getModelAddLogics(LinkedHashMap<String, Field> fields) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        Iterable<String> i = fields.keySet();
        for(String key : i) {
            for(Annotation annotation : fields.get(key).getAnnotations()) {
                if(
                        annotation instanceof OneToOne ||
                        annotation instanceof ManyToMany
                ) {
                    ParameterizedType parameterizedType  = (ParameterizedType)fields.get(key).getGenericType();
                    Class paramCls = (Class)parameterizedType.getActualTypeArguments()[0];
                    result.put(paramCls.getSimpleName(), key);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * get model field association type
     * @param fields
     * @return
     */
    public static List<String> getModelFetchFields(LinkedHashMap<String, Field> fields) {
        List<String> results = new ArrayList<String>();
        Iterable<String> i = fields.keySet();
        for(String key : i) {
            for(Annotation annotation : fields.get(key).getAnnotations()) {
                if(
                        annotation instanceof ManyToOne ||
                        annotation instanceof OneToMany ||
                        annotation instanceof OneToOne ||
                        annotation instanceof ManyToMany
                ) {
                    results.add(key);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * get model method
     * @param fields
     * @return
     */
    public static List<String> getModelMethods(String baseName, LinkedHashMap<String, Field> fields, String modelId, CrudSetting crudSetting) {
        List<String> results = new ArrayList<String>();
        Iterable<String> i = fields.keySet();
        for(String key : i) {
            for(Annotation annotation : fields.get(key).getAnnotations()) {
                if(
                        annotation instanceof ManyToOne ||
                        annotation instanceof OneToOne
                ) {
                    results.add(getOptionMethod(fields.get(key).getType().getSimpleName(), modelId, crudSetting));
                    break;
                } else if (
                        annotation instanceof OneToMany ||
                        annotation instanceof ManyToMany
                ) {
                    ParameterizedType parameterizedType  = (ParameterizedType)fields.get(key).getGenericType();
                    Class paramCls = (Class)parameterizedType.getActualTypeArguments()[0];
                    results.add(getOptionMethod(paramCls.getSimpleName(), modelId, crudSetting));
                    break;
                }
            }
        }
        return results;
    }

    /**
     * get model option template
     * @param model
     * @return
     */
    public static String getOptionMethod(final String model, final String modelId, CrudSetting crudSetting) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("model", model);
        map.put("modelId", modelId);
        return DefaultTemplate.view("optionModel", crudSetting, map);
    }

}
