package org.ace.insurance.web.common;

import java.util.HashMap;
import java.util.Map;

public class WebUtils {
	private static Map<String, Object> dialogOptions;

	public static Map<String, Object> getDialogOption() {
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 500);
		dialogOptions.put("contentWidth", 750);
		return dialogOptions;
	}

	public static Map<String, Object> getTemplateDialogOption() {
		dialogOptions = new HashMap<String, Object>();
		dialogOptions.put("modal", true);
		dialogOptions.put("draggable", false);
		dialogOptions.put("resizable", false);
		dialogOptions.put("contentHeight", 580);
		dialogOptions.put("contentWidth", 1200);
		return dialogOptions;
	}
}
