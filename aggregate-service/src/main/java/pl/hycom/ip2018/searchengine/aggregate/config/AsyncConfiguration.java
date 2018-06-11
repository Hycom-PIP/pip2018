package pl.hycom.ip2018.searchengine.aggregate.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for scheduling and asynchronus execution.
 *
 * @author Hubert Pruszynski (hubert.pruszynski@hycom.pl)
 *
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {

    /**
     * Async executor core pool size.
     */
    @Value("${app.async-executor.core-pool-size:5}")
    private int asyncExecutorCorePoolSize;

    /**
     * Async executor max pool size.
     */
    @Value("${app.async-executor.max-pool-size:50}")
    private int asyncExecutorMaxPoolSize;

    /**
     * The {@link Executor} instance for processing async tasks.
     *
     * @return async tasks executor bean
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncExecutorCorePoolSize);
        executor.setMaxPoolSize(asyncExecutorMaxPoolSize);
        executor.initialize();
        return executor;
    }

    /**
     * The {@link AsyncUncaughtExceptionHandler} implementation that will log errors from async tasks.
     *
     * @return async tasks exception handler bean
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable throwable, Method method, Object... obj) -> {
            if (log.isErrorEnabled()) {
                log.error("Error in async task\nMethod name - " + method.getName());
                for (Object param : obj) {
                    log.error("Parameter value - " + param);
                }
                log.error("Stacktrace", throwable);
            }
        };
    }

}
