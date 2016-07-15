package es.arcasi.oss.filestorage.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;

public class DiskFileStorageServiceImplTest {
  
  private byte[] fileData;
  private FileMetadata fileMetadata;
  
  private static final String BASE_PATH = FileUtils.getTempDirectoryPath();
  private static final String UNEXISTING_PATH = "/TESTINGPATHFORFILESTORAGESERVICE/";
  
  private static final String FILEID_UNKNOWN = "FILEID_UNKNOWN";
  

  @Before
  public void setUp() throws Exception {
    fileData = "FILE DATA".getBytes();
    
    fileMetadata = new FileMetadata();
    fileMetadata.setName("NAME");
    fileMetadata.setExtension("EXT");
    fileMetadata.setMimeType("MIME");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public final void base_path_cannot_be_null() throws IOException {
    new DiskFileStorageServiceImpl(null);
  }
  
  @Test(expected=FileNotFoundException.class)
  public final void base_path_must_exists() throws IOException {
    new DiskFileStorageServiceImpl(UNEXISTING_PATH);
  }
  
  @Test(expected=IllegalArgumentException.class)
  public final void base_path_must_be_a_directory() throws IOException {
    File filePath = File.createTempFile("filestorage", null);
    new DiskFileStorageServiceImpl(filePath.getPath());
    FileUtils.deleteQuietly(filePath);
  }

  @Test
  public final void save_file_returns_not_null_fileId() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    
    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    
    Assert.assertNotNull(fileId);
    FileUtils.deleteQuietly(new File (BASE_PATH + File.separator + fileId));
  }
  
  @Test
  public final void save_file_write_file_to_disk() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    
    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    File fileRead = new File (BASE_PATH + File.separator + fileId);
    
    Assert.assertTrue(fileRead.exists());
    Assert.assertArrayEquals(fileData, FileUtils.readFileToByteArray(fileRead));
    FileUtils.deleteQuietly(new File (BASE_PATH + File.separator + fileId));
  }
  
  @Test(expected=FileNotFoundException.class)
  public final void get_file_with_unknown_fileId_throws_FileNotFound() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    
    fileStorageService.get(FILEID_UNKNOWN);
  }
  
  @Test
  public final void get_file_with_existing_fileId_get_same_file() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);
    
    Assert.assertNotNull(fileStorageItem);
    Assert.assertArrayEquals(fileData, fileStorageItem.getFile());
  }
  
  @Test
  public final void get_file_with_existing_fileId_returns_metadata_null_when_no_metadata() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);
    
    Assert.assertNull(fileStorageItem.getFileMetadata());
  }
  
  @Test
  public final void get_file_with_existing_fileId_returns_same_metadata_null_when_file_has_metadata() throws IOException {
    FileStorageService fileStorageService = new DiskFileStorageServiceImpl(BASE_PATH);
    String fileId = fileStorageService.save(new FileStorageItem(fileData, fileMetadata));
    
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);
    
    Assert.assertNotNull(fileStorageItem.getFileMetadata());
    Assert.assertEquals(fileMetadata.getName(), fileStorageItem.getFileMetadata().getName());
    Assert.assertEquals(fileMetadata.getExtension(), fileStorageItem.getFileMetadata().getExtension());
    Assert.assertEquals(fileMetadata.getMimeType(), fileStorageItem.getFileMetadata().getMimeType());
  }

}
