package archive;

import command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor { //реализация паттерна команда
    private static final Map<Operation, Command> ALL_KNOWN_COMMANDS_MAP = new HashMap<>(); //хранилище всех возможных команд

    static{
        ALL_KNOWN_COMMANDS_MAP.put(Operation.ADD, new ZipAddCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.REMOVE, new ZipRemoveCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CONTENT, new ZipContentCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.CREATE, new ZipCreateCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXIT, new ExitCommand());
        ALL_KNOWN_COMMANDS_MAP.put(Operation.EXTRACT, new ZipExtractCommand());
    }

    private CommandExecutor(){
    }

    public static void execute(Operation operation) throws Exception{ //выбирает нужную команду работы с архивом
        Command command = ALL_KNOWN_COMMANDS_MAP.get(operation);
        command.execute();
    }
}
