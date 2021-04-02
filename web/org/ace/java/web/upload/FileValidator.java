package org.ace.java.web.upload;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "fileValidator")
public class FileValidator implements Validator {
	// Restricting to 2MB
	private long maxFileSize = 0;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		UploadedFile file = (UploadedFile) value;
		if (file.getFileName() == null || file.getFileName().isEmpty()) {
			file.delete();
			throw new ValidatorException(new FacesMessage("Invalid File."));
		}
		if (file.getContentType() == null || file.getContentType().isEmpty()) {
			file.delete();
			throw new ValidatorException(new FacesMessage("Invalid File."));
		}
		maxFileSize = getMaxFileSize(context);
		if (maxFileSize > 0 && file.getSize() > maxFileSize) {
			file.delete();
			throw new ValidatorException(new FacesMessage(String.format("File exceeds maximum permitted size of %d Kilo Bytes.", maxFileSize / 1024)));
		}
	}

	private long getMaxFileSize(FacesContext context) {
		long initMaxFileSize = 0;
		String value = context.getExternalContext().getInitParameter("maximumFileSize");
		try {
			if (value != null)
				initMaxFileSize = Long.parseLong(value);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Invalid value for maxFileSize (" + value + "). It must be an integer");
		}
		return initMaxFileSize;
	}
}
