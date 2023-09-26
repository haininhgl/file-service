package com.miraway.mss.utils;

import com.miraway.mss.modules.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIUtils {

    private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

    public static <T> ResponseEntity<T> handleException(Exception e, String message) {
        log.error(message, e);

        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (e instanceof InternalServerException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (e instanceof ForbiddenException) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (e instanceof ParseDataException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (e instanceof ResourceBusyException) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (e instanceof ResourceNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Exception handleHttpFailure(int status) {
        switch (status) {
            case 400:
                return new BadRequestException("Bad request");
            case 403:
                return new ForbiddenException("Forbidden");
            case 409:
                return new ResourceBusyException("Resource busy");
            case 404:
                return new ResourceNotFoundException("Resource not found");
            default:
                return new InternalServerException("Internal server error");
        }
    }

    private APIUtils() {
        throw new IllegalStateException("Utility class");
    }
}
