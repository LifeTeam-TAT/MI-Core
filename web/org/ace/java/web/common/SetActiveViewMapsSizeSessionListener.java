package org.ace.java.web.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SetActiveViewMapsSizeSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// event.getSession().setAttribute(ViewScopeManager.ACTIVE_VIEW_MAPS_SIZE,
		// 2);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
	}

}
