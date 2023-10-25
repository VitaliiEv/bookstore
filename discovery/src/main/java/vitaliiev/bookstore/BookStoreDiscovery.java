package vitaliiev.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BookStoreDiscovery {
    public static void main(String[] args) {
        SpringApplication.run(BookStoreDiscovery.class, args);
    }
}