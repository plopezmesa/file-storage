package es.arcasi.oss.filestorage.services;

/**
 * File ID Generator
 * @author plopezmesa
 *
 */
public interface FileStorageFileIdGenerator {
  /**
   * Generates a unique file ID
   * @return
   */
  public String generateFileId();
}
