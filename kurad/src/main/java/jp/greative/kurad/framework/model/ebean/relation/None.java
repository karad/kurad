package jp.greative.kurad.framework.model.ebean.relation;

import java.lang.reflect.Field;

/**
 * Ebean annotation has none
 */
public class None implements Relation {

    private Field field;

    @Override
    public None getRelationType() {
        return this;
    }
    
    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }
    
}
