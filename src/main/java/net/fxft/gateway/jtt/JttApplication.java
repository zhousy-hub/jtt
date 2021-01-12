package net.fxft.gateway.jtt;

import net.fxft.cloud.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class JttApplication {

    public static void main(String[] args) {
        SpringUtil.invokeAfterStartedRunner(SpringApplication.run(JttApplication.class, args));
    }

}
