package es.arcasi.filestorage.model;

/**
 * Item to be stored and retrieved. This item consists of the byte array and the file metadata
 * @author plopezmesa
 *
 */
public class FileStorageItem {
  private byte [] file;
  private FileMetadata fileMetadata;
  
  public FileStorageItem(byte[] file) {
    this.file = file;
  }
  
  public FileStorageItem(byte[] file, FileMetadata fileMetadata) {
    this.file = file;
    this.fileMetadata = fileMetadata;
  }

  public byte[] getFile() {
    return file;
  }
  public void setFile(byte[] file) {
    this.file = file;
  }
  public FileMetadata getFileMetadata() {
    return fileMetadata;
  }
  public void setFileMetadata(FileMetadata fileMetadata) {
    this.fileMetadata = fileMetadata;
  }
}
