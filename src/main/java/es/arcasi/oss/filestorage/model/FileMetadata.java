package es.arcasi.oss.filestorage.model;

import java.io.Serializable;

/**
 * File Metadata information to be saved and retrieved along with file bytes
 * @author plopezmesa
 *
 */
public class FileMetadata implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private String extension;
  private String mimeType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
}
