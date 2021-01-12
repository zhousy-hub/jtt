package net.fxft.gateway.jtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JttApplication {

    public static void main(String[] args) {
        SpringApplication.run(JttApplication.class, args);
    }

}
