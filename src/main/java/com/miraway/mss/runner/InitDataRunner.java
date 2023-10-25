package com.miraway.mss.runner;

import com.miraway.mss.kafka.messages.InitDataMessage;
import com.miraway.mss.kafka.messages.Permission;
import com.miraway.mss.kafka.producers.InitDataProducer;
import com.miraway.mss.utils.DataUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Order(1)
@Component
public class InitDataRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(InitDataRunner.class);

    private final InitDataProducer initDataProducer;

    public InitDataRunner(InitDataProducer initDataProducer) {
        this.initDataProducer = initDataProducer;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("InitDataRunner run");
            File permissionFile = ResourceUtils.getFile("classpath:data/permissions.json");
            String permissionFileContent = Files.readString(permissionFile.toPath(), StandardCharsets.UTF_8);

            Permission[] mappedPermissions = DataUtils.readValue(permissionFileContent, Permission[].class);
            Set<Permission> permissions = Arrays
                .stream(mappedPermissions)
                .map(permission ->
                    new Permission(
                        permission.getGroup(),
                        permission.getResource(),
                        permission.getAction(),
                        permission.getGroupName(),
                        permission.getResourceName(),
                        permission.getActionName()
                    )
                )
                .collect(Collectors.toSet());

            InitDataMessage message = new InitDataMessage(permissions);

            initDataProducer.sendMessage(message);
        } catch (Exception e) {
            log.error("InitDataRunner exception: ", e);
        }
    }
}
