package es.arcasi.oss.filestorage.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;

public class DiskFileStorageServiceImplTest extends AbstractFileStorageServiceImplTest {

  private static final String BASE_PATH = FileUtils.getTempDirectoryPath();
  private static final String UNEXISTING_PATH = "/TESTINGPATHFORFILESTORAGESERVICE/";

  public DiskFileStorageServiceImplTest() throws IOException {
    super(new DiskFileStorageServiceImpl(BASE_PATH));
  }

  @Test(expected = IllegalArgumentException.class)
  public final void base_path_cannot_be_null() throws IOException {
    new DiskFileStorageServiceImpl(null);
  }

  @Test(expected = FileNotFoundException.class)
  public final void base_path_must_exists() throws IOException {
    new DiskFileStorageServiceImpl(UNEXISTING_PATH);
  }

  @Test(expected = IllegalArgumentException.class)
  public final void base_path_must_be_a_directory() throws IOException {
    File filePath = File.createTempFile("filestorage", null);
    new DiskFileStorageServiceImpl(filePath.getPath());
    FileUtils.deleteQuietly(filePath);
  }

  @Test
  public final void save_file_write_file_to_disk() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);

    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    File fileRead = new File(BASE_PATH + File.separator + fileId);

    Assert.assertTrue(fileRead.exists());
    Assert.assertArrayEquals(fileData, FileUtils.readFileToByteArray(fileRead));
    FileUtils.deleteQuietly(new File(BASE_PATH + File.separator + fileId));
  }
}
