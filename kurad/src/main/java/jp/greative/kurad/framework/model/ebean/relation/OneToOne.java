package jp.greative.kurad.framework.model.ebean.relation;

import java.lang.reflect.Field;

/**
 * Ebean annotation one to one
 */
public class OneToOne implements Relation {

    private Field field;

    @Override
    public OneToOne getRelationType() {
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
