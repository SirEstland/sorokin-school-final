package dev.sorokin.school.console;

import java.util.Scanner;

public interface OperationCommand {
    void execute(Scanner scanner);
    ConsoleOperationType getOperationType();
}

