package dev.sorokin.school.command;

import dev.sorokin.school.console.ConsoleOperationType;
import dev.sorokin.school.console.OperationCommand;
import dev.sorokin.school.entity.User;
import dev.sorokin.school.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateCommand implements OperationCommand {

    private final UserService userService;

    @Override
    public void execute(Scanner scanner) {
        log.info("Enter login:");
        String login = scanner.nextLine().trim();
        User user = userService.createUser(login);
        log.info("Created: %s".formatted(user));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
