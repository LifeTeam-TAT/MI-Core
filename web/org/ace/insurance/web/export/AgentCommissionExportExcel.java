package org.ace.insurance.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.payment.AC001;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.web.common.ExportExcel;
import org.ace.insurance.web.manage.agent.AgentEnquiryCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AgentCommissionExportExcel extends BaseBean implements ExportExcel {

	private XSSFWorkbook wb;
	private static final String NUMBER_FORMAT = "#,###.00";
	private AgentEnquiryCriteria agentEnquiryCriteria;
	private List<AC001> agentCommissionList;
	
	public AgentCommissionExportExcel() {
		load();	
	}
	
	public AgentCommissionExportExcel(AgentEnquiryCriteria agentEnquiryCriteria,List<AC001> agentCommissionList) {
		this.agentEnquiryCriteria= agentEnquiryCriteria;
		this.agentCommissionList = agentCommissionList;
		load();	
	}
	
	private void load(){
		try {
			InputStream inp= getFacesContext().getExternalContext().getResourceAsStream("/formattedExcel/AgentComissionTemplate.xlsx");
			wb = new XSSFWorkbook(inp);
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentCommissionTemplate.xlsx Formatted Excel", e);
		}		
	}
	
	private XSSFCellStyle getDefaultCell() {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = wb.createFont();
		font.setFontName("Myanmar2");
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	private XSSFCellStyle getFirstCell(){
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = wb.createFont();
		font.setFontName("Myanmar2");
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	private XSSFCellStyle getLastCell(){
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = wb.createFont();
		font.setFontName("Myanmar2");
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	private XSSFCellStyle getMiddleCell(){
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = wb.createFont();
		font.setFontName("Myanmar2");
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public void generate(OutputStream op) throws IOException {
		Sheet sheet = wb.getSheet("commission");
		Agent agent = agentEnquiryCriteria.getSelectedAgent();
		double totalCommissionAmt= 0.0;
		DataFormat format = wb.createDataFormat();
		
		Cell cellForDate= sheet.getRow(0).getCell(14);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		cellForDate.setCellValue(dateFormat.format(agentEnquiryCriteria.getStartDate())+"-"+dateFormat.format(agentEnquiryCriteria.getEndDate()));
		
		Cell cellForName=sheet.getRow(2).getCell(4);
		cellForName.setCellValue(agent.getFullName());
		
		Cell cellForLicenseNo = sheet.getRow(3).getCell(4);
		cellForLicenseNo.setCellValue(agent.getLiscenseNo());
		
		Cell cellForPhoneNo = sheet.getRow(4).getCell(4);
		cellForPhoneNo.setCellValue("-");
		
		Cell cellForAddress = sheet.getRow(5).getCell(4);
		cellForAddress.setCellValue(agent.getFullAddress());
		
		int i = 9;
		for(AC001 agentCommission: agentCommissionList){
			i = i+1;
			sheet.addMergedRegion(new CellRangeAddress(i, i, 15, 17));
			Row row = sheet.createRow(i);
			Cell nameCell = row.createCell(1);
			nameCell.setCellValue(agentCommission.getCustomerName());
			nameCell.setCellStyle(getFirstCell());
			
			Cell middleCell1 = row.createCell(2);
			middleCell1.setCellStyle(getMiddleCell());
			
			Cell middleCell2 = row.createCell(3);
			middleCell2.setCellStyle(getMiddleCell());
			
			Cell middleCell3 = row.createCell(4);
			middleCell3.setCellStyle(getMiddleCell());
			
			Cell middleCell4 = row.createCell(5);
			middleCell4.setCellStyle(getMiddleCell());
			
			Cell policyCell = row.createCell(6);
			policyCell.setCellValue(agentCommission.getPolicyNo());
			policyCell.setCellStyle(getFirstCell());
			
			Cell middleCell5 = row.createCell(7);
			middleCell5.setCellStyle(getMiddleCell());
			
			Cell middleCell6 = row.createCell(8);
			middleCell6.setCellStyle(getMiddleCell());
			
			Cell middleCell7 = row.createCell(9);
			middleCell7.setCellStyle(getMiddleCell());
			
			Cell middleCell8 = row.createCell(10);
			middleCell8.setCellStyle(getMiddleCell());
			
			Cell middleCell9 = row.createCell(11);
			middleCell9.setCellStyle(getMiddleCell());
			
			Cell chalanCell = row.createCell(12);
			chalanCell.setCellValue(agentCommission.getChallanNo());
			chalanCell.setCellStyle(getFirstCell());
			
			Cell middleCell10 = row.createCell(13);
			middleCell10.setCellStyle(getMiddleCell());
			
			Cell middleCell11 = row.createCell(14);
			middleCell11.setCellStyle(getMiddleCell());
			
			Cell commissionCell = row.createCell(15);
			commissionCell.setCellValue(agentCommission.getCommission());
			commissionCell.setCellStyle(getFirstCell());
			commissionCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
			commissionCell.getCellStyle().setDataFormat(format.getFormat(NUMBER_FORMAT));
			totalCommissionAmt +=agentCommission.getCommission();
			
			Cell middleCell12 = row.createCell(16);
			middleCell12.setCellStyle(getMiddleCell());
			
			Cell middleCell13 = row.createCell(17);
			middleCell13.setCellStyle(getLastCell());			
			
		}
		
		i=i+1;
		Row row = sheet.createRow(i);
		sheet.addMergedRegion(new CellRangeAddress(i, i, 1, 14));
		sheet.addMergedRegion(new CellRangeAddress(i,i,15,17));
		Cell cell1 = row.createCell(1);
		cell1.setCellValue("Total");
		cell1.setCellStyle(getFirstCell());
		cell1.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
		for(int j=2;j<=13;j++){
			Cell cell2 = row.createCell(j);
			cell2.setCellStyle(getMiddleCell());
		}
		Cell totalCell = row.createCell(14);
		totalCell.setCellStyle(getLastCell());
		
		Cell totalValueCell = row.createCell(15);
		totalValueCell.setCellValue(totalCommissionAmt);
		totalValueCell.setCellStyle(getFirstCell());
		totalValueCell.getCellStyle().setDataFormat(format.getFormat(NUMBER_FORMAT));
		totalValueCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);
		
		Cell cell3 = row.createCell(16);
		cell3.setCellStyle(getMiddleCell());
		
		Cell cell4 = row.createCell(17);
		cell4.setCellStyle(getLastCell());
		
		wb.setPrintArea(0, 0,18, 0, i);
		wb.write(op);
		op.flush();
		op.close();				
	}

}
