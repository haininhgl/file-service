package com.miraway.mss.modules.common.validator;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DatabaseIdListConstraintValidator.class)
@Target({ PARAMETER, TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseIdListConstraint {
    // TODO: multi language message
    String message() default "Invalid Id in list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
