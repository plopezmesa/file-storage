package es.arcasi.oss.filestorage.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;

/**
 * Disk (local path) file storage implementation
 * @author plopezmesa
 *
 */
public class DiskFileStorageServiceImpl extends AbstractFileStorageService {
  /**
   * Base local path for file storage
   */
  private String basePath;
  
  /**
   * Creates a new disk {@link FileStorageService} implementations 
   * @param basePath The base path is required to create a {@link DiskFileStorageServiceImpl} instance
   * @throws IOException
   */
  public DiskFileStorageServiceImpl(String basePath) throws IOException {
    setBasePath(basePath);
  }

  @Override
  public String save(FileStorageItem fileStorageItem) throws IOException {
    String fileId = fileIdGenerator.generateFileId();

    String filePath = basePath + fileId;
    File savedFile = new File(filePath);

    FileUtils.writeByteArrayToFile(savedFile, fileStorageItem.getFile());
    return fileId;
  }

  @Override
  public FileStorageItem get(String fileId) throws IOException {
    String filePath = basePath + fileId;
    File savedFile = new File(filePath);

    if (!savedFile.exists()) {
      throw new FileNotFoundException();
    }

    FileStorageItem fileStorageItem = new FileStorageItem(FileUtils.readFileToByteArray(savedFile));
    return fileStorageItem;
  }

  private void setBasePath(String basePath) throws IOException {
    if (basePath == null || !new File(basePath).exists()) {
      throw new FileNotFoundException();
    }

    this.basePath = basePath;
  }

}
