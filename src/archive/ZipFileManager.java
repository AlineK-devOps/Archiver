package archive;

import exception.PathIsNotFoundException;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileManager { //менеджер архива, совершает операции над файлом с расширением .zip
    private Path zipFile; //полный путь к архиву

    public ZipFileManager(Path zipFile){
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception{ //source - путь к тому, что будем архивировать
        Path zipDirectory = zipFile.getParent();
        if (Files.notExists(zipDirectory))
            Files.createDirectories(zipDirectory); //если дериктории не существует, то создаем её

        try (ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(zipFile))){ //поток архива
            if (Files.isRegularFile(source)) //если архивируем один файл
                addNewZipEntry(zout, source.getParent(), source.getFileName());
            else if (Files.isDirectory(source)){ //если архивируем папку
                FileManager manager = new FileManager(source);
                List<Path> paths = manager.getFileList();
                for (Path path : paths)
                    addNewZipEntry(zout, source, path);
            }
            else throw new PathIsNotFoundException();
        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception{ //архивируем один файл
        try (InputStream inputStream = Files.newInputStream(filePath.resolve(fileName))){ //поток для чтения данных
            ZipEntry entry = new ZipEntry(String.valueOf(fileName)); //файл, который будем архивировать
            zipOutputStream.putNextEntry(entry); //добавили в поток архива созданный элемент архива
            copyData(inputStream, zipOutputStream); //переписываем данные из файла в поток архива
            zipOutputStream.closeEntry(); //закрываем элемент архива
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception{ //копирует из in в out
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        out.write(buffer);
    }
}
