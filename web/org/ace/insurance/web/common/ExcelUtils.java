package org.ace.insurance.web.common;

import java.io.InputStream;

import org.ace.java.web.ApplicationSetting;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private final static String CURRENCY_FORMAT = "#,###.00";
	private final static String DATE_FORMAT = "dd-MM-yyyy";

	public static void fillCompanyLogo(Workbook wb, Sheet sheet, int addressColumn) {
		try {
			InputStream logoStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ApplicationSetting.getCompanyLogoFilePath());
			InputStream addressStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ApplicationSetting.getCompanyAddressAdFilePath());
			byte[] logoBytes = IOUtils.toByteArray(logoStream);
			byte[] addressBytes = IOUtils.toByteArray(addressStream);
			int logoPictureIdx = wb.addPicture(logoBytes, Workbook.PICTURE_TYPE_PNG);
			int addressPictureIdx = wb.addPicture(addressBytes, Workbook.PICTURE_TYPE_PNG);
			logoStream.close();
			addressStream.close();
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor logoAnchor = helper.createClientAnchor();
			logoAnchor.setCol1(1);
			logoAnchor.setRow1(0);
			Picture logoPict = drawing.createPicture(logoAnchor, logoPictureIdx);
			logoPict.resize(6.5);
			ClientAnchor addressAnchor = helper.createClientAnchor();
			addressAnchor.setCol1(addressColumn);
			addressAnchor.setRow1(0);
			Picture addressPict = drawing.createPicture(addressAnchor, addressPictureIdx);
			addressPict.resize(0.7);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static XSSFCellStyle getDefaultCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontName("Myanmar3");
		font.setFontHeight((short) (12 * 20));
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	public static XSSFCellStyle getWrapCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Font font = wb.createFont();
		font.setFontName("Myanmar3");
		font.setFontHeight((short) (12 * 20));
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	public static XSSFCellStyle getTextAlignRightStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = getDefaultCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		return cellStyle;
	}

	public static XSSFCellStyle getAlignCenterStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontName("Myanmar3");
		font.setFontHeight((short) (12 * 20));
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static XSSFCellStyle getFontBoldAlignCenterStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = wb.createFont();
		font.setFontName("Myanmar3");
		font.setFontHeight((short) (16 * 20));
		font.setBold(true);
		cellStyle.setFont(font);
		return cellStyle;
	}

	public static XSSFCellStyle getTextCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = getDefaultCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		return cellStyle;
	}

	public static XSSFCellStyle getCurrencyCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = getDefaultCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		cellStyle.setDataFormat(wb.createDataFormat().getFormat(CURRENCY_FORMAT));
		return cellStyle;
	}

	public static XSSFCellStyle getDateCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = getDefaultCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setDataFormat(wb.createDataFormat().getFormat(DATE_FORMAT));
		return cellStyle;
	}

	public static XSSFCellStyle getZawgyiCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = getDefaultCellStyle(wb);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		Font font = wb.createFont();
		font.setFontName("Zawgyi-One");
		cellStyle.setFont(font);
		return cellStyle;
	}

	public static void setRegionBorder(int borderWidth, CellRangeAddress crAddress, Sheet sheet, Workbook workBook) {
		RegionUtil.setBorderTop(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderBottom(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderRight(borderWidth, crAddress, sheet, workBook);
		RegionUtil.setBorderLeft(borderWidth, crAddress, sheet, workBook);
	}
}
