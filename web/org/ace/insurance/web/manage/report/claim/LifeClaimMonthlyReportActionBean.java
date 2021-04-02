package org.ace.insurance.web.manage.report.claim;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.claim.service.interfaces.ILifePolicyClaimService;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
import org.ace.insurance.web.manage.report.life.LifeClaimMonthlyReportExcel;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeClaimMonthlyReportActionBean")
public class LifeClaimMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	@ManagedProperty(value = "#{LifePolicyClaimService}")
	private ILifePolicyClaimService lifePolicyClaimService;

	public void setLifePolicyClaimService(ILifePolicyClaimService lifePolicyClaimService) {
		this.lifePolicyClaimService = lifePolicyClaimService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private LCL001 criteria;

	private List<LifeClaimMonthlyReportDTO> lifeClaimReportList;

	@PostConstruct
	public void init() {
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new LCL001();
		lifeClaimReportList = new ArrayList<LifeClaimMonthlyReportDTO>();
	}

	public void search() {
		lifeClaimReportList = lifeClaimProposalService.findLifeClaimByCriteria(criteria);
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = " LifeClaimMonthlyReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			LifeClaimMonthlyReportExcel LifeClaimMonthlyExcel = new LifeClaimMonthlyReportExcel();
			LifeClaimMonthlyExcel.generate(op, lifeClaimReportList, criteria);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifeClaimMonthlyReportExcel.xlsx", e);
		}
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public List<LifeClaimMonthlyReportDTO> getLifeClaimReportList() {
		return lifeClaimReportList;
	}

	public void setLifeClaimReportList(List<LifeClaimMonthlyReportDTO> lifeClaimReportList) {
		this.lifeClaimReportList = lifeClaimReportList;
	}

	public LCL001 getCriteria() {
		return criteria;
	}

	public void setCriteria(LCL001 criteria) {
		this.criteria = criteria;
	}

	public LifeProductType[] getLifeProductTypes() {
		return LifeProductType.values();
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicysearch = (LifePolicySearch) event.getObject();
		criteria.setPolicyNo(lifePolicysearch.getPolicyNo());
	}

}
