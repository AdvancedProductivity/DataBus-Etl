package org.adp.databus.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zzq
 */

@SpringBootApplication
@MapperScan("org.adp.databus.app.mapper")
@ServletComponentScan(basePackages = {"org.adp.databus.app.config"})
public class AppStarter {

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }

}
