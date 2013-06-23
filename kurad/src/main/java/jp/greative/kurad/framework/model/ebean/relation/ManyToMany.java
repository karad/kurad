package jp.greative.kurad.framework.model.ebean.relation;

import java.lang.reflect.Field;

/**
 * Ebean annotation many to many
 */
public class ManyToMany implements Relation {
    
    private Field field;
    
    @Override
    public ManyToMany getRelationType() {
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
