package archive;

import exception.PathIsNotFoundException;
import exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0)
            out.write(buffer, 0, len);
    }

    public List<FileProperties> getFilesList() throws Exception{ //возвращает свойств файлов в архиве
        if (!Files.isRegularFile(zipFile))
            throw new WrongZipFileException();

        List<FileProperties> properties = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))){ //входящий поток архива
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){ //считываем данные из архива
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                copyData(zis, out);
                properties.add(new FileProperties(entry.getName(), entry.getSize(), entry.getCompressedSize(), entry.getMethod())); //добавляем список файл из архива
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return properties;
    }
}
