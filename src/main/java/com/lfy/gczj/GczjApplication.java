package com.lfy.gczj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan({"com.lfy.gczj.dao"})
public class GczjApplication {

    public static void main(String[] args) {
        SpringApplication.run(GczjApplication.class, args);
    }

}
