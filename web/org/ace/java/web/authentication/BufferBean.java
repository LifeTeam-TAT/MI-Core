package org.ace.java.web.authentication;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "BufferBean")
@RequestScoped
public class BufferBean extends BaseBean {
	
	private final String DASHBOARD = "/view/dashboard.xhtml";

	public void goDashboard() {
		try {
			String contextPath = getContextPath();
			getFacesContext().getExternalContext().redirect(contextPath + DASHBOARD);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getContextPath() {
		return getServletContext().getContextPath();
	}
}
