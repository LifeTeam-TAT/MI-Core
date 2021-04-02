package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
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
import org.ace.insurance.report.TLF.DailyIncomeReportDTO;
import org.ace.insurance.report.TLF.TlfCriteria;
import org.ace.insurance.report.config.AgentJobConfigLoader;
import org.ace.insurance.report.config.service.interfaces.IReportConfigService;
import org.ace.insurance.system.common.PaymentChannel;
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
@ManagedBean(name = "DailyIncomeReportActionBean")
public class DailyIncomeReportActionBean extends BaseBean implements Serializable {

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
	private List<DailyIncomeReportDTO> dailyIncomeReportDTOList;
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
		target.add("SALEPOINTDAILYREPORT");
		dualListModel = new DualListModel<String>(source, target);
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
		dailyIncomeReportDTOList = tlfService.findDailyIncomeReportBySalePointCriteria(tlfCriteria);

	}

	public void resetCriteria() {
		tlfCriteria = new TlfCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		tlfCriteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		tlfCriteria.setEndDate(endDate);
		dailyIncomeReportDTOList = new ArrayList<DailyIncomeReportDTO>();
		product = new Product();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "dailyIncomeReport.xlsx";
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
			try (InputStream inp = this.getClass().getResourceAsStream("/report-template/accountReport/dailyIncomeReport.xlsx");) {
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifeProposalReport.xlsx template", e);
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
				int startIndex = 0;
				String strFormula;
				String GrandSIFormula = "";
				Map<String, List<DailyIncomeReportDTO>> map = separateSalePoint();
				for (List<DailyIncomeReportDTO> dailyIncomeDTOList : map.values()) {
					startIndex = i + 1 + 1;
					for (DailyIncomeReportDTO dailyIncomeDTO : dailyIncomeDTOList) {
						i = i + 1;
						index = index + 1;
						row = sheet.createRow(i);
						// index
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);
						// product name
						cell = row.createCell(1);
						cell.setCellValue(dailyIncomeDTO.getProductName());
						cell.setCellStyle(textCellStyle);
						// pocliy no
						cell = row.createCell(2);
						cell.setCellValue(dailyIncomeDTO.getPolicyNo());
						cell.setCellStyle(textCellStyle);

						// Receipt No
						cell = row.createCell(3);
						cell.setCellValue(dailyIncomeDTO.getReceiptNo());
						cell.setCellStyle(textCellStyle);

						// received Date
						cell = row.createCell(4);
						cell.setCellValue(dailyIncomeDTO.getPaymentDate());
						cell.setCellStyle(dateCellStyle);

						// Amont
						cell = row.createCell(5);
						cell.setCellValue(dailyIncomeDTO.getHomeAmount());
						cell.setCellStyle(currencyCellStyle);

						// payment channel
						cell = row.createCell(6);
						cell.setCellValue(dailyIncomeDTO.getPaymentChannel());
						cell.setCellStyle(textCellStyle);

						/*
						 * // salepoint name cell = row.createCell(7);
						 * cell.setCellValue(dailyIncomeDTO.getSalePointName());
						 * cell.setCellStyle(textCellStyle);
						 */
					}

				}

				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				row = sheet.createRow(i);

				cell = row.createCell(0);
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 4), sheet, wb);
				cell.setCellValue("Total");
				cell.setCellStyle(defaultCellStyle);

				cell = row.createCell(5);
				cell.setCellStyle(currencyCellStyle);
				strFormula = "SUM(F3:F" + i + ")";
				GrandSIFormula += "H" + (i + 1) + "+";
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(strFormula);

				wb.setPrintArea(0, 0, 12, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (

			Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Map<String, List<DailyIncomeReportDTO>> separateSalePoint() {
		Map<String, List<DailyIncomeReportDTO>> map = new LinkedHashMap<>();
		for (DailyIncomeReportDTO dto : dailyIncomeReportDTOList) {
			if (map.containsKey(dto.getPolicyNo())) {
				map.get(dto.getPolicyNo()).add(dto);
			} else {
				List<DailyIncomeReportDTO> list = new ArrayList<>();
				list.add(dto);
				map.put(dto.getPolicyNo(), list);
			}
		}
		return map;
	}

	public List<DailyIncomeReportDTO> getDailyIncomeReportDTOList() {
		return dailyIncomeReportDTOList;
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

	public TlfCriteria getTlfCriteria() {
		return tlfCriteria;
	}

	public void setTlfCriteria(TlfCriteria tlfCriteria) {
		this.tlfCriteria = tlfCriteria;
	}

	public EnumSet<PaymentChannel> getPaymentChannels() {
		EnumSet<PaymentChannel> set = EnumSet.allOf(PaymentChannel.class);
		return set;
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		tlfCriteria.setSalePoint(salePoint);
	}

}
