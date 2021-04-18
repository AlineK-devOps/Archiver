package archive;

import exception.WrongZipFileException;

import java.io.IOException;

public class Archiver { //главный класс архиватор
    public static void main(String[] args) {
        Operation operation = null;
        do{
            try{
                operation = askOperation();
                CommandExecutor.execute(operation);
            }
            catch (WrongZipFileException ex){
                ConsoleHelper.writeMessage("Вы не выбрали файл архива или выбрали неверный файл.");
            }
            catch (Exception ex){
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введенные данные.");
            }
        }
        while (operation != Operation.EXIT);
    }

    public static Operation askOperation() throws IOException{ //узнать, какую команду хочет выполнить пользователь
        ConsoleHelper.writeMessage("Выберите операцию:");
        ConsoleHelper.writeMessage(Operation.CREATE.ordinal() + " - упаковать файлы в архив");
        ConsoleHelper.writeMessage(Operation.ADD.ordinal() + " - добавить файл в архив");
        ConsoleHelper.writeMessage(Operation.REMOVE.ordinal() + " - удалить файл из архива");
        ConsoleHelper.writeMessage(Operation.EXTRACT.ordinal() + " - распаковать архив");
        ConsoleHelper.writeMessage(Operation.CONTENT.ordinal() + " - просмотреть сожержимое архива");
        ConsoleHelper.writeMessage(Operation.EXIT.ordinal() + " - выход");

        int ordinal = ConsoleHelper.readInt();
        return Operation.values()[ordinal];
    }
}
