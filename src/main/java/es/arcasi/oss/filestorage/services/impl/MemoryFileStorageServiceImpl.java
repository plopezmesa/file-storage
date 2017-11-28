package es.arcasi.oss.filestorage.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;
import net.jodah.expiringmap.ExpiringMap;

/**
 * Memory file storage implementation
 * A Map with a defined timeout (TTL) in seconds for its entries. Bases on {@link ExpiringMap} library
 * @author plopezmesa
 *
 */
public class MemoryFileStorageServiceImpl extends AbstractFileStorageService {
  /**
   * Default TTL is set to 600 sg
   */
  private final static long DEFAULT_TTL = 600L;
  /**
   * Map implemetation to store files in memory
   */
  private static Map<String, FileStorageItem> mapCache;

  /**
   * Creates a Memory File Storage with TimeToLive = DEFAULT_TTL
   */
  public MemoryFileStorageServiceImpl() {
    this(DEFAULT_TTL);
  }

  /**
   * Creates a Memory File Storage with TimeToLive specified
   * @param ttl TimeToLive in seconds
   */
  public MemoryFileStorageServiceImpl(long ttl) {
    mapCache = ExpiringMap.builder().expiration(ttl, TimeUnit.SECONDS).build();
  }

  @Override
  public String save(FileStorageItem fileStorageItem) {
    String fileId = fileIdGenerator.generateFileId();

    mapCache.put(fileId, fileStorageItem);

    return fileId;
  }

  @Override
  public FileStorageItem get(String fileId) {
    FileStorageItem fileStorageItem = mapCache.get(fileId);
    return fileStorageItem;
  }

  @Override
  public FileMetadata getMetadata(String fileId) throws IOException {
    FileStorageItem fileStorageItem = mapCache.get(fileId);

    FileMetadata fileMetadata = null;
    if (fileStorageItem != null) {
      fileMetadata = fileStorageItem.getFileMetadata();
    }
    return fileMetadata;
  }

  @Override
  public boolean delete(String fileId) {
    return mapCache.remove(fileId) != null;
  }

}
