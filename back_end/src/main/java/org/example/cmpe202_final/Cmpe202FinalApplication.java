package org.example.cmpe202_final;

import org.example.cmpe202_final.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(
  RsaKeyProperties.class
)
@SpringBootApplication
public class Cmpe202FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(Cmpe202FinalApplication.class, args);
    }
}
