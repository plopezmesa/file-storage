package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import es.arcasi.oss.filestorage.model.FileStorageItem;

public class MemoryStorageServiceImplTest extends AbstractFileStorageServiceImplTest {

  private static final long TTL = 0;
  private static final long SLEEP = 1000*TTL + 500;

  public MemoryStorageServiceImplTest() throws IOException {
    super(new MemoryFileStorageServiceImpl());
  }
  
  @Test
  public final void file_is_not_available_after_ttl() throws IOException, InterruptedException {
    super.fileStorageService = new MemoryFileStorageServiceImpl(TTL);
    
    String fileId = fileStorageService.save(new FileStorageItem(fileData));
    Thread.sleep(SLEEP);
    FileStorageItem fileStorageItem = fileStorageService.get(fileId);
    
    Assert.assertNull(fileStorageItem);
    super.fileStorageService = new MemoryFileStorageServiceImpl();
  }

}
