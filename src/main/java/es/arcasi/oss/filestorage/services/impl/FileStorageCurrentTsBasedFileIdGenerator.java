package es.arcasi.oss.filestorage.services.impl;

import org.apache.commons.lang.RandomStringUtils;

import es.arcasi.oss.filestorage.services.FileStorageFileIdGenerator;

/**
 * {@linkplain FileStorageFileIdGenerator} implementation
 * FileID is generated using the current TS (System.currentTimeMillis()) concatenated with a random 8 chars alphanumeric string
 * @author plopezmesa
 *
 */
public class FileStorageCurrentTsBasedFileIdGenerator implements FileStorageFileIdGenerator {
  
  private static final int RANDOM_ALPHANUMERIC_SIZE = 8; 
  
  public String generateFileId ()  {
    return String.format("%s-%s", String.valueOf(System.currentTimeMillis()), RandomStringUtils.randomAlphanumeric(RANDOM_ALPHANUMERIC_SIZE));
  }
}
