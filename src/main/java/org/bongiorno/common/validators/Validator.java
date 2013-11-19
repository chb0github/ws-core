package org.bongiorno.common.validators;

public interface Validator<T> {

    public boolean validate(T object);
}
