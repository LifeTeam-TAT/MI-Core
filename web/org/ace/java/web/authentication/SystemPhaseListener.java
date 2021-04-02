package org.ace.java.web.authentication;

/***************************************************************************************
 * @author <<TNS>>
 * @Date 2018-01-23
 * @Version 1.0
 * @Purpose <<to redirect login page when view expired.>>*   
 ***************************************************************************************/
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.Constants;

public class SystemPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		if (httpRequest.getRequestedSessionId() != null && !httpRequest.isRequestedSessionIdValid()) {
			String facesRequestHeader = httpRequest.getHeader("Faces-Request");
			boolean isAjaxRequest = facesRequestHeader != null && facesRequestHeader.equals("partial/ajax");

			if (isAjaxRequest) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.VIEW_EXPIRED, true);
				ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
				if (ApplicationSetting.getHomePageDir().contains("ps_login.xhtml")) {
					handler.performNavigation("pslogin");
				} else {
					handler.performNavigation("login");
				}
			}
		}
	}
}
