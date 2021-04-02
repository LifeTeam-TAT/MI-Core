package org.ace.insurance.web.manage.report.medical;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.life.LifeDailyIncomeReportDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GroupLifeExcelTemplate {

	private XSSFWorkbook wb;

	public GroupLifeExcelTemplate() {
		load();
	}

	private void load() {
		try {
			InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/Group_Health_InsuredPerson_Beneficiary_Template.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Group_Health_InsuredPerson_Beneficiary_Template.xlsx tempalte", e);
		}
	}

	public void generate(OutputStream op, List<RelationShip> relationshipList, List<Township> townshipList, 
			List<Occupation> occupationList, List<Country> countryList) {
		
		try {
			String[] sheetNames = {"Relationship", "Township", "Occupation", "Country"};
			
			for(String sheetName: sheetNames) {
				Sheet sheet = wb.getSheet(sheetName);
				Row row = sheet.createRow(0);
				Cell cell = row.createCell(0);
				int rowCount = -1;
				
				switch(sheetName) {
					case "Relationship": 
						for(RelationShip relationship: relationshipList) {
							row = sheet.createRow(++rowCount);
							
							for(int i = 0; i < 2; i++) {
								cell = row.createCell(i);
								if(i == 0) cell.setCellValue(relationship.getName());
								if(i == 1) cell.setCellValue(relationship.getId());
							}
						}
						break;
					case "Township":
						for(Township township: townshipList) {
							row = sheet.createRow(++rowCount);
							
							for(int i = 0; i < 2; i++) {
								cell = row.createCell(i);
								if(i == 0) cell.setCellValue(township.getName());
								if(i == 1) cell.setCellValue(township.getId());
							}
						}
						break;
					case "Occupation":
						for(Occupation occupation: occupationList) {
							row = sheet.createRow(++rowCount);
							
							for(int i = 0; i < 2; i++) {
								cell = row.createCell(i);
								if(i == 0) cell.setCellValue(occupation.getName());
								if(i == 1) cell.setCellValue(occupation.getId());
							}
						}
						break;
					case "Country":
						for(Country country: countryList) {
							row = sheet.createRow(++rowCount);
							
							for(int i = 0; i < 2; i++) {
								cell = row.createCell(i);
								if(i == 0) cell.setCellValue(country.getName());
								if(i == 1) cell.setCellValue(country.getId());
							}
						}
						break;
				}
			}
			
//			wb.setPrintArea(2, 0, 1, 1, 1);
			wb.write(op);
			op.flush();
			op.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}