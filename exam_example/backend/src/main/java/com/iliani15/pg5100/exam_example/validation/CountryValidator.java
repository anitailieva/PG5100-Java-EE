package com.iliani15.pg5100.exam_example.validation;

import com.iliani15.pg5100.exam_example.Countries;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by anitailieva on 07/10/2016.
 */
public class CountryValidator implements ConstraintValidator<Country,String> {
    @Override
    public void initialize(Country constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return Countries.getCountries().stream()
                .anyMatch(s -> s.equalsIgnoreCase(value));
    }
}

