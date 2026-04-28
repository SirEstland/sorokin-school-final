package dev.sorokin.school.command;

import dev.sorokin.school.console.ConsoleOperationType;
import dev.sorokin.school.console.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ExitCommand implements OperationCommand {
 
    private final AtomicBoolean running = new AtomicBoolean(true);
 
    public boolean isRunning() {
        return running.get();
    }
 
    @Override
    public void execute(Scanner scanner) {
        running.set(false);
    }
 
    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.EXIT;
    }
}
