package es.arcasi.filestorage.services;

import java.io.IOException;

import es.arcasi.filestorage.model.FileStorageItem;

/**
 * Service to save (store) and retrieve {@linkplain FileStorageItem} files
 * This interface can have many implementations
 * @author plopezmesa
 *
 */
public interface FileStorageService {
  /**
   * Stores (saves) the {@linkplain FileStorageItem} file
   * @param fileStorageItem
   * @return The file ID needed to retrieve the {@linkplain FileStorageItem} file
   * @throws IOException
   */
  public String save (FileStorageItem fileStorageItem) throws IOException;
  
  /**
   * Retrieves the {@linkplain FileStorageItem} file identified by the given fileId
   * @param fileId Unique file ID
   * @return The {@linkplain FileStorageItem} file identified by the given fileId
   * @throws IOException
   */
  public FileStorageItem get (String fileId) throws IOException;
}
