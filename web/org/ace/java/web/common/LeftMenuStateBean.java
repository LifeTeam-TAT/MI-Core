package org.ace.java.web.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

@SessionScoped
@ManagedBean(name = "LeftMenuStateBean")
public class LeftMenuStateBean {

	private int openTabIndex;

	public void loadActiveIndex(TabChangeEvent event) {
		AccordionPanel panel = (AccordionPanel) event.getComponent();
		String activeIndex = panel.getActiveIndex();
		openTabIndex = Integer.parseInt(activeIndex);
	}

	public int getOpenTabIndex() {
		return openTabIndex;
	}

	public void setOpenTabIndex(int openTabIndex) {
		this.openTabIndex = openTabIndex;
	}

}
