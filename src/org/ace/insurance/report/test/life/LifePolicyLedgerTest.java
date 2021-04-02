package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.web.common.ntw.eng.AbstractProcessor;
import org.ace.insurance.web.common.ntw.eng.DefaultProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class LifePolicyLedgerTest {
	private static Logger logger = LogManager.getLogger(LifePolicy.class);
	private static ILifePolicyService lifePolicyService;

	@BeforeClass
	public static void init() {
		logger.info("LifePolicyTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
		logger.info("LifePolicyTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePolicyLedgerTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			List<JasperPrint> jpList = new ArrayList<JasperPrint>();
			LifePolicy lifePolicy = lifePolicyService.findLifePolicyById("ISLIF009001000000001101112013");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			PolicyInsuredPerson insuredPerson = lifePolicy.getInsuredPersonInfo().get(0);
			DateTime policyEndDate = new DateTime(lifePolicy.getActivedPolicyEndDate());
			String endYear = String.valueOf(policyEndDate.getYear() + lifePolicy.getPeriodMonth());
			String endMonth = String.valueOf(policyEndDate.getMonthOfYear());
			String endDay = String.valueOf(policyEndDate.getDayOfMonth());

			DateTime policyStartDate = new DateTime(lifePolicy.getActivedPolicyStartDate());
			String startYear = String.valueOf(policyStartDate.getYear());
			String startMonth = String.valueOf(policyStartDate.getMonthOfYear());
			String startDay = String.valueOf(policyStartDate.getDayOfMonth());

			DateTime endDate = new DateTime(lifePolicy.getLastPaymentDate());
			String paymentEndYear = String.valueOf(endDate.getYear());
			String paymentEndMonth = String.valueOf(endDate.getMonthOfYear());
			String paymentEndDay = String.valueOf(endDate.getDayOfMonth());

			/* Eng */
			AbstractProcessor convertor = new DefaultProcessor();
			/* Myanmar */
			// AbstractMynNumConvertor convertor = new DefaultConvertor();
			paramMap.put("endDay", endDay);
			paramMap.put("endMonth", endMonth);
			paramMap.put("endYear", endYear);
			paramMap.put("startDay", startDay);
			paramMap.put("startMonth", startMonth);
			paramMap.put("startYear", startYear);
			paramMap.put("paymentEndDay", paymentEndDay);
			paramMap.put("paymentEndMonth", paymentEndMonth);
			paramMap.put("paymentEndYear", paymentEndYear);
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("period", lifePolicy.getPeriodMonth());
			paramMap.put("agent", lifePolicy.getAgent() == null ? "" : lifePolicy.getAgent().getName() + "(" + lifePolicy.getAgent().getLiscenseNo() + ")");
			paramMap.put("sumInsured", lifePolicy.getTotalSumInsured());
			paramMap.put("myanSumInsured", convertor.getName(lifePolicy.getTotalSumInsured()));
			paramMap.put("customerName", lifePolicy.getCustomer() == null ? lifePolicy.getOrganization().getName() : lifePolicy.getCustomer().getFullName());
			paramMap.put("customerAddress", lifePolicy.getCustomer() == null ? lifePolicy.getOrganization().getFullAddress() : lifePolicy.getCustomer().getFullAddress());
			paramMap.put("age", insuredPerson.getAgeForNextYear() + " Y");
			paramMap.put("submittedDate", lifePolicy.getLifeProposal().getSubmittedDate());
			paramMap.put("surveyDate", new Date());
			paramMap.put("activePolicySD", lifePolicy.getCommenmanceDate());
			paramMap.put("totalPremium", lifePolicy.getTotalPremium());
			paramMap.put("termPremium", lifePolicy.getTotalTermPremium());
			paramMap.put("paymentType", lifePolicy.getPaymentType().getName());
			paramMap.put("occupation",
					lifePolicy.getCustomer() == null ? "" : lifePolicy.getCustomer().getOccupation() == null ? "" : lifePolicy.getCustomer().getOccupation().getName());

			List<PolicyInsuredPersonBeneficiaries> benfList = insuredPerson.getPolicyInsuredPersonBeneficiariesList();
			if (benfList.size() > 2) {
				paramMap.put("TableDataSource", null);
				// paramMap.put("policyInsuredPersonBeneficiariesList", null);
			} else {
				paramMap.put("TableDataSource", new JRBeanCollectionDataSource(insuredPerson.getPolicyInsuredPersonBeneficiariesList()));
			}
			paramMap.put("timeSlotList", lifePolicy.getTimeSlotList());
			// InputStream inputStream2 =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/lifePolicyLedger.jrxml");
			InputStream inputStream2 = new FileInputStream("report-template/life/lifePolicyLedger.jrxml");
			JasperReport jreport2 = JasperCompileManager.compileReport(inputStream2);
			JasperPrint jprint2 = JasperFillManager.fillReport(jreport2, paramMap, new JREmptyDataSource());
			jpList.add(jprint2);

			if (benfList.size() > 2) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("policyNo", lifePolicy.getPolicyNo());
				param.put("insuredPersonName", lifePolicy.getCustomerName());
				param.put("idNo", lifePolicy.getInsuredPersonInfo().get(0).getIdNo());
				System.out.println(">>>>>>>>>" + lifePolicy.getInsuredPersonInfo().get(0).getIdNo());
				param.put("listDataSource", new JRBeanCollectionDataSource(benfList));
				// InputStream inputStream3 =
				// Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/lifePolicyBeneficiariesLedger.jrxml");
				InputStream inputStream3 = new FileInputStream("report-template/life/lifePolicyBeneficiariesLedger.jrxml");
				JasperReport jreport3 = JasperCompileManager.compileReport(inputStream3);
				JasperPrint jprint3 = JasperFillManager.fillReport(jreport3, param, new JREmptyDataSource());
				jpList.add(jprint3);
			}
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/LifePolicyLedger.pdf"));
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {
			List<JasperPrint> jpList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("policyNo", "policyNo");
			paramMap.put("period", 1);
			paramMap.put("agent", "agent");
			paramMap.put("sumInsured", 1000.0);
			paramMap.put("myanSumInsured", "myanSumInsured");
			paramMap.put("customerName", "customerName");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("submittedDate", new Date());
			paramMap.put("surveyDate", new Date());
			paramMap.put("activePolicySD", new Date());
			paramMap.put("totalPremium", 1000.0);
			paramMap.put("termPremium", 1000.0);
			paramMap.put("paymentType", "paymentType");
			paramMap.put("age", "age");
			paramMap.put("occupation", "occupation");
			paramMap.put("endDay", "endDay");
			paramMap.put("endMonth", "endMonth");
			paramMap.put("endYear", "endYear");
			paramMap.put("paymentEndYear", "paymentEndYear");
			paramMap.put("paymentEndMonth", "paymentEndMonth");
			paramMap.put("paymentEndDay", "paymentEndDay");
			paramMap.put("startYear", "startYear");
			paramMap.put("startMonth", "startMonth");
			paramMap.put("startDay", "startDay");

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
			benfList.add(policyInsuredPersonBeneficy);

			List<Date> dateList = new ArrayList<Date>();
			dateList.add(new Date());

			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(benfList));
			paramMap.put("timeSlotList", dateList);
			InputStream inputStream2 = new FileInputStream("report-template/life/lifePolicyLedger.jrxml");
			JasperReport jreport2 = JasperCompileManager.compileReport(inputStream2);
			JasperPrint jprint2 = JasperFillManager.fillReport(jreport2, paramMap, new JREmptyDataSource());
			jpList.add(jprint2);

			PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficy1 = new PolicyInsuredPersonBeneficiaries();
			policyInsuredPersonBeneficy1.setAge(1);
			policyInsuredPersonBeneficy1.setBeneficiaryNo("beneficiaryNo");
			policyInsuredPersonBeneficy1.setGender(Gender.MALE);
			policyInsuredPersonBeneficy1.setIdNo("idNo");
			policyInsuredPersonBeneficy1.setIdType(IdType.NRCNO);
			Name name1 = new Name();
			name1.setFirstName("firstName");
			policyInsuredPersonBeneficy1.setName(name1);
			policyInsuredPersonBeneficy1.setPercentage(100f);
			benfList.add(policyInsuredPersonBeneficy1);

			PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficy2 = new PolicyInsuredPersonBeneficiaries();
			policyInsuredPersonBeneficy2.setAge(1);
			policyInsuredPersonBeneficy2.setBeneficiaryNo("beneficiaryNo");
			policyInsuredPersonBeneficy2.setGender(Gender.MALE);
			policyInsuredPersonBeneficy2.setIdNo("idNo");
			policyInsuredPersonBeneficy2.setIdType(IdType.NRCNO);
			Name name2 = new Name();
			name2.setFirstName("firstName");
			policyInsuredPersonBeneficy2.setName(name2);
			policyInsuredPersonBeneficy2.setPercentage(100f);
			benfList.add(policyInsuredPersonBeneficy2);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("policyNo", "policyNo");
			param.put("insuredPersonName", "insuredPersonName");
			param.put("idNo", "idNo");
			param.put("listDataSource", new JRBeanCollectionDataSource(benfList));
			InputStream inputStream3 = new FileInputStream("report-template/life/lifePolicyBeneficiariesLedger.jrxml");
			JasperReport jreport3 = JasperCompileManager.compileReport(inputStream3);
			JasperPrint jprint3 = JasperFillManager.fillReport(jreport3, param, new JREmptyDataSource());
			jpList.add(jprint3);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/LifePolicyLedger.pdf"));
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
