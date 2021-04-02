package org.ace.insurance.web.manage.report.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.payment.service.interfaces.ITLFService;
import org.ace.insurance.report.TLF.AccountManualReceiptDTO;
import org.ace.insurance.report.TLF.TlfCriteria;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ViewScoped
@ManagedBean(name = "AccountManualReceiptReportActionBean")
public class AccountManualReceiptReportActionBean extends BaseBean {

	@ManagedProperty(value = "#{TLFService}")
	private ITLFService tlfService;

	public void setTlfService(ITLFService tlfService) {
		this.tlfService = tlfService;
	}

	private TlfCriteria tlfCriteria;
	private List<AccountManualReceiptDTO> accountManualReceiptDTOList;
	private User user;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		resetCriteria();
	}

	public void filterByCriteria() {
		accountManualReceiptDTOList = tlfService.findAccountManualReceiptListByCriteria(tlfCriteria);
	}

	public void resetCriteria() {
		tlfCriteria = new TlfCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		tlfCriteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		tlfCriteria.setEndDate(endDate);
		accountManualReceiptDTOList = new ArrayList<AccountManualReceiptDTO>();
	}

	private class ExportExcel {
		private XSSFWorkbook wb;

		public ExportExcel() {
			load();
		}

		private void load() {
			try (InputStream inp = this.getClass().getResourceAsStream("/report-template/accountReport/accountManualReceipt.xlsx");) {
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
				int startIndex;
				String strFormula;
				String GrandSIFormula = "";
				Map<String, List<AccountManualReceiptDTO>> map = separateSalePoint();
				for (List<AccountManualReceiptDTO> salePointList : map.values()) {
					startIndex = i + 1 + 1;
					for (AccountManualReceiptDTO salePointDTO : salePointList) {
						i = i + 1;
						index = index + 1;
						row = sheet.createRow(i);
						// index
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);
						// Product Name
						cell = row.createCell(1);
						cell.setCellValue(salePointDTO.getAccountName());
						cell.setCellStyle(textCellStyle);
						// Amount
						cell = row.createCell(2);
						cell.setCellValue(salePointDTO.getAmount());
						cell.setCellStyle(currencyCellStyle);
						// Received Date
						cell = row.createCell(3);
						cell.setCellValue(salePointDTO.getCreatedDate());
						cell.setCellStyle(dateCellStyle);

					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 1));
					row = sheet.createRow(i);

					cell = row.createCell(0);
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 1), sheet, wb);
					cell.setCellValue("Total");
					cell.setCellStyle(defaultCellStyle);

					cell = row.createCell(2);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(C3:C" + i + ")";
					GrandSIFormula += "H" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					wb.setPrintArea(0, 0, 13, 0, i);
					wb.write(op);
					op.flush();
					op.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, List<AccountManualReceiptDTO>> separateSalePoint() {
		Map<String, List<AccountManualReceiptDTO>> map = new LinkedHashMap<>();
		for (AccountManualReceiptDTO dto : accountManualReceiptDTOList) {
			if (map.containsKey(dto.getAccountName())) {
				map.get(dto.getAccountName()).add(dto);
			} else {
				List<AccountManualReceiptDTO> list = new ArrayList<>();
				list.add(dto);
				map.put(dto.getAccountName(), list);
			}
		}
		return map;
	}

	public TlfCriteria getTlfCriteria() {
		return tlfCriteria;
	}

	public void setTlfCriteria(TlfCriteria tlfCriteria) {
		this.tlfCriteria = tlfCriteria;
	}

	public ITLFService getTlfService() {
		return tlfService;
	}

	public List<AccountManualReceiptDTO> getAccountManualReceiptDTOList() {
		return accountManualReceiptDTOList;
	}

}
