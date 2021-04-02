package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.junit.Test;

public class PublicLifeIssueReportTest {
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(PublicLifeIssueReportTest.class.getName());
	}

	// @Test
	public void generateLifePolicyIssueSingle() throws Exception {

		final String templateFileName2 = "lifePolicyIssue.jrxml";
		final String templatePath2 = "D:/dev/workspace/insurance-cus/ggip/report-template/" + templateFileName2;
		final String outputFileName = "lifePolicyIssue.pdf";
		final String outputFilePath = "D:/temp/" + outputFileName;
		List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		Map coverMap = new HashMap();
		coverMap.put("policyNo", "CASH/1208/0000000001/HO");
		coverMap.put("customerName", "Hein Soe");
		coverMap.put("totalSumInsured", 5000000.00);
		InputStream inputStream1 = new FileInputStream("report-template/life/lifePolicyIssueCover.jrxml");
		JasperReport jreport1 = JasperCompileManager.compileReport(inputStream1);
		JasperPrint jprint1 = JasperFillManager.fillReport(jreport1, coverMap, new JREmptyDataSource());

		Map paramIssueDetails = new HashMap();
		paramIssueDetails.put("policyNo", "CASH/1208/0000000001/HO");
		paramIssueDetails.put("receiptNo", "GGLE/1208/0000000001/HO");
		paramIssueDetails.put("commenmanceDate", new Date());
		paramIssueDetails.put("agent", "U Aung Thaw Mann Maung" + "LIC0001");
		paramIssueDetails.put("productName", "PUBLIC LIFE");
		paramIssueDetails.put("periodYears", 10);
		paramIssueDetails.put("sumInsured", 5000000.00);
		paramIssueDetails.put("customerName", "Daw May Tha Zin Phoo Nygon Hmwe");
		paramIssueDetails.put("occupation", "Programmer");
		paramIssueDetails.put("customerAddress", "(CC/2/63),58*59 Vs Bayintnaung/ Anawyahtar Between, Kantharyar Qtr, Mandalay, Myanmar.");
		paramIssueDetails.put("ageForNextYear", 28);
		paramIssueDetails.put("totalPremium", 513150.00);
		paramIssueDetails.put("totalTermPremium", 256575.00);
		paramIssueDetails.put("paymentType", 6);
		paramIssueDetails.put("endDate", new Date(2013, 12, 30));
		paramIssueDetails.put("lastPaymentDate", new Date(2013, 8, 12));
		List<PolicyInsuredPersonBeneficiaries> benfList = populateBeneficiaries();
		if (benfList.size() > 2) {
			paramIssueDetails.put("policyInsuredPersonBeneficiariesList", null);
		} else {
			paramIssueDetails.put("policyInsuredPersonBeneficiariesList", benfList);
		}
		paramIssueDetails.put("timeSlotList", populateTimeSlots());

		InputStream inputStream2 = new FileInputStream("report-template/life/publicLifePolicyIssue.jrxml");
		JasperReport jreport2 = JasperCompileManager.compileReport(inputStream2);
		JasperPrint jprint2 = JasperFillManager.fillReport(jreport2, paramIssueDetails, new JREmptyDataSource());
		List pages_2 = jprint2.getPages();
		JRPrintPage object_2 = (JRPrintPage) pages_2.get(0);
		jprint1.addPage(1, object_2);

		if (benfList.size() > 2) {
			Map paramMap = new HashMap();
			paramMap.put("policyNo", "CASH/1208/0000000001/HO");
			paramMap.put("idNo", "asfdasfasf");
			paramMap.put("insuredPersonName", "Zaw Than Oo" + " - LIC0001");
			paramMap.put("listDataSource", new JRBeanCollectionDataSource(benfList));
			InputStream inputStream3 = new FileInputStream("report-template/life/lifePolicyBeneficiaries.jrxml");
			JasperReport jreport3 = JasperCompileManager.compileReport(inputStream3);
			JasperPrint jprint3 = JasperFillManager.fillReport(jreport3, paramMap, new JREmptyDataSource());
			List pages_3 = jprint3.getPages();
			JRPrintPage object_3 = (JRPrintPage) pages_3.get(0);
			jprint1.addPage(2, object_3);
		}
		JasperExportManager.exportReportToPdfFile(jprint1, "D:/temp/publicLifePolicyIssueCover.pdf");
	}

	@Test
	public void prepareGenerateLifePolicyIssueSingle() throws Exception {
		final String templateFileName2 = "lifePolicyIssue.jrxml";
		final String templatePath2 = "D:/dev/workspace/insurance-cus/ggip/report-template/" + templateFileName2;
		final String outputFileName = "lifePolicyIssue.pdf";
		final String outputFilePath = "D:/temp/" + outputFileName;
		List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		Map coverMap = new HashMap();
		coverMap.put("policyNo", "policyNo");
		coverMap.put("customerName", "customerName");
		coverMap.put("totalSumInsured", 1000000.00);
		InputStream inputStream1 = new FileInputStream("report-template/life/lifePolicyIssueCover.jrxml");
		JasperReport jreport1 = JasperCompileManager.compileReport(inputStream1);
		JasperPrint jprint1 = JasperFillManager.fillReport(jreport1, coverMap, new JREmptyDataSource());

		Map paramIssueDetails = new HashMap();
		paramIssueDetails.put("policyNo", "policyNo");
		paramIssueDetails.put("receiptNo", "receiptNo");
		paramIssueDetails.put("commenmanceDate", new Date());
		paramIssueDetails.put("agent", "agent");
		paramIssueDetails.put("productName", "PUBLIC LIFE");
		paramIssueDetails.put("periodYears", 10);
		paramIssueDetails.put("sumInsured", 5000000.00);

		paramIssueDetails.put("customerName", "customerName");
		paramIssueDetails.put("occupation", "occupation");
		paramIssueDetails.put("customerAddress", "customerAddress");
		paramIssueDetails.put("ageForNextYear", 28);
		paramIssueDetails.put("totalPremium", 1000.0);
		paramIssueDetails.put("totalTermPremium", 1000.0);
		paramIssueDetails.put("paymentType", 6);
		paramIssueDetails.put("endDate", new Date());
		paramIssueDetails.put("lastPaymentDate", new Date());

		List<PolicyInsuredPersonBeneficiaries> benfList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficy = new PolicyInsuredPersonBeneficiaries();
		policyInsuredPersonBeneficy.setAge(1);
		policyInsuredPersonBeneficy.setBeneficiaryNo("beneficiaryNo");
		policyInsuredPersonBeneficy.setGender(Gender.MALE);
		policyInsuredPersonBeneficy.setIdNo("idNo");
		policyInsuredPersonBeneficy.setIdType(IdType.NRCNO);
		Name name = new Name();
		name.setFirstName("firstName");
		policyInsuredPersonBeneficy.setName(name);
		policyInsuredPersonBeneficy.setPercentage(100f);
		RelationShip relationShip = new RelationShip();
		relationShip.setName("RelationShipName");
		policyInsuredPersonBeneficy.setRelationship(relationShip);
		benfList.add(policyInsuredPersonBeneficy);

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(new Date());

		paramIssueDetails.put("policyInsuredPersonBeneficiariesList", benfList);
		paramIssueDetails.put("timeSlotList", dateList);

		InputStream inputStream2 = new FileInputStream("report-template/life/publicLifePolicyIssue.jrxml");
		JasperReport jreport2 = JasperCompileManager.compileReport(inputStream2);
		JasperPrint jprint2 = JasperFillManager.fillReport(jreport2, paramIssueDetails, new JREmptyDataSource());
		List pages_2 = jprint2.getPages();
		JRPrintPage object_2 = (JRPrintPage) pages_2.get(0);
		jprint1.addPage(1, object_2);

		PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficy2 = new PolicyInsuredPersonBeneficiaries();
		policyInsuredPersonBeneficy2.setAge(1);
		policyInsuredPersonBeneficy2.setBeneficiaryNo("beneficiaryNo");
		policyInsuredPersonBeneficy2.setGender(Gender.MALE);
		policyInsuredPersonBeneficy2.setIdNo("idNo");
		policyInsuredPersonBeneficy2.setIdType(IdType.NRCNO);
		policyInsuredPersonBeneficy2.setName(name);
		policyInsuredPersonBeneficy2.setPercentage(100f);
		policyInsuredPersonBeneficy2.setRelationship(relationShip);
		benfList.add(policyInsuredPersonBeneficy2);

		Map paramMap = new HashMap();
		paramMap.put("policyNo", "policyNo");
		paramMap.put("idNo", "idNo");
		paramMap.put("insuredPersonName", "insuredPersonName");
		paramMap.put("listDataSource", new JRBeanCollectionDataSource(benfList));
		InputStream inputStream3 = new FileInputStream("report-template/life/lifePolicyBeneficiaries.jrxml");
		JasperReport jreport3 = JasperCompileManager.compileReport(inputStream3);
		JasperPrint jprint3 = JasperFillManager.fillReport(jreport3, paramMap, new JREmptyDataSource());
		List pages_3 = jprint3.getPages();
		JRPrintPage object_3 = (JRPrintPage) pages_3.get(0);
		jprint1.addPage(2, object_3);

		JasperExportManager.exportReportToPdfFile(jprint1, "D:/temp/publicLifePolicyIssueCover.pdf");
	}

	private List<Date> populateTimeSlots() {
		List<Date> timeSlotList = new ArrayList<Date>();
		timeSlotList.add(Utils.getDate("20-01-2013"));
		timeSlotList.add(Utils.getDate("20-04-2013"));
		timeSlotList.add(Utils.getDate("20-08-2013"));
		timeSlotList.add(Utils.getDate("20-012-2013"));
		return timeSlotList;
	}

	private List<PolicyInsuredPersonBeneficiaries> populateBeneficiaries() {
		List<PolicyInsuredPersonBeneficiaries> ret = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		PolicyInsuredPersonBeneficiaries person1 = populateBeneficiary("U Aung", "Hla", 100f, "FATHER", "Arr Pu Kyii", "8/Ma Ka Na (N) 123456", 26);
		PolicyInsuredPersonBeneficiaries person2 = populateBeneficiary("Mg Zaw", "Htun", 90f, "FATHER", "Arr Pu Kyii", "8/Ma Ka Na (N) 123456", 26);
		person2.setRelationship(null);
		PolicyInsuredPersonBeneficiaries person3 = populateBeneficiary("Mg Zaw Zaw Aung Zaw", "Htun", 90f, "FATHER", "Arr Pu Kyii", "8/Ma Ka Na (N) 123456", 26);
		ret.add(person1);
		// ret.add(person2);
		ret.add(person3);
		// ret.add(person3);
		// ret.add(person3);
		// ret.add(person3);
		return ret;
	}

	private PolicyInsuredPersonBeneficiaries populateBeneficiary(String firstName, String lastName, float percentage, String relationshipName, String relationshipDescription,
			String idno, int age) {
		PolicyInsuredPersonBeneficiaries person = new PolicyInsuredPersonBeneficiaries();
		Name name = new Name();
		name.setFirstName(firstName);
		name.setLastName(lastName);

		RelationShip relationship = new RelationShip();
		relationship.setName(relationshipName);
		relationship.setDescription(relationshipDescription);

		person.setName(name);
		person.setPercentage(percentage);
		person.setRelationship(relationship);
		person.setIdNo(idno);
		person.setAge(age);
		return person;
	}
}
