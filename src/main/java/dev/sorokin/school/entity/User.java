package dev.sorokin.school.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private List<Account> accountList;

    @Override
    public String toString() {
        return "User{id=%d, login='%s', accounts=%s}".formatted(id, login, accountList);
    }
}

