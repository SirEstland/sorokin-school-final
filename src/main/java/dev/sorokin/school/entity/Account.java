package dev.sorokin.school.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private int id;
    private int userId;
    private int moneyAmount;

    @Override
    public String toString() {
        return "Account{id=%d, userId=%d, amount=%d}".formatted(id, userId, moneyAmount);
    }
}
