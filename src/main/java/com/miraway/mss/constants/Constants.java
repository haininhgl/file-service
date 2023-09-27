package com.miraway.mss.constants;

public final class Constants {

    // validation constants
    public static final int STRING_MAX_LENGTH = 256;

    public static final String SYSTEM = "system";

    public static final String DATABASE_ID_REGEX = "^[a-fA-F0-9]{24}$";

    private Constants() {
        throw new IllegalStateException("Constant class");
    }
}



