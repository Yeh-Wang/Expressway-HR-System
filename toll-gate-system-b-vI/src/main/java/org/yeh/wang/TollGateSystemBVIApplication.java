package org.yeh.wang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.yeh.wang.mapper")
public class TollGateSystemBVIApplication {

    public static void main(String[] args) {
        SpringApplication.run(TollGateSystemBVIApplication.class, args);
    }

    static {
        System.setProperty("druid.mysql.usePingMethod","false");
    }
}
