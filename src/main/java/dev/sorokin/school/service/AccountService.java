package dev.sorokin.school.service;

import dev.sorokin.school.config.AccountProperties;
import dev.sorokin.school.entity.Account;
import dev.sorokin.school.entity.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountService {

    private final Map<Integer, Account> accounts = new HashMap<>();
    private final AccountProperties accountProperties;
    private final UserService userService;
    private int idCounter = 0;

    public AccountService(AccountProperties accountProperties, @Lazy UserService userService) {
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public Account createAccount(int userId) {
        idCounter++;
        Account account = new Account(idCounter, userId, accountProperties.getDefaultAmount());
        accounts.put(account.getId(), account);
        return account;
    }

    public Account createAccountForUser(int userId) {
        userService.findUserById(userId);
        Account account = createAccount(userId);
        userService.addAccountToUser(userId, account);
        return account;
    }

    public Account findAccountById(int id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new IllegalArgumentException("No such account: id=" + id);
        }
        return account;
    }

    public Optional<Account> findAccountByIdOptional(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void deposit(int accountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive, got " + amount);
        }
        Account account = findAccountById(accountId);
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void withdraw(int accountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive, got " + amount);
        }
        Account account = findAccountById(accountId);
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException(
                    "insufficient funds on account id=" + account.getId()
                            + ", moneyAmount=" + account.getMoneyAmount()
                            + ", attempted withdraw=" + amount
            );
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    /**
     * Performs the transfer.
     * @return the amount the recipient actually received.
     */
    public int transfer(int fromAccountId, int toAccountId, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive, got " + amount);
        }
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Account from = findAccountById(fromAccountId);
        Account to = findAccountById(toAccountId);

        if (amount > from.getMoneyAmount()) {
            throw new IllegalArgumentException("Not enough money to transfer from account: id=" + from.getId() + ", moneyAmount=" + from.getMoneyAmount() + ", attemptedTransfer=" + amount);
        }

        from.setMoneyAmount(from.getMoneyAmount() - amount);

        boolean sameUser = from.getUserId() == to.getUserId();
        int amountToReceive = sameUser
                ? amount
                : (int) Math.round(amount * (1 - accountProperties.getTransferCommission()));

        to.setMoneyAmount(to.getMoneyAmount() + amountToReceive);
        return amountToReceive;
    }

    public int closeAccount(int accountId) {
        Account account = findAccountById(accountId);
        User owner = userService.findUserById(account.getUserId());
        List<Account> userAccounts = owner.getAccountList();

        if (userAccounts.size() <= 1) {
            throw new IllegalArgumentException("Cannot close the only account of the user: accountId=" + accountId);
        }

        Account target = userAccounts.stream()
                .filter(a -> a.getId() != account.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No other account found"));

        int remaining = account.getMoneyAmount();
        target.setMoneyAmount(target.getMoneyAmount() + remaining);

        userService.removeAccountFromUser(owner.getId(), account);
        accounts.remove(account.getId());

        return target.getId();
    }
}

