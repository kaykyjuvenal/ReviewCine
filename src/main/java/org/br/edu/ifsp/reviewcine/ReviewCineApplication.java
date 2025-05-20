package org.br.edu.ifsp.reviewcine;

import jakarta.servlet.AsyncContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReviewCineApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ReviewCineApplication.class, args);
        Main main =  context.getBean(Main.class);
        main.menu();
    }

}
