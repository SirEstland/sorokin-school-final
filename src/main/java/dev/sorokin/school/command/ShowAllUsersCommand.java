package dev.sorokin.school.command;

import dev.sorokin.school.console.ConsoleOperationType;
import dev.sorokin.school.console.OperationCommand;
import dev.sorokin.school.entity.User;
import dev.sorokin.school.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowAllUsersCommand implements OperationCommand {

    private final UserService userService;

    @Override
    public void execute(Scanner scanner) {
        userService.getAllUsers().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .forEach(u -> log.info("%s".formatted(u)));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
