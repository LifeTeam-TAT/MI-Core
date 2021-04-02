package org.ace.java.web.upload;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

@FacesComponent(value = "fileUpload")
public class HtmlFileRenderer extends UIInput implements ClientBehaviorHolder {
	@Override
	public String getFamily() {
		return "fileUpload";
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter rw = context.getResponseWriter();
		String clientId = getClientId(context);
		rw.startElement("input", null);
		rw.writeAttribute("id", clientId, "id");
		rw.writeAttribute("name", clientId, "name");
		rw.writeAttribute("type", "file", "file");
		UIComponent component = getCurrentComponent(context);
		// add CSS Class if set
		String cssClass = (String) component.getAttributes().get("styleClass");
		if (cssClass != null)
			rw.writeAttribute("class", cssClass, "styleClass");
		// add CSS Style if set
		String style = (String) component.getAttributes().get("style");
		if (style != null)
			rw.writeAttribute("style", style, "style");
		rw.endElement("input");
		rw.flush();
	}

	@Override
	public void decode(FacesContext context) {
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		String clientId = getClientId(context);
		UploadedFile file = (UploadedFile) request.getAttribute(clientId);
		ValueExpression valueExpr = getValueExpression("value");
		if (valueExpr != null & file != null) {
			UIComponent component = getCurrentComponent(context);
			if (file.getSize() > 0) {
				((EditableValueHolder) component).setSubmittedValue(file);
			} else {
				((EditableValueHolder) component).setSubmittedValue(null);
			}
			((EditableValueHolder) component).setValid(true);
		}
	}
}
