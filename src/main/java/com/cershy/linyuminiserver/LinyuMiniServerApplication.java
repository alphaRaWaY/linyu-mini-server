package com.cershy.linyuminiserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cershy.linyuminiserver.mapper")
@SpringBootApplication
public class LinyuMiniServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinyuMiniServerApplication.class, args);
    }

}
