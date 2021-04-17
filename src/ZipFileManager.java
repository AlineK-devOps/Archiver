import java.nio.file.Path;

public class ZipFileManager { //менеджер архива, совершает операции над файлом с расширением .zip
    private Path zipFile; //полный путь к архиву

    public ZipFileManager(Path zipFile){
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception{ //source - путь к тому, что будем архивировать

    }
}
