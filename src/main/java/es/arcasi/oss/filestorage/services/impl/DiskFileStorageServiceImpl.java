package es.arcasi.oss.filestorage.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.arcasi.oss.filestorage.model.FileMetadata;
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
   * Jackson JSON Serialization
   */
  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Creates a new disk {@link FileStorageService} implementations
   * @param basePath The base path is required to create a {@link DiskFileStorageServiceImpl} instance
   * @throws IOException
   */
  public DiskFileStorageServiceImpl(String basePath) throws IOException {
    setBasePath(basePath);
  }

  @Override
  public Collection<String> keys() throws IOException {
    File basePathDir = new File(basePath);

    File[] files = basePathDir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return !name.endsWith(METADATA_FILE_EXT);
      }
    });

    Collection<String> fileIds = new ArrayList<>(files.length);
    for (File file : files) {
      fileIds.add(file.getName());
    }

    return fileIds;
  }

  private void setBasePath(String basePath) throws IOException {
    if (basePath == null) {
      throw new IllegalArgumentException("BasePath cannot be null");
    }

    if (!basePath.endsWith(File.separator)) {
      basePath += File.separator;
    }

    File basePathFile = new File(basePath);

    if (!basePathFile.exists()) {
      throw new FileNotFoundException();
    }

    if (!basePathFile.isDirectory() || !basePathFile.canRead() || !basePathFile.canWrite()) {
      throw new IllegalArgumentException("BasePath must be a directory with read and write permissions");
    }

    this.basePath = basePath;
  }

  private File getFilePath(String fileId) {
    String filePath = basePath + fileId;
    File savedFile = new File(filePath);
    return savedFile;
  }

  private File getFileMetadataPath(String fileId) {
    String fileMetadataPath = basePath + fileId + METADATA_FILE_EXT;
    File savedFile = new File(fileMetadataPath);
    return savedFile;
  }

  @Override
  public String save(FileStorageItem fileStorageItem) throws IOException {
    String fileId = fileIdGenerator.generateFileId();

    saveFile(fileStorageItem.getFile(), fileId);
    saveMetadata(fileStorageItem.getFileMetadata(), fileId);
    return fileId;
  }

  private void saveFile(byte[] file, String fileId) throws IOException {
    File savedFile = getFilePath(fileId);
    FileUtils.writeByteArrayToFile(savedFile, file);
  }

  private void saveMetadata(FileMetadata fileMetadata, String fileId) throws IOException {
    File savedFile = getFileMetadataPath(fileId);
    objectMapper.writeValue(savedFile, fileMetadata);
  }

  @Override
  public FileStorageItem get(String fileId) {
    try {
      FileStorageItem fileStorageItem = new FileStorageItem(getFile(fileId), getFileMetadata(fileId));
      return fileStorageItem;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public FileMetadata getMetadata(String fileId) throws IOException {
    return getFileMetadata(fileId);
  }

  private byte[] getFile(String fileId) throws IOException {
    File savedFile = getFilePath(fileId);

    if (!savedFile.exists()) {
      throw new FileNotFoundException();
    }

    return FileUtils.readFileToByteArray(savedFile);
  }

  private FileMetadata getFileMetadata(String fileId) throws IOException {
    File savedFile = getFileMetadataPath(fileId);

    FileMetadata fileMetadata = null;
    if (savedFile.exists()) {
      fileMetadata = objectMapper.readValue(savedFile, FileMetadata.class);
    }

    return fileMetadata;
  }

  @Override
  public boolean delete(String fileId) {

    if (StringUtils.isBlank(fileId)) {
      throw new IllegalArgumentException("fileId cannot be blank");
    }

    boolean deleted = FileUtils.deleteQuietly(getFilePath(fileId));
    FileUtils.deleteQuietly(getFileMetadataPath(fileId));
    return deleted;
  }

}
