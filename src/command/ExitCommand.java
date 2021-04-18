package command;

import archive.ConsoleHelper;

public class ExitCommand implements Command{ //выход из архива
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
