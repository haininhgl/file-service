package com.miraway.mss.constants;

import org.springframework.data.domain.Sort;

public final class Constants {

    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String DISPLAY_NAME = "displayName";
    public static final String PARENT_ID = "parentId";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String THUMBNAIL_PATH = "D:/miraway/folder.svg";
    public static final String INIT_DATA_TOPIC = "initData";
    public static final String ROOT_ORGANIZATION_NAME = "__ROOT_ORGANIZATION__";
    public static final String ORGANIZATION_ID = "organizationId";
    public static final String ID = "_id";

    // validation constants
    public static final int STRING_MAX_LENGTH = 256;

    public static final String SYSTEM = "system";

    public static final String DATABASE_ID_REGEX = "^[a-fA-F0-9]{24}$";

    // pagination
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGEABLE_INDEX = 0;
    public static final int DEFAULT_PAGINATION_REQUEST_INDEX = DEFAULT_PAGEABLE_INDEX + 1;
    public static final String DESCENDING_SORT_DIRECTION = Sort.Direction.DESC.name();
    public static final String DEFAULT_SORT_DIRECTION = DESCENDING_SORT_DIRECTION;
    public static final String DEFAULT_SORT_BY = LAST_MODIFIED_DATE;
}



