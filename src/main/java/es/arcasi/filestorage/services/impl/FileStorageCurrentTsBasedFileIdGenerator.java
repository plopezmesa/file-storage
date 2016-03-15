package es.arcasi.filestorage.services.impl;

import org.apache.commons.lang.RandomStringUtils;

import es.arcasi.filestorage.services.FileStorageFileIdGenerator;

public class FileStorageCurrentTsBasedFileIdGenerator implements FileStorageFileIdGenerator {
  
  private static final int RANDOM_ALPHANUMERIC_SIZE = 8; 
  
  public String generateFileId ()  {
    return String.format("%s-%s", String.valueOf(System.currentTimeMillis()), RandomStringUtils.randomAlphanumeric(RANDOM_ALPHANUMERIC_SIZE));
  }
}
