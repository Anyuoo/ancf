package com.anyu.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序的主入口
 *
 * @author Anyu
 * @since 2021/1/7 11:34
 */
@SpringBootApplication(scanBasePackages = "com.anyu")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
