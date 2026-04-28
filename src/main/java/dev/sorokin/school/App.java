package dev.sorokin.school;


import dev.sorokin.school.config.AppConfig;
import dev.sorokin.school.console.ConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            ConsoleListener listener = context.getBean(ConsoleListener.class);
            listener.start();
        }
    }
}


