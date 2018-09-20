package com.joojava.config.liquibase;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.SQLException;

import static com.joojava.config.FrameworkConstants.SPRING_PROFILE_DEVELOPMENT;
import static com.joojava.config.FrameworkConstants.SPRING_PROFILE_HEROKU;
import static com.joojava.config.FrameworkConstants.SPRING_PROFILE_NO_LIQUIBASE;

/**
 * @author leegive
 */
@Slf4j
public class AsyncSpringLiquibase extends SpringLiquibase {

    public static final String DISABLED_MESSAGE = "Liquibase is disabled";
    public static final String STARTING_ASYNC_MESSAGE = "Starting Liquibase asynchronously, your database might not be ready at startup!";
    public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
    public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
    public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: {}";
    public static final long SLOWNESS_THRESHOLD = 5;
    public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";

    private final TaskExecutor taskExecutor;
    private final Environment env;

    public AsyncSpringLiquibase(@Qualifier("taskExecutor") TaskExecutor taskExecutor, Environment env) {
        this.taskExecutor = taskExecutor;
        this.env = env;
    }

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        if (!env.acceptsProfiles(SPRING_PROFILE_NO_LIQUIBASE)) {
            if (env.acceptsProfiles(SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_HEROKU)) {
                try (Connection connection = getDataSource().getConnection()) {
                    taskExecutor.execute(() -> {
                        try {
                            log.warn(STARTING_ASYNC_MESSAGE);
                            initDb();
                        } catch (LiquibaseException e) {
                            log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
                        }
                    });
                } catch (SQLException e) {
                    log.error(EXCEPTION_MESSAGE, e.getMessage(), e);
                }
            } else {
                log.debug(STARTING_SYNC_MESSAGE);
                initDb();
            }
        } else {
            log.debug(DISABLED_MESSAGE);
        }
    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
        if (watch.getTotalTimeMillis() > SLOWNESS_THRESHOLD * 1000L) {
            log.warn(SLOWNESS_MESSAGE, SLOWNESS_THRESHOLD);
        }
    }
}
