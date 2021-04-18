package archive;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileManager { //менеджер архива, совершает операции над файлом с расширением .zip
    private Path zipFile; //полный путь к архиву

    public ZipFileManager(Path zipFile){
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception{ //source - путь к тому, что будем архивировать
        try (ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(zipFile)); //поток архива
             InputStream inputStream = Files.newInputStream(source)){ //поток для чтения данный из source

            ZipEntry entry = new ZipEntry(String.valueOf(source.getFileName())); //файл, который будем архивировать
            zout.putNextEntry(entry); //добавили в поток архива созданный элемент архива

            byte[] buffer = new byte[inputStream.available()]; //переписываем данные из файла в поток архива
            inputStream.read(buffer);
            zout.write(buffer);

            zout.closeEntry(); //закрываем элемент архива
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
