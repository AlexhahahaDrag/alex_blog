package com.alex.blog.id;

import com.codingapi.leaf.framework.LeafAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@LeafAutoConfiguration(scanBasePackages = {"com.alex.blog.id.entity"})
public class IdGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorApplication.class, args);
    }
}
