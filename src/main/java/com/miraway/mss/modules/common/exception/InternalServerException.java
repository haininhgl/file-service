package com.miraway.mss.modules.common.exception;

public class InternalServerException extends Exception {

    private static final long serialVersionUID = 1L;

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException() {
        super("Lỗi máy chủ.");
    }
}
