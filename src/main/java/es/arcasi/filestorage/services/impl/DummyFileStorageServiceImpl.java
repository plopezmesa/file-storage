package es.arcasi.filestorage.services.impl;

import java.io.IOException;

import es.arcasi.filestorage.model.FileStorageItem;

public class DummyFileStorageServiceImpl extends AbstractFileStorageService {

  @Override
  public String save(FileStorageItem fileStorageItem) throws IOException {
    String fileId = fileIdGenerator.generateFileId();
    return fileId;
  }

  @Override
  public FileStorageItem get(String fileId) throws IOException {
    return new FileStorageItem(fileId.getBytes());
  }
}
