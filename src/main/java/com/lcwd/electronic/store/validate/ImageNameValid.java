package com.lcwd.electronic.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//Rule kaha pe hoga - ImageNameValidator class mei hoga
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //Default error message
    String message() default "Invalid Image Name !!";

    //Represents group of constraints
    Class<?>[] groups() default { };

    //Additional information about annotation
    Class<? extends Payload>[] payload() default { };
}
