package org.bongiorno.common.validators;

import org.bongiorno.common.utils.QuickCollection;
import org.bongiorno.common.utils.QuickCollection;

import java.util.*;


public class CompositeValidator<T> extends QuickCollection<T> implements Validator<T>{

    public CompositeValidator() {
        super.delegate = new LinkedHashSet<T>();
    }

    public CompositeValidator(Set<T> validators) {
        super.delegate = validators;
    }

    public CompositeValidator(T ... validators) {
        this(new HashSet<T>(Arrays.asList(validators)));
    }

    @Override
    public boolean validate(T object) {
      return true;
    }

    public Collection<T> getValidators() {
        return Collections.unmodifiableCollection(super.delegate);
    }
}
