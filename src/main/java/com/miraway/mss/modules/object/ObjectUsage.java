package com.miraway.mss.modules;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class objectUsage {

    @Id
    private String id;

    @DBRef
    private Object object;

    private boolean isDeletable = false;

    
    private String clientService;

    private String clientId;

    private String clientName;


}
