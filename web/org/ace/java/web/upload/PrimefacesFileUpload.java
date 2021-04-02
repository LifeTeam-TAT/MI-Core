package org.ace.java.web.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "PrimefacesFileUpload")
public class PrimefacesFileUpload {
	private UploadedFile uploadedFile;
	private List<UploadedFile> uploadedFileList = new ArrayList<UploadedFile>();
	private boolean initFlag = true;
	private StreamedContent streamedContent;

	@PostConstruct
	public void init() {
		initFlag = false;
	}

	public boolean getInitFlag() {
		return initFlag;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public StreamedContent getUploadImage() {
		return streamedContent;
	}

	public List<StreamedContent> getUploadedFileList() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			List<StreamedContent> contentList = new ArrayList<StreamedContent>();
			for (UploadedFile uploadedFile : uploadedFileList) {
				StreamedContent content = new DefaultStreamedContent();
				contentList.add(content);
			}
			return contentList;
		} else {
			List<StreamedContent> contentList = new ArrayList<StreamedContent>();
			for (UploadedFile uploadedFile : uploadedFileList) {
				StreamedContent content = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
				contentList.add(content);
			}
			return contentList;
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		uploadedFileList.add(uploadedFile);
		System.out.println("Uploadated Files : " + uploadedFile.getFileName());
		FacesMessage msg = new FacesMessage("Succesful is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void multiUpload() {
		try {
			for (UploadedFile uploadedFile : uploadedFileList) {
				String fileName = uploadedFile.getFileName();
				byte[] content = uploadedFile.getContents();
				System.out.println("Content Length : " + content.length);
				String contentType = uploadedFile.getContentType();
				Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
				String systemPath = ((ServletContext) context).getRealPath("/");
				File file = new File(systemPath + "/upload/" + fileName);
				FileOutputStream outputStream = new FileOutputStream(file);
				IOUtils.write(content, outputStream);
				outputStream.flush();
				outputStream.close();
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Successful! " + uploadedFile.getFileName() + " is uploaded."));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void upload(ActionEvent event) {
		String fileName = uploadedFile.getFileName();
		byte[] content = uploadedFile.getContents();
		System.out.println("Content Length : " + content.length);
		String contentType = uploadedFile.getContentType();
		Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		File file = new File(systemPath + "/upload/" + fileName);
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			IOUtils.write(content, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), "image/png", uploadedFile.getFileName());
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Successful! " + uploadedFile.getFileName() + " is uploaded."));
	}
}
