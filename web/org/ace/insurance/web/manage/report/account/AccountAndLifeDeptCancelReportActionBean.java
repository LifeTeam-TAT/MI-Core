package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.report.TLF.AccountAndLifeCancelReportDTO;
import org.ace.insurance.report.TLF.TlfCriteria;
import org.ace.insurance.report.config.AgentJobConfigLoader;
import org.ace.insurance.report.config.service.interfaces.IReportConfigService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
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
import org.primefaces.model.DualListModel;

@ViewScoped
@ManagedBean(name = "AccountAndLifeDeptCancelReportActionBean")
public class AccountAndLifeDeptCancelReportActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

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

	private TlfCriteria tlfCriteria;
	private List<Product> productList;
	private Product product;
	private User user;
	private List<AccountAndLifeCancelReportDTO> accountAndLifeCancelReportDTOList;
	private DualListModel<String> dualListModel;
	private List<String> source;
	private List<String> target;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		loadProductList();
		resetCriteria();
		product = new Product();
		loadDualListModel();

	}

	private void loadDualListModel() {
		source = AgentJobConfigLoader.getKeys();
		target = new ArrayList<String>();
		target.add("ACCOUNTANDLIFEDEPTCANCELREPORT");
		dualListModel = new DualListModel<String>(source, target);
	}

	public void resetCriteria() {
		tlfCriteria = new TlfCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		tlfCriteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		tlfCriteria.setEndDate(endDate);
		accountAndLifeCancelReportDTOList = new ArrayList<AccountAndLifeCancelReportDTO>();
		product = new Product();
	}

	public void loadProductList() {
		productList = new ArrayList<Product>();
		List<Product> healthProductList = productService.findProductByInsuranceType(InsuranceType.HEALTH);
		List<Product> lifeProductList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		productList.addAll(healthProductList);
		productList.addAll(lifeProductList);
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
		if (product != null) {
			tlfCriteria.getProductIdList().add(product.getId());
		}
		accountAndLifeCancelReportDTOList = tlfService.findAccountAndLifeCancelReportDTO(tlfCriteria);

	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "accountAndLifeDeptCancelReport.xlsx";
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
			try (InputStream inp = this.getClass().getResourceAsStream("/report-template/accountReport/accountAndLifedeptReport.xlsx");) {
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load accountAndLifedeptReport.xlsx template", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("SalePointReport");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell cell;

				int i = 1;
				int index = 0;
				int startIndex;
				String accountPremiumFormula;
				String lifePremiumFormula;
				String GrandSIFormula = "";
				String GrandPremiumFormula = "";
				Map<String, List<AccountAndLifeCancelReportDTO>> map = separateSalePoint();
				for (List<AccountAndLifeCancelReportDTO> salePointList : map.values()) {
					startIndex = i + 1 + 1;
					for (AccountAndLifeCancelReportDTO salePointDTO : salePointList) {
						i = i + 1;
						index = index + 1;
						row = sheet.createRow(i);
						// index
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);
						// TLF no
						cell = row.createCell(1);
						cell.setCellValue(salePointDTO.getTlfNo());
						cell.setCellStyle(textCellStyle);

						// PRODUCT GROUP
						cell = row.createCell(2);
						cell.setCellValue(salePointDTO.getProductName());
						cell.setCellStyle(textCellStyle);

						// SALEPOINT
						/*
						 * cell = row.createCell(3); cell.setCellValue(salePointDTO.getSalePointName());
						 * cell.setCellStyle(textCellStyle);
						 */

						// PAYMENT CHANNEL
						cell = row.createCell(3);
						cell.setCellValue(salePointDTO.getPaymentChannel());
						cell.setCellStyle(textCellStyle);

						// ACCOUNT RECEIPT DATE
						cell = row.createCell(4);
						cell.setCellValue(salePointDTO.getAccountReceiptDate());
						cell.setCellStyle(dateCellStyle);

						// sale point name
						cell = row.createCell(5);
						cell.setCellValue(salePointDTO.isAccountStatus() ? "CANCEL" : "COMPLETE");
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(6);
						cell.setCellValue(salePointDTO.getAccountPremium());
						cell.setCellStyle(currencyCellStyle);

						cell = row.createCell(7);
						cell.setCellValue(salePointDTO.getLifeDeptPaymentDate());
						cell.setCellStyle(dateCellStyle);

						cell = row.createCell(8);
						cell.setCellValue(salePointDTO.isLifeDeptStatus() ? "CANCEL" : "COMPLETE");
						cell.setCellStyle(textCellStyle);

						// life dept premium
						cell = row.createCell(9);
						cell.setCellValue(salePointDTO.getHomeAmount());
						cell.setCellStyle(currencyCellStyle);

					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 6));
					row = sheet.createRow(i);

					cell = row.createCell(0);
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 6), sheet, wb);
					cell.setCellValue("Total");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(6);
					cell.setCellStyle(currencyCellStyle);
					accountPremiumFormula = "SUM(H3:H" + i + ")";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(accountPremiumFormula);

					cell = row.createCell(9);
					cell.setCellStyle(currencyCellStyle);
					accountPremiumFormula = "SUM(H3:H" + i + ")";
					lifePremiumFormula = "SUM(K3:K" + i + ")";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(lifePremiumFormula);

					wb.setPrintArea(0, 0, 12, 0, i);
					wb.write(op);
					op.flush();
					op.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, List<AccountAndLifeCancelReportDTO>> separateSalePoint() {
		Map<String, List<AccountAndLifeCancelReportDTO>> map = new LinkedHashMap<>();
		for (AccountAndLifeCancelReportDTO dto : accountAndLifeCancelReportDTOList) {
			if (map.containsKey(dto.getSalePointName())) {
				map.get(dto.getSalePointName()).add(dto);
			} else {
				List<AccountAndLifeCancelReportDTO> list = new ArrayList<>();
				list.add(dto);
				map.put(dto.getSalePointName(), list);
			}
		}
		return map;
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		tlfCriteria.setSalePoint(salePoint);
	}

	public TlfCriteria getTlfCriteria() {
		return tlfCriteria;
	}

	public void setTlfCriteria(TlfCriteria tlfCriteria) {
		this.tlfCriteria = tlfCriteria;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public List<AccountAndLifeCancelReportDTO> getAccountAndLifeCancelReportDTOList() {
		return accountAndLifeCancelReportDTOList;
	}

}
