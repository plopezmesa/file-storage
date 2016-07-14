package es.arcasi.filestorage.services.impl;

import es.arcasi.filestorage.services.FileStorageFileIdGenerator;
import es.arcasi.filestorage.services.FileStorageService;

/**
 * Abstract base class for {@link FileStorageService} implementations
 * @author plopezmesa
 *
 */
public abstract class AbstractFileStorageService implements FileStorageService {
  protected FileStorageFileIdGenerator fileIdGenerator = new FileStorageCurrentTsBasedFileIdGenerator();
}
