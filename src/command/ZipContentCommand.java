package command;

import archive.ConsoleHelper;
import archive.FileProperties;
import archive.ZipFileManager;

public class ZipContentCommand extends ZipCommand{ //класс просмотра содержимого архива
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Просмотр соержимого архива.");
        ZipFileManager fileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Содержимое архива:");
        for (FileProperties properties : fileManager.getFilesList())
            System.out.println(properties);
        ConsoleHelper.writeMessage("Содержимое архива прочитано.");
    }
}
