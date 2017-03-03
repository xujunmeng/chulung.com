package com.chulung;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;

/**
 * Created by chukai on 2017/2/28.
 */
@EnableWebMvc
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.chulung.website.mapper")
@Import(FdfsClientConfig.class)
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(new File("/ciki").getAbsolutePath());
        SpringApplication.run(Application.class);
    };
}
