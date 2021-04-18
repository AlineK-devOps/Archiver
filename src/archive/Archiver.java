package archive;

import command.ExitCommand;
import java.nio.file.Paths;

public class Archiver { //главный класс архиватор
    public static void main(String[] args) {
        try{
            ConsoleHelper.writeMessage("Введите полный путь архива:");
            String zipFile = ConsoleHelper.readString();
            ZipFileManager manager = new ZipFileManager(Paths.get(zipFile));

            ConsoleHelper.writeMessage("Введите путь к файлу, который будем архивировать:");
            String file = ConsoleHelper.readString();
            manager.createZip(Paths.get(file));

            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
