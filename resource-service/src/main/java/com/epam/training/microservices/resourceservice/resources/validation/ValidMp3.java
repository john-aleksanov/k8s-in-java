package com.epam.training.microservices.resourceservice.resources.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = {Mp3Validator.class})
public @interface ValidMp3 {
    String message() default "The file is not a valid mp3 file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
