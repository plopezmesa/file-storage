package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;

import org.junit.Test;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;

import es.arcasi.oss.filestorage.model.FileStorageItem;

public class GoogleFileStorageServiceImplTest extends AbstractFileStorageServiceImplTest {

  public GoogleFileStorageServiceImplTest() throws IOException {
    super(new GoogleFileStorageServiceImpl(getMockStorage(), "local-test"));
  }

  private static Storage getMockStorage() {
    Storage storage = LocalStorageHelper.getOptions().getService();
    return storage;
  }

  @Test(expected = IllegalArgumentException.class)
  public final void bucketname_cannot_be_null() throws IOException {
    new GoogleFileStorageServiceImpl(null);
  }

  @Test(expected = StorageException.class)
  public final void storage_exception_if_default_instance_used_and_no_credential_env_set() throws IOException {
    GoogleFileStorageServiceImpl googleFileStorageServiceImpl = new GoogleFileStorageServiceImpl("remote-test");

    googleFileStorageServiceImpl.save(new FileStorageItem(fileData));
  }
}
