package com.miraway.mss.modules.common.exception;

public class ForbiddenException extends Exception {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        super("Người dùng hiện tại không có quyền.");
    }
}
