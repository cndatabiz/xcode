package com.dataz.xcode;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口类
 * @author Tommy
 */

@SpringBootApplication
@Log4j2
public class XcodeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        log.debug("application start...");
        SpringApplication.run(XcodeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.debug("xcode's generator is running.");
    }
}

