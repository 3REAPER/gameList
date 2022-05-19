package com.samsung;

import com.samsung.domain.Author;
import com.samsung.service.AuthorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

// url h2 консоли: http://localhost:8080/h2-console
// url базы: jdbc:h2:mem:testdb

@SpringBootApplication
public class LibraryApp {
    public static void main(String[] args) {
        //Запуск SpringBoot-приложения и получение контекста
        ConfigurableApplicationContext context = SpringApplication.run(LibraryApp.class, args);
        
        /*try {
            //Консоль для визуализации бд в браузере
            Console.main(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }
}
