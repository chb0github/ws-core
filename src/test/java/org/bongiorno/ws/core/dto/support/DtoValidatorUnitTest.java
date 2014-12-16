package org.bongiorno.ws.core.dto.support;

import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DtoValidatorUnitTest {

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Validator validator;

    @InjectMocks
    private DtoValidator dtoValidator = new DtoValidator();

    private Object[] args = new Object[]{"argString",null};

    @Test
    public void testDoValidation() throws Exception{
        when(joinPoint.getArgs()).thenReturn(args);
        when(validator.validate("argString")).thenReturn(new HashSet<>());

        dtoValidator.doValidation(joinPoint);
    }

    public void testDoValidationWithErrors() throws Exception{
        Set<ConstraintViolation<String>> violations = new HashSet<ConstraintViolation<String>>();
        violations.add(new TestConstrainViolation<>("test constraint"));
        when(joinPoint.getArgs()).thenReturn(args);
        when(validator.validate("argString")).thenReturn(violations);

        dtoValidator.doValidation(joinPoint);
    }

    // eliminates the hibernate validator dependency so this can be moved to WS core
    private static class TestConstrainViolation<T> implements ConstraintViolation<T> {
        
        private String message;

        private TestConstrainViolation() {
        }

        private TestConstrainViolation(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getMessageTemplate() {
            return null;  
        }

        @Override
        public T getRootBean() {
            return null;  
        }

        @Override
        public Class<T> getRootBeanClass() {
            return null;  
        }

        @Override
        public Object getLeafBean() {
            return null;  
        }

        @Override
        public Path getPropertyPath() {
            return null;
        }

        @Override
        public Object getInvalidValue() {
            return null;  
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;  
        }
    }
}
