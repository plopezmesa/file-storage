package es.arcasi.filestorage.services;

import java.io.IOException;

import es.arcasi.filestorage.model.FileStorageItem;

public interface FileStorageService {
  public String save (FileStorageItem fileStorageItem) throws IOException;
  public FileStorageItem get (String fileId) throws IOException;
}
