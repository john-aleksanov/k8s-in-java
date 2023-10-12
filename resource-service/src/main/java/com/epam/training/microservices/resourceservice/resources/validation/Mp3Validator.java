package com.epam.training.microservices.resourceservice.resources.validation;

import com.epam.training.microservices.resourceservice.exceptions.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class Mp3Validator implements ConstraintValidator<ValidMp3, MultipartFile> {

    @Override
    public void initialize(ValidMp3 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty() || !file.getContentType().equals("audio/mpeg")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
