package com.spring.async.asyncpool;

import com.spring.async.properties.ThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @doc: 自定义的线程池以及修改默认的线程池
 * @outhor Xudong Yang
 * @create 2018-03-01 上午9:56
 * @project async
 * @E-mail yangxudong96@yahoo.com
 */
@Configuration
@Slf4j
public class MyAsyncConfigurer implements AsyncConfigurer {

    @Autowired
    private ThreadPoolProperties poolProperties;

    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(poolProperties.getCorePoolSize());
        threadPool.setMaxPoolSize(poolProperties.getMaxPoolSize());
        threadPool.setQueueCapacity(poolProperties.getQueueCapacity());
        threadPool.setKeepAliveSeconds(poolProperties.getKeepAliveSeconds());
        threadPool.setThreadNamePrefix("taskExecutor-");
        /**
         *  rejection-policy：当pool已经达到max size的时候，如何处理新任务
         *  CallerRunsPolicy ：不在新线程中执行任务，而是由调用者所在的线程来执行
         */
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(poolProperties.getCorePoolSize());
        threadPool.setMaxPoolSize(poolProperties.getMaxPoolSize());
        threadPool.setQueueCapacity(poolProperties.getQueueCapacity());
        threadPool.setKeepAliveSeconds(poolProperties.getKeepAliveSeconds());
        threadPool.setThreadNamePrefix("taskExecutor-");
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     *
     * @author hry
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.info("错误信息 => " + throwable.getMessage());
            log.info("方法名 => " + method.getName());
            for (Object param : obj) {
                log.info("参数值 => " + param);
            }
        }

    }

}
