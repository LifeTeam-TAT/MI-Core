package org.ace.insurance.web.manage.report.personalAccident;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.service.interfaces.ILifeProposalReportService;
import org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "PersonalAccidentProposalReportActionBean")
public class PersonalAccidentProposalReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{LifeProposalReportService}")
	private ILifeProposalReportService lifeProposalReportService;

	public void setLifeProposalReportService(ILifeProposalReportService lifeProposalReportService) {
		this.lifeProposalReportService = lifeProposalReportService;
	}

	private LifeProposalCriteria criteria;
	private User user;
	private boolean accessBranches;
	private List<PersonalAccidentProposalReport> proposalReportList;

	private String branchName;
	private final String reportName = "PersonalAccidentProposalReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	@PostConstruct
	private void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }

		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new LifeProposalCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		filter();
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
			try {
				proposalReportList = lifeProposalReportService.findPersonalAccidentProposal(criteria);
			} catch (SystemException ex) {
				handelSysException(ex);
			}
		}
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "PersonalAccidentProposalReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(proposalReportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export PersonalAccidentProposalReport.xlsx", e);
		}
	}

	private class ExportExcel {
		private List<PersonalAccidentProposalReport> personAcdtProposalReportList;
		private XSSFWorkbook wb;

		public ExportExcel(List<PersonalAccidentProposalReport> personAcdtProposalReportList) {
			this.personAcdtProposalReportList = personAcdtProposalReportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/personalAccident/personalAccidentProposalReport.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load PersonalAccidentProposalReport.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("PersonalAccidentProposal");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);

				Row row = null;
				Cell cell;

				row = sheet.getRow(0);
				cell = row.getCell(0);

				int i = 3;
				int index = 0;
				for (PersonalAccidentProposalReport report : personAcdtProposalReportList) {
					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);
					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(1);
					cell.setCellValue(report.getProposalNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(2);
					cell.setCellValue(report.getInsuredPersonName());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(3);
					cell.setCellValue(report.getAddressAndPhoneNo());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(4);
					cell.setCellValue(report.getAgeAndDateOfBirth());
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(5);
					cell.setCellValue((report.getMaritalStatus() != null) ? report.getMaritalStatus().getLabel() : null);
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(6);
					cell.setCellValue(report.getOccupation());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(7);
					cell.setCellValue(report.getAgentNameAndAgentCode());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(8);
					cell.setCellValue(report.getActivedProposalStartDate());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(9);
					cell.setCellValue(report.getActivedProposalEndDate());
					cell.setCellStyle(dateCellStyle);

					cell = row.createCell(10);
					cell.setCellValue(report.getSumInsured());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(11);
					cell.setCellValue(report.getPremium());
					cell.setCellStyle(currencyCellStyle);

					cell = row.createCell(12);
					cell.setCellValue(report.getCashReceiptNoAndPaymentDate());
					cell.setCellStyle(textCellStyle);

					cell = row.createCell(13);
					cell.setCellValue(report.getRemark());
					cell.setCellStyle(textCellStyle);

				}

				String strFormula;
				Font font = wb.createFont();
				font.setFontName("Myanmar3");

				i = i + 1;
				row = sheet.createRow(i);

				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 9));

				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 9), sheet, wb);
				cell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
				cell.getCellStyle().setFont(font);

				cell = row.createCell(10);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(K5:K" + i + ")";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 11, 13), sheet, wb);

				cell = row.createCell(11);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(L5:L" + i + ")";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 12, 13), sheet, wb);

				wb.setPrintArea(0, 0, 13, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void generateReport() throws Exception {
		try {
			if (criteria.getBranch() == null) {
				branchName = "All";
			} else {
				branchName = criteria.getBranch().getName();
			}

			FileHandler.forceMakeDirectory(dirPath);
			lifeProposalReportService.generatePersonalAcdtProposalReport(proposalReportList, dirPath, fileName, branchName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnAgent(SelectEvent event) {
		criteria.setAgent((Agent) event.getObject());
	}

	public void returnCustomer(SelectEvent event) {
		criteria.setCustomer((Customer) event.getObject());
	}

	public void returnOrganization(SelectEvent event) {
		criteria.setOrganization((Organization) event.getObject());
	}

	public void selectProduct() {
		selectProduct(InsuranceType.LIFE);
	}

	public void returnProduct(SelectEvent event) {
		criteria.setProduct((Product) event.getObject());
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	public LifeProposalCriteria getCriteria() {
		return criteria;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public List<PersonalAccidentProposalReport> getProposalReportList() {
		return proposalReportList;
	}

	public String getStream() {
		return pdfDirPath + fileName;

	}

}
