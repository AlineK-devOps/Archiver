package command;

import archive.ConsoleHelper;
import archive.ZipFileManager;

import java.nio.file.Paths;

public abstract class ZipCommand implements Command{ // класс команд работющих непосредственно с архивом
    public ZipFileManager getZipFileManager() throws Exception{ //создает менеджер архива
        ConsoleHelper.writeMessage("Введите полный путь архива:");
        String rootPath = ConsoleHelper.readString();
        return new ZipFileManager(Paths.get(rootPath));
    }
}
