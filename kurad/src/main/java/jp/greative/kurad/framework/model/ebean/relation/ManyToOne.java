package jp.greative.kurad.framework.model.ebean.relation;

import java.lang.reflect.Field;

/**
 * Ebean annotation many to one
 */
public class ManyToOne implements Relation {

    private Field field;
    
    @Override
    public ManyToOne getRelationType() {
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
