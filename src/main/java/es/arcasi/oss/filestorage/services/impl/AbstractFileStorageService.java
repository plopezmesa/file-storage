package es.arcasi.oss.filestorage.services.impl;

import es.arcasi.oss.filestorage.services.FileStorageFileIdGenerator;
import es.arcasi.oss.filestorage.services.FileStorageService;

/**
 * Abstract base class for {@link FileStorageService} implementations
 * @author plopezmesa
 *
 */
public abstract class AbstractFileStorageService implements FileStorageService {
  protected FileStorageFileIdGenerator fileIdGenerator = new FileStorageCurrentTsBasedFileIdGenerator();
}
