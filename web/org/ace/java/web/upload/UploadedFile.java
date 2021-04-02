package org.ace.java.web.upload;

import java.io.File;
import java.io.Serializable;

public class UploadedFile implements Serializable {
	private static final long serialVersionUID = 3492495123134214556L;
	private String dir;
	private String filename;
	private String type;

	public UploadedFile(String dir, String filename, String type) {
		this.dir = dir;
		this.filename = filename;
		this.type = type;
	}

	public String getContentType() {
		return type;
	}

	public String getFileName() {
		return filename;
	}

	public String getFilesystemName() {
		// return filename;
		return new File(dir + File.separator + filename).getAbsolutePath();
	}

	public File getFile() {
		if (dir == null || filename == null) {
			return null;
		} else {
			return new File(dir + File.separator + filename);
		}
	}

	public long getSize() {
		return (new File(dir + File.separator + filename)).getAbsoluteFile().length();
	}

	public boolean delete() {
		return (new File(dir + File.separator + filename)).delete();
	}
}
