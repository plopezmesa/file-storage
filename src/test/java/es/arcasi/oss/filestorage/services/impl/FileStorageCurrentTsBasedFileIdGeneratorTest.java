package es.arcasi.oss.filestorage.services.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.arcasi.oss.filestorage.services.FileStorageFileIdGenerator;

public class FileStorageCurrentTsBasedFileIdGeneratorTest {

  FileStorageFileIdGenerator fileStorageFileIdGenerator;

  @Before
  public void setUp() throws Exception {
    fileStorageFileIdGenerator = new FileStorageCurrentTsBasedFileIdGenerator();
  }

  @Test
  public final void generated_fileId_is_not_null() {
    String fileId = fileStorageFileIdGenerator.generateFileId();

    Assert.assertNotNull(fileId);
  }

  @Test
  public final void generated_fileId_is_22_chars_long() {
    String fileId = fileStorageFileIdGenerator.generateFileId();

    Assert.assertEquals(22, fileId.length());
  }

  @Test
  public final void generated_fileId_parts_are_13_and_8_chars_long() {
    String fileId = fileStorageFileIdGenerator.generateFileId();
    String[] fileIdParts = fileId.split("-");

    Assert.assertEquals(13, fileIdParts[0].length());
    Assert.assertEquals(8, fileIdParts[1].length());
  }

}
