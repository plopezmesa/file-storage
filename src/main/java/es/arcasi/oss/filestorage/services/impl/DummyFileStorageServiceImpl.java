package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;

import es.arcasi.oss.filestorage.model.FileStorageItem;

/**
 * Dummy file storage implementation. Only for testing purposes
 * @author plopezmesa
 *
 */
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
