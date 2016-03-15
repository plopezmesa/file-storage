package es.arcasi.filestorage.services.impl;

import es.arcasi.filestorage.services.FileStorageFileIdGenerator;
import es.arcasi.filestorage.services.FileStorageService;

public abstract class AbstractFileStorageService implements FileStorageService {
  protected FileStorageFileIdGenerator fileIdGenerator = new FileStorageCurrentTsBasedFileIdGenerator();
}
