package org.ace.insurance.report.test.life;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.ace.insurance.common.Utils;
import org.junit.Test;

public class LifeCashReceiptTest {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(LifeCashReceiptTest.class.getName());
    }
    
    @Test
    public void test() {
    	try {
        	Map paramMap = new HashMap();
        	paramMap.put("proposalNo", "FRE/1307/0000000026/HO");
    		paramMap.put("cashReceiptNo","CASH/1309/0000001323/HO");
    		paramMap.put("sumInsured", 100000000.00);
    		paramMap.put("premium", 200000.00);
    		paramMap.put("discountAmount", 0.0);
    		paramMap.put("addOnPremium", 360000.00);
    		paramMap.put("netPremium", 560000.00);
    		paramMap.put("serviceCharges", 0.0);
    		paramMap.put("stempFees", 0.0);
    		paramMap.put("totalAmount", 560000.00);
    		paramMap.put("receiptType", "Cash Receipt");
    		paramMap.put("paymentType", "ANNUAL");
    		paramMap.put("policyType", "LAMPSUM");
    		paramMap.put("confirmDate", Utils.getDate("04-09-2013"));
    		paramMap.put("fromDate", Utils.getDate("04-09-2013"));
    		paramMap.put("toDate", Utils.getDate("04-09-2014"));
    		paramMap.put("agent", "Daw War War Aung (A-1246)");
    		paramMap.put("insuredPerson","Metta Development Foundation");
    		paramMap.put("customerAddress","Room No.1302, Building (12+1), Parami Condomium, 16 ward., HLAING, YANGON,MYANMAR");
    		paramMap.put("insuredPropertyAddress","Room No.1302, Building (12+1), Parami Condomiun, 16 ward., HLAING, YANGON,MYANMAR");
        	InputStream inputStream = new FileInputStream("report-template/life/lifeCashReceipt.jrxml");
    		JasperReport jreport = JasperCompileManager.compileReport(inputStream);
    		JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
    		JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifeCashReceipt.pdf");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
