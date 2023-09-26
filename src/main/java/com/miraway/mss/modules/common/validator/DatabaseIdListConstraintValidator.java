package com.miraway.mss.modules.common.validator;

import com.miraway.mss.constants.Constants;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class DatabaseIdListConstraintValidator implements ConstraintValidator<DatabaseIdListConstraint, Set<String>> {

    @Override
    public void initialize(DatabaseIdListConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set<String> idList, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(idList)) {
            return true;
        }

        idList = idList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(idList)) {
            return true;
        }

        return idList.stream().allMatch(s -> s.matches(Constants.DATABASE_ID_REGEX));
    }
}
