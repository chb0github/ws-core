package org.bongiorno.common.validators;

/**
 * @author cbongiorno
 *         Date: 4/24/12
 *         Time: 11:29 AM
 */
public interface Validator<T> {

    public boolean validate(T object);
}
