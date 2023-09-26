package com.miraway.mss.modules.common.validator;

import static com.miraway.mss.constants.Constants.DATABASE_ID_REGEX;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.annotation.Nullable;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

// validate database id when doing filtering, it can null or a valid database id length

@Pattern(regexp = DATABASE_ID_REGEX)
@Nullable
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ConstraintComposition(CompositionType.OR)
public @interface OptionalDatabaseIdConstraint {
    // TODO: multi language message
    String message() default "Invalid Id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
