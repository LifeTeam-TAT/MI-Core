package org.ace.java.web.upload;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "FileUploadController")
public class FileUploadController {
	private List<UploadedFile> uploadedFileList;
	private UploadedFile uploadedFile;
	// private boolean uploaded = false;
	private boolean initFlag = true;

	@PostConstruct
	public void init() {
		initFlag = false;
	}

	public boolean getInitFlag() {
		return initFlag;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	// public boolean getUploaded() {
	// return uploaded;
	// }
	public List<UploadedFile> getUploadedFileList() {
		return uploadedFileList;
	}

	public void setUploadedFileList(List<UploadedFile> uploadedFileList) {
		this.uploadedFileList = uploadedFileList;
	}

	// public void setUploaded(boolean uploaded) {
	// this.uploaded = uploaded;
	// }
	public String doUpload() {
		if (uploadedFileList == null) {
			uploadedFileList = new ArrayList<UploadedFile>();
		}
		uploadedFileList.add(uploadedFile);
		initFlag = false;
		return "uploaded";
	}
}
