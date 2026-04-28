package dev.sorokin.school.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AccountProperties {
    private final int defaultAmount;
    private final double transferCommission;

    public AccountProperties(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission
    ) {
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }
}

