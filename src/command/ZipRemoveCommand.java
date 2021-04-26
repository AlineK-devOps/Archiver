package command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import exception.WrongZipFileException;

import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand{ //класс удаления файла из архива
    @Override
    public void execute() throws Exception {
        try{
            ConsoleHelper.writeMessage("Удаление файла из архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите путь к файлу, который желаете удалить:");
            String removeFile = ConsoleHelper.readString();
            zipFileManager.removeFile(Paths.get(removeFile));
            ConsoleHelper.writeMessage("Удаление файла из архива завершено.");
        }
        catch (WrongZipFileException ex){
            ConsoleHelper.writeMessage("Вы неверно указали имя файла.");
        }
    }
}
