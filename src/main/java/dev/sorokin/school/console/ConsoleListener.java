package dev.sorokin.school.console;

import dev.sorokin.school.command.ExitCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ConsoleListener {

    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private final ExitCommand exitCommand;

    public ConsoleListener(List<OperationCommand> commands, ExitCommand exitCommand) {
        this.commandMap = new EnumMap<>(ConsoleOperationType.class);
        commands.forEach(c -> commandMap.put(c.getOperationType(), c));
        this.exitCommand = exitCommand;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        log.info("App started. Commands: %s".formatted(
                Arrays.stream(ConsoleOperationType.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "))));

        while (exitCommand.isRunning()) {
            log.info("Enter command:");
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            ConsoleOperationType type;
            try {
                type = ConsoleOperationType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException ex) {
                log.warn("Unknown command: %s".formatted(input));
                continue;
            }

            OperationCommand command = commandMap.get(type);
            if (command == null) {
                log.warn("Not implemented: %s".formatted(type));
                continue;
            }

            try {
                command.execute(scanner);
            } catch (NumberFormatException ex) {
                log.error("Invalid number: %s".formatted(ex.getMessage()));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        log.info("App stopped.");
    }
}
