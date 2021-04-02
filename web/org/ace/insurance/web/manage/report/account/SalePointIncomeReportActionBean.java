package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.SalePointIncomeCriteria;
import org.ace.insurance.report.TLF.SalePointIncomeExcel;
import org.ace.insurance.report.TLF.SalePointIncomeReportDTO;
import org.ace.insurance.report.config.AgentJobConfigLoader;
import org.ace.insurance.report.config.service.interfaces.IReportConfigService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

@ViewScoped
@ManagedBean(name = "SalePointIncomeReportActionBean")
public class SalePointIncomeReportActionBean extends BaseBean {

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	@ManagedProperty(value = "#{ReportConfigService}")
	private IReportConfigService reportConfigService;

	public void setReportConfigService(IReportConfigService reportConfigService) {
		this.reportConfigService = reportConfigService;
	}

	private SalePointIncomeCriteria criteria;
	private List<SalePointIncomeReportDTO> sPIncomeReportDTOList;
	private DualListModel<String> dualListModel;
	private List<String> source;
	private List<String> target;

	@PostConstruct
	private void Init() {
		createNew();
		resetCriteria();
		loadDualListModel();
	}

	public void resetCriteria() {
		criteria = new SalePointIncomeCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		sPIncomeReportDTOList = new ArrayList<SalePointIncomeReportDTO>();
	}

	public void submit() {
		try {
			reportConfigService.configReport(dualListModel.getTarget());
			loadDualListModel();
			addInfoMessage(null, MessageId.REPORT_CONFIG_SUCCESS);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void filter() {
		sPIncomeReportDTOList = tlfService.findSalePointIncomeByCriteria(criteria);
	}

	private void loadDualListModel() {
		source = AgentJobConfigLoader.getKeys();
		target = new ArrayList<String>();
		target.add("NEWSALEPOINTREPORT");
		dualListModel = new DualListModel<String>(source, target);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		criteria.setSalePointId(salesPoints.getId());
		criteria.setSalePointName(salesPoints.getName());
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "SalePointReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			SalePointIncomeExcel spIncomeExcel = new SalePointIncomeExcel();
			spIncomeExcel.generate(op, sPIncomeReportDTOList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifeProposalReport.xlsx", e);
		}
	}

	private void createNew() {
		sPIncomeReportDTOList = new ArrayList<>();
	}

	public List<SalePointIncomeReportDTO> getsPIncomeReportDTOList() {
		return sPIncomeReportDTOList;
	}

	public void setCriteria(SalePointIncomeCriteria criteria) {
		this.criteria = criteria;
	}

	public SalePointIncomeCriteria getCriteria() {
		return criteria;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		EnumSet<PaymentChannel> set = EnumSet.allOf(PaymentChannel.class);
		return set;
	}

}
