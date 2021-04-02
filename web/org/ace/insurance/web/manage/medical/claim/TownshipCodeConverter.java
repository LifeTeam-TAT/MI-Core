package org.ace.insurance.web.manage.medical.claim;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

@FacesConverter("themeConverter")
public class TownshipCodeConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.isEmpty())
			return null;

		SelectItem selectItem = new SelectItem();
		selectItem.setLabel(value);
		return selectItem;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		return ((SelectItem) object).getLabel();
	}
}
