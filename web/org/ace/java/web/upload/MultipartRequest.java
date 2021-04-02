package org.ace.java.web.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class MultipartRequest {
	private ServletRequest request;
	// private final String contentDisposition="Content-Disposition:";
	// private final String contentType="Content-Type";
	private final String CR = "\r";
	private final String LF = "\n";
	private final String DASH = "-";
	private Map<String, String[]> map = new HashMap<String, String[]>();
	private String dir = "";

	/**
	 * 
	 * @param request
	 * @throws IOException
	 * @throws UploadedFileException
	 */
	public MultipartRequest(ServletRequest request) throws IOException, UploadedFileException {
		this.request = request;
		this.dir = getFilePath(dir);
		parseRequest();
	}

	/**
	 * 
	 * @param request
	 * @param dir
	 *            A directory to the root path of the web application
	 * @throws IOException
	 * @throws UploadedFileException
	 */
	public MultipartRequest(ServletRequest request, String dir) throws IOException, UploadedFileException {
		this.request = request;
		this.dir = getFilePath(dir);
		parseRequest();
	}

	private void parseRequest() throws IOException, UploadedFileException {
		StringBuffer sb = new StringBuffer();
		BufferedInputStream bs = new BufferedInputStream(request.getInputStream(), 8 * 1024);
		int c;
		while ((c = bs.read()) != -1) {
			sb.append((char) c);
		}
		String stream = sb.toString();
		// get the boundary of the inputstream
		String boundary = getBoundery(stream);
		// remove the lass boundary in the stream which is in the form
		// --------------------7876899227363389393763--
		stream = stream.substring(0, stream.indexOf(boundary + DASH + DASH));
		// split the content by using the boundary delimeter
		String[] data = stream.split(boundary + CR + LF + "Content-Disposition: form-data; name=");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		for (int i = 1; i < data.length; i++) {
			String name = data[i].substring(1, data[i].indexOf("\"", 2));
			String filename = null;
			String contentType = null;
			String value = null;
			// extract the value of any given input element
			value = data[i].substring(data[i].indexOf(CR + LF + CR + LF) + 4, data[i].length() - 2);
			if (data[i].substring(data[i].indexOf("\"") + name.length() + 1, data[i].indexOf(CR + LF + CR + LF)).indexOf("filename=\"") != -1)
				filename = data[i].substring(data[i].indexOf("filename=\"") + 10, data[i].indexOf("\"" + CR + LF));
			if (data[i].indexOf("Content-Type") != -1)
				contentType = data[i].substring(data[i].indexOf(CR + LF) + 16, data[i].indexOf(CR + LF + CR + LF));
			putStringParameters(name, value, parameterMap);
			// if the filename is not null, then a file exists hence store the
			// uploaded file
			// to the attribute of the ServletRequest object
			if (filename != null && value.trim().length() > 0) {
				putUploadedFileParameters(httpRequest, name, createFile(value, filename), contentType);
			}
		}
	}

	private void putStringParameters(String name, String value, Map<String, String[]> parameterMap) {
		String[] values = (String[]) parameterMap.get(name);
		if (values == null) {
			parameterMap.put(name, new String[] { value });
			map.put(name, new String[] { value });
		} else {
			int length = values.length;
			String[] newValues = new String[length + 1];
			System.arraycopy(values, 0, newValues, 0, length);
			newValues[length] = value;
			parameterMap.put(name, newValues);
			map.put(name, newValues);
		}
	}

	/***
	 * The method sets the value of the upload file in form of an attribute to
	 * the ServletRequest object The attribute supports a single value,
	 * therefore it is not possible to have multiple values in one attribute
	 * This is due to the fact that JSF does not support multiple elements
	 * having the same id
	 * 
	 * @param request
	 * @param name
	 * @param filename
	 * @param contentType
	 */
	private void putUploadedFileParameters(ServletRequest request, String name, String filename, String contentType) {
		request.setAttribute(name, new UploadedFile(dir, filename, contentType));
	}

	private String createFile(String content, String filename) throws UploadedFileException, IOException {
		// check if the supplied directory is a valid directory
		if (!(new File(dir)).isDirectory())
			throw new UploadedFileException("Invalid upload directory (" + dir + ")");
		// check of the directory is not write protected
		if (!(new File(dir)).canWrite())
			throw new UploadedFileException("The directory [" + (new File(dir)).getAbsolutePath() + "] is either write protected or does not exist");
		// Replace all back slashes with forward slashes (filename from IE comes
		// as an absolute path from the client).
		filename = filename.replace("\\", "/").substring(filename.lastIndexOf('/') + 1);
		// Get filename prefix (actual name) and suffix (extension).
		String prefix = filename;
		String suffix = "";
		if (filename.contains(".")) {
			prefix = filename.substring(0, filename.lastIndexOf('.'));
			suffix = filename.substring(filename.lastIndexOf('.'));
		}
		// Write the uploaded file as the temporary file.
		File file = File.createTempFile(prefix + "_", suffix, new File(dir));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 10 * 1024);
		for (int x = 0; x < content.length(); x++) {
			bos.write((int) content.charAt(x));
		}
		bos.close();
		return file.getName();
	}

	private String getBoundery(String stream) {
		return stream.substring(0, stream.indexOf("\r"));
	}

	public Object getParameter(String key) {
		return map.get(key);
	}

	public Map<String, String[]> getParameterMap() {
		return map;
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(map.keySet());
	}

	private String getFilePath(String dir) {
		return request.getServletContext().getRealPath(dir);
	}
}
