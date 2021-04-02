package org.ace.insurance.web.manage.life.paidUp;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "YearEndPaidUpActionBean")
public class YearEndPaidUpActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {

	}

	@PreDestroy
	public void destory() {

	}
}
