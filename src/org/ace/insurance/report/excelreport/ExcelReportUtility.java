package org.ace.insurance.report.excelreport;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

abstract class ExcelReportUtility<T> {
	public List<T> list;
	public XSSFWorkbook wb;
	public final String CURRENCY_FORMAT = "#,###.00";
	public final String DATE_FORMAT = "dd-mmm-yy";

	public ExcelReportUtility(List<T> list) {
		this.list = list;
	}

	public XSSFCellStyle getDefaultCell() {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Myanmar3");
		cellStyle.setFont(font);
		return cellStyle;
	}

	public XSSFCellStyle getDefaultTextCell() {
		XSSFCellStyle cellStyle = getDefaultCell();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		return cellStyle;
	}

	public XSSFCellStyle getDefaultDateCell() {
		XSSFDataFormat cellFormat = wb.createDataFormat();
		XSSFCellStyle cellStyle = getDefaultCell();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		cellStyle.setDataFormat(cellFormat.getFormat(DATE_FORMAT));
		return cellStyle;
	}

	public XSSFCellStyle getDefaultCurrencyCell() {
		XSSFDataFormat cellFormat = wb.createDataFormat();
		XSSFCellStyle cellStyle = getDefaultCell();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		cellStyle.setDataFormat(cellFormat.getFormat(CURRENCY_FORMAT));
		return cellStyle;
	}

	public void setRegionBorder(int borderWidth, CellRangeAddress crAddress, Sheet sheet, Workbook workBook) {
		RegionUtil.setBorderTop(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderBottom(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderRight(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderLeft(borderWidth, crAddress, sheet, workBook);
	}

	abstract void load();

	abstract void generate(OutputStream op);

	abstract double getTotalPremium();

	abstract int getTotalPolicyCount();

}
