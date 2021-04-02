package org.ace.insurance.web.manage.report.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.insurance.report.common.service.interfaces.ISalesReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "SalesReportActionBean")
public class SalesReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{SalesReportService}")
	private ISalesReportService salesReportService;

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	private SalesReportCriteria criteria;
	private List<SalesReport> salesReportList;
	private PolicyReferenceType referenceType;
	private Agent agent;
	private List<Product> productList;
	private Product product;
	private LazyDataModelUtil<SalesReport> lazyModel;
	private final String reportName = "SaleReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	@PostConstruct
	public void init() {
		agent = new Agent();
		product = new Product();
		criteria = new SalesReportCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		criteria.setReferenceType(referenceType);
		productList = productService.findAllProduct();
		salesReportList = salesReportService.findSalesReport(criteria);
		lazyModel = new LazyDataModelUtil(salesReportList);
	}

	public Agent getAgent() {
		return agent;
	}

	public LazyDataModelUtil<SalesReport> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModelUtil<SalesReport> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public SalesReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SalesReportCriteria criteria) {
		this.criteria = criteria;
	}

	public List<SalesReport> getSalesReportList() {
		return salesReportList;
	}

	public void setSalesReportList(List<SalesReport> salesReportList) {
		this.salesReportList = salesReportList;
	}

	public List<PolicyReferenceType> getReferenceTypes() {
		// FIXME CHECK REFTYPE
		return Arrays.asList(PolicyReferenceType.ENDOWNMENT_LIFE_POLICY, PolicyReferenceType.LIFE_BILL_COLLECTION, PolicyReferenceType.HEALTH_POLICY);
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	/*
	 * * Product methods start
	 */
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	/*
	 * * Product methods end
	 */

	public void filter() {
		criteria.setReferenceType(referenceType);
		salesReportList = salesReportService.findSalesReport(criteria);
		lazyModel = new LazyDataModelUtil(salesReportList);
	}

	public double totalPremium() {
		double totalPremium = 0.0;
		for (SalesReport s : salesReportList) {
			totalPremium += s.getPremium();
		}

		return totalPremium;
	}

	public double totalAmount() {
		double totalAmount = 0.0;
		for (SalesReport s : salesReportList) {
			totalAmount += s.getInsuredAmount();
		}

		return totalAmount;
	}

	public double totalCommission() {
		double totalCommission = 0.0;
		for (SalesReport s : salesReportList) {
			totalCommission += s.getCommission();
		}

		return totalCommission;
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public void generateReport() {
		try {
			FileHandler.forceMakeDirectory(dirPath);
			salesReportService.generateSaleReport(salesReportList, dirPath + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProduct(product);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}
}
