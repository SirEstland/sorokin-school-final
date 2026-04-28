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
public class AccountCloseCommand implements OperationCommand {

    private final AccountService accountService;

    @Override
    public void execute(Scanner scanner) {
        log.info("Enter account id to close:");
        int accountId = Integer.parseInt(scanner.nextLine().trim());

        Account account = accountService.findAccountById(accountId);
        int remaining = account.getMoneyAmount();
        int targetId = accountService.closeAccount(accountId);

        log.info("Closed #%d, balance %d → #%d".formatted(accountId, remaining, targetId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
