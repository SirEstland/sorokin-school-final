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
public class AccountDepositCommand implements OperationCommand {

    private final AccountService accountService;

    @Override
    public void execute(Scanner scanner) {
        log.info("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine().trim());
        log.info("Enter amount:");
        int amount = Integer.parseInt(scanner.nextLine().trim());
        accountService.deposit(accountId, amount);
        Account account = accountService.findAccountById(accountId);
        log.info("Deposited %d to #%d, balance: %d".formatted(amount, accountId, account.getMoneyAmount()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
