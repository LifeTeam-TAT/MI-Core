package org.ace.insurance.web.manage.report.life;

/**
 * @author NNH
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.report.life.GroupLifeProposalCriteria;
import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.insurance.report.life.service.interfaces.IGroupLifeProposalReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.web.common.BaseBean;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean(name = "GroupLifeProposalReportActionBean")
public class GroupLifeProposalReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{GroupLifeProposalReportService}")
	private IGroupLifeProposalReportService grouplifeProposalReportService;
	@ManagedProperty(value = "#{UserProcessService}")
	private IUserProcessService userProcessService;
	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setGrouplifeProposalReportService(IGroupLifeProposalReportService grouplifeProposalReportService) {
		this.grouplifeProposalReportService = grouplifeProposalReportService;
	}

	public void setUserProcessService(IUserProcessService userProcessService) {
		this.userProcessService = userProcessService;
	}

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private Date startDate;
	private Date endDate;

	private GroupLifeProposalCriteria criteria;
	private List<GroupLifeProposalReport> grouplifeProposalList;
	private LifeProposal lifeProposal;

	protected IDConfigLoader configLoader;
	private User user;
	private LazyDataModelUtil<GroupLifeProposalReport> lazyModel;
	private boolean accessBranches;
	private List<Branch> branchList;

	private void initializeInjection() {
		startDate = (startDate == null) ? (Date) getParam("startDate") : startDate;
		endDate = (endDate == null) ? (Date) getParam("endDate") : endDate;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		lifeProposal = new LifeProposal();
		criteria = new GroupLifeProposalCriteria();
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);
		user = userProcessService.getLoginUser();
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		if (configLoader.isCentralizedSystem()) {
			criteria.setBranch(user.getBranch());
		} else {
			Branch branch = branchService.findByBranchCode(configLoader.getBranchCode());
			criteria.setBranch(branch);
		}
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -3);
			criteria.setStartDate(cal.getTime());
		}
		if (criteria.getEndDate() == null) {
			Date endDate = new Date();
			criteria.setEndDate(endDate);
		}
		grouplifeProposalList = grouplifeProposalReportService.findLifeProposal(criteria);
		lazyModel = new LazyDataModelUtil<GroupLifeProposalReport>(grouplifeProposalList);
	}

	public LazyDataModel<GroupLifeProposalReport> getLazyModel() {
		return lazyModel;
	}

	public void filter() {
		criteria.setAgent(lifeProposal.getAgent());
		criteria.setCustomer(lifeProposal.getCustomer());
		criteria.setOrganization(lifeProposal.getOrganization());
		grouplifeProposalList = grouplifeProposalReportService.findLifeProposal(criteria);
		lazyModel = new LazyDataModelUtil<GroupLifeProposalReport>(grouplifeProposalList);
	}

	public void resetCriteria() {
		init();
	}

	public List<Branch> getBranchList() {
		if (branchList == null) {
			branchList = branchService.findAllBranch();
		}
		return branchList;
	}

	/*
	 * * Product methods end
	 */

	private final String reportName = "GroupLifeProposalReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		return pdfDirPath + fileName;

	}

	public void generateReport() throws Exception {
		try {
			FileHandler.forceMakeDirectory(dirPath);
			grouplifeProposalReportService.generateGroupLifeProposalReport(grouplifeProposalList, dirPath, fileName, criteria, branchList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<GroupLifeProposalReport> getGrouplifeProposalList() {
		return grouplifeProposalList;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	public GroupLifeProposalCriteria getCriteria() {
		return criteria;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

}
