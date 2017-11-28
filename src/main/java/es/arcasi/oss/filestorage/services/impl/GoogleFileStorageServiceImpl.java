package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;

/**
 * Google Cloud Storage file storage implementation
 * @author plopezmesa
 *
 */
public class GoogleFileStorageServiceImpl extends AbstractFileStorageService {

  private String bucketName;
  private Storage storage;

  public GoogleFileStorageServiceImpl(String bucketName) throws IOException {
    this(StorageOptions.getDefaultInstance().getService(), bucketName);
  }

  public GoogleFileStorageServiceImpl(Storage storage, String bucketName) throws IOException {
    this.storage = storage;
    this.bucketName = bucketName;

    if (bucketName == null) {
      throw new IllegalArgumentException("Bucket name cannot be null");
    }
  }

  @Override
  public String save(FileStorageItem fileStorageItem) throws IOException {
    String fileId = fileIdGenerator.generateFileId();

    BlobId blobId = BlobId.of(bucketName, fileId);
    BlobInfo blobInfo = BlobInfo
        .newBuilder(blobId)
        .setMetadata(getBlobInfoMetadata(fileStorageItem.getFileMetadata()))
        .build();

    storage.create(blobInfo, fileStorageItem.getFile());
    return fileId;
  }

  private Map<String, String> getBlobInfoMetadata(FileMetadata fileMetadata) {
    return fileMetadata != null ? fileMetadata.getMetadata() : new HashMap<String, String>();
  }

  @Override
  public FileStorageItem get(String fileId) {
    try {
      BlobId blobId = BlobId.of(bucketName, fileId);
      Blob blob = storage.get(blobId);

      FileStorageItem fileStorageItem = new FileStorageItem(blob.getContent(), getFileMetadata(blob.getMetadata()));
      return fileStorageItem;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public FileMetadata getMetadata(String fileId) throws IOException {
    try {
      BlobId blobId = BlobId.of(bucketName, fileId);
      Blob blob = storage.get(blobId);
      return getFileMetadata(blob.getMetadata());
    }
    catch (Exception e) {
      return null;
    }
  }

  private FileMetadata getFileMetadata(Map<String, String> blobMetadata) {
    FileMetadata fileMetadata = null;
    if (blobMetadata != null && blobMetadata.size() > 0) {
      fileMetadata = new FileMetadata();
      fileMetadata.setMetadata(blobMetadata);
    }
    return fileMetadata;
  }

  @Override
  public boolean delete(String fileId) {
    BlobId blobId = BlobId.of(bucketName, fileId);
    boolean deleted = storage.delete(blobId);
    return deleted;
  }

}
