package org.ace.insurance.web.manage.agent.payment;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.service.interfaces.IAgentCommissionService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.proxy.AGT001;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewAgentCommissionPaymentActionBean")
public class AddNewAgentCommissionPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentCommissionService}")
	private IAgentCommissionService agentCommissionService;

	public void setAgentCommissionService(IAgentCommissionService agentCommissionService) {
		this.agentCommissionService = agentCommissionService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private User user;
	private AGT001 agentCommission;
	private List<AgentCommission> commissionList;
	private SalesPoints salesPoint;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		agentCommission = (agentCommission == null) ? (AGT001) getParam("AgentCommissionDTO") : agentCommission;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		commissionList = agentCommissionService.findAgentCommissionByInvoiceNo(agentCommission.getInvoiceNo());
	}

	@PreDestroy
	public void destroy() {
		removeParam("AgentCommissionDTO");
	}

	public AGT001 getAgentCommission() {
		return agentCommission;
	}

	public String payAgentCommission() {
		String result = null;
		try {
			agentCommissionService.paymentAgentCommission(commissionList, salesPoint, user.getBranch());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			extContext.getSessionMap().put("Agent Id :", agentCommission.getAgent());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public List<AgentCommission> getCommissionList() {
		return commissionList;
	}

	public void returnSalesPoints(SelectEvent event) {
		salesPoint = (SalesPoints) event.getObject();
	}

	public SalesPoints getSalesPoint() {
		return salesPoint;
	}

	public void setSalesPoint(SalesPoints salesPoint) {
		this.salesPoint = salesPoint;
	}

}
