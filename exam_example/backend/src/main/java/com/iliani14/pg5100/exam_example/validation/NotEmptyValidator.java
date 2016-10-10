package com.iliani14.pg5100.exam_example.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by anitailieva on 07/10/2016.
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty,String> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return false;
        }

        return  ! value.trim().isEmpty();
    }
}
