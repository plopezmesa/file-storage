package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;
import es.arcasi.oss.filestorage.services.FileStorageService;

@Ignore
public class AbstractFileStorageServiceImplTest {
  protected static final String FILEID_UNKNOWN = "FILEID_UNKNOWN";

  protected byte[] fileData;
  protected FileMetadata fileMetadata;

  protected FileStorageService fileStorageService;

  @Before
  public void setUp() throws Exception {
    fileData = "FILE DATA".getBytes();

    fileMetadata = new FileMetadata();
    fileMetadata.setName("NAME");
    fileMetadata.setExtension("EXT");
    fileMetadata.setMimeType("MIME");
  }

  public AbstractFileStorageServiceImplTest(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @Test
  public final void save_file_returns_not_null_fileId() throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData));

    Assert.assertNotNull(fileId);
  }

  @Test
  public final void get_file_with_unknown_fileId_must_return_null() throws IOException {
    Assert.assertNull(fileStorageService.get(FILEID_UNKNOWN));
  }

  @Test
  public final void get_file_with_existing_fileId_get_same_file() throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData));

    FileStorageItem fileStorageItem = fileStorageService.get(fileId);

    Assert.assertNotNull(fileStorageItem);
    Assert.assertArrayEquals(fileData, fileStorageItem.getFile());
  }

  @Test
  public final void get_file_with_existing_fileId_returns_null_metadata_when_no_metadata() throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData));

    FileStorageItem fileStorageItem = fileStorageService.get(fileId);

    Assert.assertNull(fileStorageItem.getFileMetadata());
  }

  @Test
  public final void get_file_with_existing_fileId_returns_same_metadata_when_file_has_metadata() throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData, fileMetadata));

    FileStorageItem fileStorageItem = fileStorageService.get(fileId);

    Assert.assertNotNull(fileStorageItem.getFileMetadata());
    Assert.assertEquals(fileMetadata.getName(), fileStorageItem.getFileMetadata().getName());
    Assert.assertEquals(fileMetadata.getExtension(), fileStorageItem.getFileMetadata().getExtension());
    Assert.assertEquals(fileMetadata.getMimeType(), fileStorageItem.getFileMetadata().getMimeType());
  }
  
  @Test
  public final void delete_unexisting_file_returns_false () throws IOException {
    Assert.assertFalse(fileStorageService.delete(FILEID_UNKNOWN));
  }
  
  @Test
  public final void delete_existing_file_returns_true () throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData));

    Assert.assertTrue(fileStorageService.delete(fileId));
  }
  
  @Test
  public final void delete_existing_file_must_return_null_when_getting_it_again () throws IOException {
    String fileId = fileStorageService.save(new FileStorageItem(fileData));

    fileStorageService.delete(fileId);
    
    Assert.assertNull(fileStorageService.get(fileId));
  }

}
