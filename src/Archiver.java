import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Archiver { //главный класс архиватор
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите полный путь архива:");
            String zipFile = reader.readLine();
            ZipFileManager manager = new ZipFileManager(Paths.get(zipFile));

            System.out.println("Введите путь к файлу, который будем архивировать:");
            String file = reader.readLine();
            manager.createZip(Paths.get(file));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
