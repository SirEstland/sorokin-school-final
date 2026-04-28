package dev.sorokin.school.service;

import dev.sorokin.school.config.AccountProperties;
import dev.sorokin.school.entity.Account;
import dev.sorokin.school.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {

    private final Map<Integer, User> users = new HashMap<>();
    private final AccountService accountService;
    private final AccountProperties accountProperties;
    private int idCounter = 0;

    public UserService(AccountService accountService, AccountProperties accountProperties) {
        this.accountService = accountService;
        this.accountProperties = accountProperties;
    }

    public User createUser(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login must not be empty");
        }
        boolean exists = users.values().stream()
                .anyMatch(u -> u.getLogin().equals(login));
        if (exists) {
            throw new IllegalArgumentException("User with login '" + login + "' already exists");
        }

        idCounter++;
        User user = new User(idCounter, login, new ArrayList<>());
        Account initialAccount = accountService.createAccount(user.getId());
        user.getAccountList().add(initialAccount);
        users.put(user.getId(), user);
        return user;
    }

    public User findUserById(int id) {
        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException("No such user: id=" + id);
        }
        return user;
    }

    public Optional<User> findUserByIdOptional(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public void addAccountToUser(int userId, Account account) {
        User user = findUserById(userId);
        user.getAccountList().add(account);
    }

    public void removeAccountFromUser(int userId, Account account) {
        User user = findUserById(userId);
        user.getAccountList().remove(account);
    }
}

