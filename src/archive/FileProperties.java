package archive;

public class FileProperties { //свойства файлов в архиве
    private String name; //имя файла
    private long size; //размер файла в байтах
    private long compressedSize; //размер после сжатия в байтах
    private int compressionMethod; //метод сжатия

    public FileProperties(String name, long size, long compressedSize, int compressionMethod){
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressionMethod = compressionMethod;
    }

    public long getCompressionRatio(){ //считает степень сжатия
        return 100 - (getCompressedSize() * 100 / getSize());
    }

    @Override
    public String toString() {
        return getSize() > 0? String.format("%s %f Kb (%f Kb) сжатие: %d%%", getName(), (float) getSize() / 1024, (float) getCompressedSize() / 1024, getCompressionRatio()) : getName();
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compressionMethod;
    }
}
