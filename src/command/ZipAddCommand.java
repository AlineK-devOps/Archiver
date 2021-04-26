package command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import exception.PathIsNotFoundException;

import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{ //класс лобавления файла в архив
    @Override
    public void execute() throws Exception {
        try{
            ConsoleHelper.writeMessage("Добавление файла в архив.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите путь к файлу, который желаете добавить:");
            String addFile = ConsoleHelper.readString();
            zipFileManager.addFile(Paths.get(addFile));
            ConsoleHelper.writeMessage("Добавление файла в архив завершено.");
        }
        catch (PathIsNotFoundException ex){
            ConsoleHelper.writeMessage("Вы неверно указали имя файла.");
        }
    }
}
