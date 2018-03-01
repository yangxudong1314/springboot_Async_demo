package com.spring.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @doc:
 * @outhor Xudong Yang
 * @create 2018-03-01 上午10:13
 * @project async
 * @E-mail yangxudong96@yahoo.com
 */
@Slf4j
@Service
public class TestService {
    /**
     * 副线程抛出错误，AsyncUncaughtExceptionHandler自动处理
     *
     * @param i
     */
    @Async
    public void doTask1(int i) {
        log.info("doTask1  ==>" + i + " started.");
        throw new IllegalArgumentException(String.valueOf(i));
    }

    /**
     * 对于方法返回值是Futrue的异步方法,在调用future的get时捕获异常
     *
     * @param i
     */
    @Async
    public Future<String> doTask2(int i) {
        Future<String> future;
        // Future对象中填入错误参数
        try {
            Thread.sleep(1000 * 1);
            log.info("doTask2  ==>" + i + " started.");
            future = new AsyncResult<String>("success:" + i);
            throw new IllegalArgumentException(String.valueOf(i));
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        } catch (IllegalArgumentException e) {
            future = new AsyncResult<String>("error-IllegalArgumentException");
        }
        return future;
    }
}
