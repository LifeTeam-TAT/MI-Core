package org.ace.insurance.web.manage.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.ComponentSystemEvent;

import org.ace.insurance.life.claim.LifeClaimNotiCriteria;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimNotificationService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageLifeClaimNotificationActionBean")
public class ManageLifeClaimNotificationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE_PARAM_SUFFIX = "_PARAM";
	private static final String MESSAGE_REQUEST_PARAM_SUFFIX = "_REQUEST_PARAM";

	@ManagedProperty(value = "#{LifeClaimNotificationService}")
	private ILifeClaimNotificationService notificationService;

	public void setNotificationService(ILifeClaimNotificationService notificationService) {
		this.notificationService = notificationService;
	}

	private List<LifeClaimNotification> notificationList;
	private LifeClaimNotiCriteria criteria;

	@PostConstruct
	public void init() {
		notificationList = new ArrayList<LifeClaimNotification>();
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new LifeClaimNotiCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
	}

	public void search() {
		notificationList = notificationService.findLifeClaimNotificationByCriteria(criteria);
	}

	public String addLifeClaimNotification() {
		removeParam("Actived");
		removeParam("SportMan");
		removeParam("Claim");
		return "lifeClaimNotification";
	}

	public String prepareLifeClaimProposal(LifeClaimNotification notification) {
		putParam("lifeClaimNotification", notification);
		return "lifeClaimProposal";
	}

	public String prepareUpdateLifeClaimProposal(LifeClaimNotification notification) {
		String result = null;
		putParam("notification", notification);
		result = "lifeClaimNotification";
		return result;
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicySearch = (LifePolicySearch) event.getObject();
		criteria.setPolicyNo(lifePolicySearch.getPolicyNo());
	}

	public void checkMessage(ComponentSystemEvent event) {
		ExternalContext extContext = getFacesContext().getExternalContext();
		String messageId = (String) extContext.getSessionMap().get(Constants.MESSAGE_ID);
		String proposalNo = (String) extContext.getSessionMap().get(Constants.PROPOSAL_NO);
		String requestNo = (String) extContext.getSessionMap().get(Constants.REQUEST_NO);
		if (messageId != null) {
			if (proposalNo != null) {
				addInfoMessage(null, messageId + MESSAGE_PARAM_SUFFIX, proposalNo);
				extContext.getSessionMap().remove(Constants.MESSAGE_ID);
				extContext.getSessionMap().remove(Constants.PROPOSAL_NO);
			} else {
				if (requestNo != null) {
					addInfoMessage(null, messageId + MESSAGE_REQUEST_PARAM_SUFFIX, requestNo);
					extContext.getSessionMap().remove(Constants.MESSAGE_ID);
					extContext.getSessionMap().remove(Constants.REQUEST_NO);
				} else {
					addInfoMessage(null, messageId);
					extContext.getSessionMap().remove(Constants.MESSAGE_ID);
				}
			}
		}
	}

	public List<LifeClaimNotification> getNotificationList() {
		return notificationList;
	}

	public LifeClaimNotiCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(LifeClaimNotiCriteria criteria) {
		this.criteria = criteria;
	}

}
