package command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import exception.PathIsNotFoundException;

import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand{ //класс создания архива
    @Override
    public void execute() throws Exception {
        try{
            ConsoleHelper.writeMessage("Создание архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите полное имя файла для архивации:");
            String source = ConsoleHelper.readString();
            zipFileManager.createZip(Paths.get(source));
            ConsoleHelper.writeMessage("Архив создан");
        }
        catch (PathIsNotFoundException ex){
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
