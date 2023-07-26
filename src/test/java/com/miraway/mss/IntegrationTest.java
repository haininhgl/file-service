package com.miraway.mss;

import com.miraway.mss.FileManagerApp;
import com.miraway.mss.config.AsyncSyncConfiguration;
import com.miraway.mss.config.EmbeddedMongo;
import com.miraway.mss.config.EmbeddedRedis;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { FileManagerApp.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedMongo
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
