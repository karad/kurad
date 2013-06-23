package jp.greative.kurad.framework.model.ebean.relation;

import java.lang.reflect.Field;

/**
 * Ebean annotation interface
 */
public interface Relation {
    
    public Field getField();
    public void setField(Field field);
    public Relation getRelationType();
}
