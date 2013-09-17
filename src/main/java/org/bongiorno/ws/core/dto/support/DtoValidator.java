package org.bongiorno.ws.core.dto.support;

import org.bongiorno.ws.core.exceptions.BadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.bongiorno.ws.core.exceptions.BadRequestException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * To be portable this aspect needs to be config driven, not annotated
 *
 * if you ever need to do method validation please see version 484 in the svn history
 */
@Aspect
public class DtoValidator {

    private Validator validator;

    public DtoValidator() {
    }

    public DtoValidator(Validator validator) {
        this.validator = validator;
    }

    public void doValidation(JoinPoint jp){
        for( Object arg : jp.getArgs() ){
            if (arg != null) {
                Set<ConstraintViolation<Object>> violations = validator.validate(arg);
                if( violations.size() > 0 ){
                    throw buildError(violations);
                }
            }
        }
    }

    private static BadRequestException buildError( Set<ConstraintViolation<Object>> violations ){
        Map<String, String> errorMap = new HashMap<String, String>();
        for( ConstraintViolation error : violations ){
            errorMap.put(error.getPropertyPath().toString(), error.getMessage());
        }
        return new BadRequestException(errorMap);
    }
}
