package org.ace.insurance.web.manage.report.life;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.report.life.service.interfaces.ILifeClaimRegisterReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeClaimRegisterReportActionBean")
public class LifeClaimRegisterReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger logger = LogManager.getLogger(this.getClass());

	@ManagedProperty(value = "#{LifeClaimRegisterReportService}")
	private ILifeClaimRegisterReportService lifeClaimRegisterReportService;

	public void setLifeClaimRegisterReportService(ILifeClaimRegisterReportService lifeClaimRegisterReportService) {
		this.lifeClaimRegisterReportService = lifeClaimRegisterReportService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private EnquiryCriteria criteria;
	private List<LifeClaimRegisterReport> lifeClaimRegisterReportList;
	private List<Product> productList;
	private List<Branch> branchList;
	private LazyDataModelUtil<LifeClaimRegisterReport> lazyModel;
	private final String reportName = "LifeClaimRegisterReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";
	private boolean accessBranches;
	private User user;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() {
		criteria = new EnquiryCriteria();
		user = (User) getParam("LoginUser");
		loadProductList();
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		if (criteria.getStartDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}
		if (criteria.getEndDate() == null) {
			Date endDate = new Date();
			criteria.setEndDate(endDate);
		}
		filter();
	}

	public void loadProductList() {
		productList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		String farmerId = KeyFactorIDConfig.getFarmerId();
		for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			if (product.getId().equals(farmerId)) {
				iterator.remove();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void filter() {
		boolean valid = true;
		String formID = "customerList";
		if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
			if (criteria.getStartDate().after(criteria.getEndDate())) {
				addErrorMessage(formID + ":startDate", MessageId.STARTDATE_MUSTBE_LESSTHAN_ENDDATE);
				valid = false;
			}
		}
		if (valid) {
			List<String> productIdList = new ArrayList<String>();
			for (Product product : productList) {
				productIdList.add(product.getId());
			}
			lifeClaimRegisterReportList = lifeClaimRegisterReportService.findLifeClaimRegisterReports(criteria, productIdList);
			lazyModel = new LazyDataModelUtil(lifeClaimRegisterReportList);
		}
	}

	public LazyDataModelUtil<LifeClaimRegisterReport> getLazyModel() {
		return lazyModel;
	}

	public List<LifeClaimRegisterReport> getLifeClaimRegisterReportList() {
		return lifeClaimRegisterReportList;
	}

	public void setLifeClaimRegisterReportList(List<LifeClaimRegisterReport> lifeClaimRegisterReportList) {
		this.lifeClaimRegisterReportList = lifeClaimRegisterReportList;
	}

	public EnquiryCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(EnquiryCriteria criteria) {
		this.criteria = criteria;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void loadBranchList() {
		if (branchList == null) {
			branchList = branchService.findAllBranch();
		}
	}

	public List<Branch> getBranchList() {
		if (branchList == null) {
			branchList = branchService.findAllBranch();
		}
		return branchList;
	}

	public void generateReport() {
		try {
			String fullTemplateFilePath = "report-template/life/lifeClaimRegisterReport.jrxml";
			if (criteria.getBranch() != null) {
				branchList = new ArrayList<Branch>();
				branchList.add(criteria.getBranch());
			} else {
				branchList = branchService.findAllBranch();
			}
			List<String> productIdList = new ArrayList<String>();
			for (Product product : productList) {
				productIdList.add(product.getId());
			}
			lifeClaimRegisterReportService.generateLifeClaimRegisterReport(fullTemplateFilePath, criteria, productIdList, branchList, dirPath, fileName);
		} catch (Exception e) {
			logger.error("Fail to create the directory(" + dirPath + ").", e);
			e.printStackTrace();
		}
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		criteria.setCustomer(customer);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		criteria.setOrganization(organization);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProduct(product);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}
}
