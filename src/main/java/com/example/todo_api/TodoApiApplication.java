package com.example.todo_api;

//imports the spring boot application class
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; //imports the autoconfigure annotation

//marks this class as a Spring Boot application
@SpringBootApplication
public class TodoApiApplication {
    public static void main(String[] args) {
        //runs the application
        SpringApplication.run(TodoApiApplication.class, args);
    }
}
