package org.ace.java.web.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.component.SystemException;

@ViewScoped
@ManagedBean(name = "TestBean")
public class TestBean extends BaseBean {
	public void show() {
		handelSysException(new SystemException("OLD_PASSWORD_DOES_NOT_MATCH", "Password doesn't match"));
	}
}
