package dev.sorokin.school.command;

import dev.sorokin.school.console.ConsoleOperationType;
import dev.sorokin.school.console.OperationCommand;
import dev.sorokin.school.entity.Account;
import dev.sorokin.school.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountCreateCommand implements OperationCommand {

    private final AccountService accountService;

    @Override
    public void execute(Scanner scanner) {
        log.info("Enter user id:");
        int userId = Integer.parseInt(scanner.nextLine().trim());
        Account account = accountService.createAccountForUser(userId);
        log.info("Created: %s".formatted(account));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
