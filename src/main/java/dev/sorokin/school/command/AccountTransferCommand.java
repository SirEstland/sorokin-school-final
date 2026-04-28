package dev.sorokin.school.command;

import dev.sorokin.school.config.AccountProperties;
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
public class AccountTransferCommand implements OperationCommand {

    private final AccountService accountService;
    private final AccountProperties accountProperties;

    @Override
    public void execute(Scanner scanner) {
        log.info("Enter from account id:");
        int fromId = Integer.parseInt(scanner.nextLine().trim());
        log.info("Enter to account id:");
        int toId = Integer.parseInt(scanner.nextLine().trim());
        log.info("Enter amount:");
        int amount = Integer.parseInt(scanner.nextLine().trim());

        Account from = accountService.findAccountById(fromId);
        Account to = accountService.findAccountById(toId);
        boolean sameUser = from.getUserId() == to.getUserId();

        int received = accountService.transfer(fromId, toId, amount);

        if (sameUser) {
            log.info("Transfer #%d→#%d: %d (same user, no commission)".formatted(fromId, toId, amount));
        } else {
            int commission = amount - received;
            log.info("Transfer #%d→#%d: sent %d, commission %d, received %d".formatted(fromId, toId, amount, commission, received));
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
