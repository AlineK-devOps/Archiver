package command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import exception.WrongZipFileException;

import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand{ //класс распаковки архива
    @Override
    public void execute() throws Exception {
        try{
            ConsoleHelper.writeMessage("Распаковка архива.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Введите путь распаковки архива:");
            String outputFolder = ConsoleHelper.readString();
            zipFileManager.extractAll(Paths.get(outputFolder));
            ConsoleHelper.writeMessage("Архив сраспакован");
        }
        catch (WrongZipFileException ex){
            ConsoleHelper.writeMessage("Вы неверно указали имя дериктории.");
        }
    }
}
