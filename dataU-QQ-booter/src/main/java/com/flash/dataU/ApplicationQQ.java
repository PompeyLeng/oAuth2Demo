package com.flash.dataU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

/**
 * dataU start Class.
 *
 * @author Flash (18811311416@sina.cn)
 * @since 2017-07-06 13:55
 */

//@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
@Controller
public class ApplicationQQ {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationQQ.class,args);
    }

}
