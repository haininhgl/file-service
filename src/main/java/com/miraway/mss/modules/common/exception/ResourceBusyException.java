package com.miraway.mss.modules.common.exception;

public class ResourceBusyException extends Exception {

    private static final long serialVersionUID = 1L;

    public ResourceBusyException() {
        super("Resource is being used.");
    }

    public ResourceBusyException(String defaultMessage) {
        super(defaultMessage);
    }
}
