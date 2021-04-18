package archive;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager { //класс для получения всех файлов в папке (архиив папки)
    private Path rootPath; //корневой путь директории
    private List<Path> fileList; //список относительных путей файлов внутри rootPath

    public FileManager(Path rootPath) throws IOException{
        this.rootPath = rootPath;
        fileList = new ArrayList<>();
        collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException{ //обходит дерево файлов, которые необходимо заархивировать
       if (Files.isRegularFile(path))
            fileList.add(rootPath.relativize(path));
       else if (Files.isDirectory(path)){
           DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
           for (Path value : directoryStream) collectFileList(value);
           directoryStream.close();
       }
    }
}
