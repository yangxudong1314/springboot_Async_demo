package com.spring.async.controller;

import com.spring.async.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @doc:
 * @outhor Xudong Yang
 * @create 2018-03-01 上午10:20
 * @project async
 * @E-mail yangxudong96@yahoo.com
 */
@org.springframework.stereotype.Controller
@Slf4j
public class Controller {
    @Autowired
    private TestService testService;

    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        testService.doTask1(10);
        log.info("我是主线程");
        return "void返回类型";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2() throws ExecutionException, InterruptedException {
        Future<String> future = testService.doTask2(10);
        log.info(future.get());
        return "Future返回类型";
    }
}
