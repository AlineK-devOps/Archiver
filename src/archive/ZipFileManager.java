package archive;

import exception.PathIsNotFoundException;
import exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager { //менеджер архива, совершает операции над файлом с расширением .zip
    private Path zipFile; //полный путь к архиву

    public ZipFileManager(Path zipFile){
        this.zipFile = zipFile;
    }

    public void extractAll(Path outputFolder) throws Exception{ //распаковка архива в папку outputFolder
        if (!Files.isRegularFile(zipFile)) //если архива не существует
            throw new WrongZipFileException();

        if (Files.notExists(outputFolder))
            Files.createDirectories(outputFolder); //если дериктории не существует, то создаем её

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))){ //входящий поток архива
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null){ //считываем данные из архива
                if (entry.isDirectory())
                    new File(String.valueOf(outputFolder.resolve(entry.getName()))).mkdirs();
                else{
                    OutputStream out = Files.newOutputStream(outputFolder.resolve(entry.getName()));
                    copyData(zis, out);
                    out.close();
                }
            }
        }
    }

    public void addFiles(List<Path> absolutePathList) throws Exception{ //добавляет файлы в архив
        if (!Files.isRegularFile(zipFile)) //если архива не существует
            throw new WrongZipFileException();

        Path tempZipFile = Files.createTempFile(null, null); //создаём временный архив

        List<String> fileNames = new ArrayList<>(); //список переписанных файлов
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile));//входящий поток архива
             ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(tempZipFile))){

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){ //считываем данные из архива
                zout.putNextEntry(new ZipEntry(entry.getName()));
                copyData(zis, zout);
                fileNames.add(entry.getName());
                zout.closeEntry();
                zis.closeEntry();
            }

            for (Path file : absolutePathList){
                if (!Files.isRegularFile(file))
                    throw new PathIsNotFoundException();

                if (!fileNames.contains(String.valueOf(file.getFileName()))){ //если файла нет в архиве
                    ConsoleHelper.writeMessage("Файл " + file.getFileName() + " добавлен в архив.");
                    addNewZipEntry(zout, file.getParent(), file.getFileName());
                }
                else //если файл уже есть в архиве
                    ConsoleHelper.writeMessage("Файл уже есть в архиве.");
            }
            Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING); //замена старого архива временным
        }
    }

    public void addFile(Path absolutePath) throws Exception{
        addFiles(Collections.singletonList(absolutePath));
    }

    public void removeFiles(List<Path> pathList) throws Exception{ //удаляет файлы из архива
        if (!Files.isRegularFile(zipFile)) //если архива не существует
            throw new WrongZipFileException();

        Path tempZipFile = Files.createTempFile(null, null); //создаём временный архив

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile)); //входящий поток архива
        ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(tempZipFile))){

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){ //считываем данные из архива
                if (!pathList.contains(Paths.get(entry.getName()))){ //если файл не содержится в списке на удаление, переписать его в новый архив
                    zout.putNextEntry(new ZipEntry(entry.getName()));
                    copyData(zis, zout);
                    zout.closeEntry();
                    zis.closeEntry();
                }
                else //если файл содержится в списке на удаление, вывести сообщение о том, что файл удалён
                    ConsoleHelper.writeMessage("Файл " + entry.getName() + " удалён.");
            }
            Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING); //замена старого архива временным
        }
    }

    public void removeFile(Path path) throws Exception{ //удаляет файлы из архива
        removeFiles(Collections.singletonList(path));
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
                zis.closeEntry();
            }
        }
        return properties;
    }
}
