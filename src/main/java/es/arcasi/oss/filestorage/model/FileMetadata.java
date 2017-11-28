package es.arcasi.oss.filestorage.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * File Metadata information to be saved and retrieved along with file bytes
 * @author plopezmesa
 *
 */
public class FileMetadata implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final String FILENAME_KEY = "_FILENAME";
  private static final String FILESIZE_KEY = "_FILESIZE";
  private static final String CONTENT_TYPE_KEY = "_CONTENT_TYPE";

  protected Map<String, String> metadata = new HashMap<>();

  public String getFileName() {
    return metadata.get(FILENAME_KEY);
  }

  public void setFileName(String fileName) {
    metadata.put(FILENAME_KEY, fileName);
  }

  public String getContentType() {
    return metadata.get(CONTENT_TYPE_KEY);
  }

  public void setContentType(String contentType) {
    metadata.put(CONTENT_TYPE_KEY, contentType);
  }

  public Long getFileSize() {
    return Long.valueOf(metadata.get(FILESIZE_KEY));
  }

  public void setFileSize(Long fileSize) {
    metadata.put(FILESIZE_KEY, String.valueOf(fileSize));
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

}
