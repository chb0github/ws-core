package org.bongiorno.ws.core.exceptions;

/**
 * May be thrown upon encountering an identifier that refers to an entity that does not exist.
 */
public class NonexistentEntityException extends ServiceException {

    private String entityDescription;

    private Class<?> entity;

    private Object id;

    public NonexistentEntityException(Throwable cause, Class<?> entity, Object id){
        super(cause, "%s '%s' does not exist", entity.getSimpleName(), id);
        entityDescription = entity.getSimpleName() + ' ' + id;
        this.entity = entity;
        this.id = id;
    }


    public NonexistentEntityException(Class<?> entity, Object id) {
        this(null, entity, id);
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public Class<?> getEntity() {
        return entity;
    }

    public Object getId() {
        return id;
    }
}
