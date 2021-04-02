package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.LifeProposalReport;
import org.ace.insurance.report.life.service.interfaces.ILifeProposalReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean(name = "LifeProposalReportActionBean")
public class LifeProposalReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProposalReportService}")
	private ILifeProposalReportService lifeProposalReportService;

	public void setLifeProposalReportService(ILifeProposalReportService lifeProposalReportService) {
		this.lifeProposalReportService = lifeProposalReportService;
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

	private LifeProposalCriteria criteria;
	private List<LifeProposalReport> lifeProposalReportList;
	private boolean accessBranches;
	private User user;
	private LazyDataModelUtil<LifeProposalReport> lazyModel;
	private String branchName;
	private List<Product> productList;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		loadProductList();
		resetCriteria();
	}

	public void loadProductList() {
		productList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		String farmerId = KeyFactorIDConfig.getFarmerId();
		Product product;
		for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
			product = iterator.next();
			if (product.getId().equals(farmerId)) {
				iterator.remove();
				break;
			}
		}
	}

	public LazyDataModel<LifeProposalReport> getLazyModel() {
		return lazyModel;
	}

	public void filter() {
		boolean valid = true;
		String formID = "proposalList";
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
			lifeProposalReportList = lifeProposalReportService.findLifeProposal(criteria, productIdList);
			lazyModel = new LazyDataModelUtil<LifeProposalReport>(lifeProposalReportList);
		}
	}

	public ILifeProposalReportService getLifeProposalReportService() {
		return lifeProposalReportService;
	}

	public LifeProposalCriteria getCriteria() {
		return criteria;
	}

	public void resetCriteria() {
		criteria = new LifeProposalCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -3);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		filter();
	}

	public double totalReportSumInsured() {
		double total = 0.0;
		for (LifeProposalReport report : lifeProposalReportList) {
			total += report.getSumInsured();
		}
		return total;
	}

	private final String reportName = "LifeProposalReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		return pdfDirPath + fileName;

	}

	public void generateReport() throws Exception {
		try {
			if (criteria.getBranch() == null) {
				branchName = "All";
			} else {
				branchName = criteria.getBranch().getName();
			}

			FileHandler.forceMakeDirectory(dirPath);
			lifeProposalReportService.generateLifeProposalReport(lifeProposalReportList, dirPath, fileName, branchName);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	public void selectProduct() {
		selectProduct(InsuranceType.LIFE);
	}

	public List<Product> getProductList() {
		return productList;
	}

	// Exporting
	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "LifeProposalReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try (OutputStream op = ec.getResponseOutputStream();) {
			ExportExcel exportExcel = new ExportExcel();
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifeProposalReport.xlsx", e);
		}
	}

	private class ExportExcel {
		private XSSFWorkbook wb;

		public ExportExcel() {
			load();
		}

		private void load() {
			try (InputStream inp = this.getClass().getResourceAsStream("/report-template/life/LifeProposalReport.xlsx");) {
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifeProposalReport.xlsx template", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("LifeProposalReport");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell cell;

				ExcelUtils.fillCompanyLogo(wb, sheet, 10);
				row = sheet.getRow(0);
				cell = row.getCell(0);
				if (criteria.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Life Proposal Report ( All )");
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n \n Life Proposal Report ( " + criteria.getBranch().getName() + " )");
				}

				int i = 1;
				int index = 0;
				int startIndex;
				String strFormula;
				String GrandSIFormula = "";
				String GrandPremiumFormula = "";
				Map<String, List<LifeProposalReport>> map = separateBranch();
				for (List<LifeProposalReport> branchList : map.values()) {
					startIndex = i + 1 + 1;
					for (LifeProposalReport lifeProposalReport : branchList) {
						i = i + 1;
						index = index + 1;
						row = sheet.createRow(i);
						// index
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);

						// proposalNo
						cell = row.createCell(1);
						cell.setCellValue(lifeProposalReport.getPorposalNo());
						cell.setCellStyle(textCellStyle);

						// date of proposed
						cell = row.createCell(2);
						cell.setCellValue(lifeProposalReport.getInspectionDate());
						cell.setCellStyle(dateCellStyle);

						// customer name
						cell = row.createCell(3);
						cell.setCellValue(lifeProposalReport.getCustomerName());
						cell.setCellStyle(textCellStyle);

						// father name
						cell = row.createCell(4);
						cell.setCellValue(lifeProposalReport.getFatherName());
						cell.setCellStyle(textCellStyle);

						// agent name and code no
						cell = row.createCell(5);
						cell.setCellValue(lifeProposalReport.getAgentNameAndCode());
						cell.setCellStyle(textCellStyle);

						// address and phone no
						cell = row.createCell(6);
						cell.setCellValue(lifeProposalReport.getAddressAndPhoneNo());
						cell.setCellStyle(textCellStyle);

						// SumInsured
						cell = row.createCell(7);
						cell.setCellValue(lifeProposalReport.getSumInsured());
						cell.setCellStyle(currencyCellStyle);

						// One Year Premium
						cell = row.createCell(8);
						cell.setCellValue(lifeProposalReport.getOneYearPremium());
						cell.setCellStyle(currencyCellStyle);

						// Premium
						cell = row.createCell(9);
						cell.setCellValue(lifeProposalReport.getPremium());
						cell.setCellStyle(currencyCellStyle);

						// branch
						cell = row.createCell(10);
						cell.setCellValue(lifeProposalReport.getBranch());
						cell.setCellStyle(textCellStyle);

						// workFlowTask
						cell = row.createCell(11);
						cell.setCellValue(lifeProposalReport.getWorkflow());
						cell.setCellStyle(textCellStyle);

						// responsiblePerson
						cell = row.createCell(12);
						cell.setCellValue(lifeProposalReport.getResponsiblePerson());
						cell.setCellStyle(textCellStyle);

						// remarks
						cell = row.createCell(13);
						cell.setCellValue(lifeProposalReport.getRemark());
						cell.setCellStyle(textCellStyle);
					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
					row = sheet.createRow(i);

					cell = row.createCell(0);
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);
					cell.setCellValue("Sub Total");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(7);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(H" + startIndex + ":H" + i + ")";
					GrandSIFormula += "H" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					cell = row.createCell(8);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(9);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(J" + startIndex + ":J" + i + ")";
					GrandPremiumFormula += "J" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					sheet.addMergedRegion(new CellRangeAddress(i, i, 10, 13));
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 10, 13), sheet, wb);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(7);
				cell.setCellStyle(currencyCellStyle);
				GrandSIFormula = GrandSIFormula.substring(0, GrandSIFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandSIFormula);

				cell = row.createCell(8);
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(9);
				cell.setCellStyle(currencyCellStyle);
				GrandPremiumFormula = GrandPremiumFormula.substring(0, GrandPremiumFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandPremiumFormula);

				sheet.addMergedRegion(new CellRangeAddress(i, i, 10, 13));
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 10, 13), sheet, wb);

				wb.setPrintArea(0, 0, 13, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, List<LifeProposalReport>> separateBranch() {
		Map<String, List<LifeProposalReport>> map = new LinkedHashMap<String, List<LifeProposalReport>>();
		for (LifeProposalReport report : lifeProposalReportList) {
			if (map.containsKey(report.getBranch())) {
				map.get(report.getBranch()).add(report);
			} else {
				List<LifeProposalReport> list = new ArrayList<LifeProposalReport>();
				list.add(report);
				map.put(report.getBranch(), list);
			}
		}
		return map;
	}

}
