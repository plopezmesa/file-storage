package es.arcasi.oss.filestorage.services;

import java.io.IOException;
import java.util.Collection;

import es.arcasi.oss.filestorage.model.FileMetadata;
import es.arcasi.oss.filestorage.model.FileStorageItem;

/**
 * Service to save (store) and retrieve {@linkplain FileStorageItem} files
 * This interface can have many implementations
 * @author plopezmesa
 *
 */
public interface FileStorageService {

  /**
   * FileMetadata will be written with the same filename as the file but with this METADATA_FILE_EXT extension added
   */
  public static final String METADATA_FILE_EXT = ".fsm";

  /**
   * Lists all fileIds for {@link FileStorageItem}
   * @return The Collection of fileIds found
   * @throws IOException
   */
  public Collection<String> keys() throws IOException;

  /**
   * Stores (saves) the {@linkplain FileStorageItem} file
   * @param fileStorageItem
   * @return The file ID needed to retrieve the {@linkplain FileStorageItem} file
   * @throws IOException
   */
  public String save(FileStorageItem fileStorageItem) throws IOException;

  /**
   * Retrieves the {@linkplain FileStorageItem} file identified by the given fileId
   * @param fileId Unique file ID
   * @return The {@linkplain FileStorageItem} file identified by the given fileId
   * @throws IOException
   */
  public FileStorageItem get(String fileId) throws IOException;

  /**
   * Retrieves the {@linkplain FileMetadata} from {@link FileStorageItem} identified by the given fileId
   * @param fileId Unique file ID
   * @return The {@linkplain FileMetadata} from {@link FileStorageItem} identified by the given fileId
   * @throws IOException
   */
  public FileMetadata getMetadata(String fileId) throws IOException;

  /**
   * Deletes the {@linkplain FileStorageItem} file identified by the given fileId
   * @param fileId Unique file ID
   * @return true whenever a file is deleted
   */
  public boolean delete(String fileId);
}
