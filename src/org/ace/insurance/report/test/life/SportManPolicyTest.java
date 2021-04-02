package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.web.common.ntw.mym.AbstractMynNumConvertor;
import org.ace.insurance.web.common.ntw.mym.DefaultConvertor;
import org.ace.insurance.web.common.ntw.mym.NumberToNumberConvertor;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SportManPolicyTest {
	private static ITownshipService townshipService;
	private static ILifePolicyService lifePolicyService;

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(SportManPolicyTest.class.getName());
	}

	@BeforeClass
	public static void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		townshipService = (ITownshipService) factory.getBean("TownshipService");
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");

	}

	@SuppressWarnings("unchecked")
	@Test
	public void generateSportManPolicy() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyById("id");
		PaymentDTO paymentDTO = new PaymentDTO();
		try {
			List jpList = new ArrayList();
			Map coverMap = new HashMap();
			coverMap.put("policyNo", lifePolicy.getPolicyNo());
			coverMap.put("customerName", lifePolicy.getCustomerName());
			coverMap.put("totalSumInsured", lifePolicy.getSumInsured());
			InputStream inputStream1 = new FileInputStream("report-template/life/lifePolicyIssueCover.jrxml");
			JasperReport jreport1 = JasperCompileManager.compileReport(inputStream1);
			JasperPrint jprint1 = JasperFillManager.fillReport(jreport1, coverMap, new JREmptyDataSource());
			jpList.add(jprint1);

			Map paramIssueDetails = new HashMap();
			paramIssueDetails.put("policyNo", lifePolicy.getPolicyNo());
			paramIssueDetails.put("receiptNo", paymentDTO.getReceiptNo());
			Agent agent = lifePolicy.getAgent();

			/* Myanmar */
			AbstractMynNumConvertor convertor = new DefaultConvertor();
			paramIssueDetails.put("myanSumInsured", convertor.getName(lifePolicy.getTotalSumInsured()));
			paramIssueDetails.put("myanPremium", convertor.getName(lifePolicy.getTotalPremium()));

			paramIssueDetails.put("myanSumInsuredNum", NumberToNumberConvertor.getMyanmarNumber(lifePolicy.getTotalSumInsured(), true));
			paramIssueDetails.put("myanPremiumNum", NumberToNumberConvertor.getMyanmarNumber(lifePolicy.getTotalPremium(), true));
			/*
			 * if (agent != null) { paramIssueDetails.put("agent",
			 * agent.getName() + " (" + agent.getLiscenseNo() + ")"); } else {
			 * paramIssueDetails.put("agent", ""); }
			 * paramIssueDetails.put("productName", lifePolicy
			 * .getInsuredPersonInfo().get(0).getProduct().getName());
			 * paramIssueDetails.put("periodYears", lifePolicy
			 * .getInsuredPersonInfo().get(0).getPeriodYears());
			 * paramIssueDetails.put("occupation", lifePolicy
			 * .getInsuredPersonInfo().get(0).getOccupation() == null ? "" :
			 * lifePolicy.getInsuredPersonInfo().get(0).getOccupation()
			 * .getName()); paramIssueDetails.put("totalTermPremium",
			 * lifePolicy.getTotalTermPremium());
			 * paramIssueDetails.put("paymentType", lifePolicy.getPaymentType()
			 * .getMonth()); paramIssueDetails.put("lastPaymentDate", lifePolicy
			 * .getInsuredPersonInfo().get(0).getLastPaymentDate());
			 * paramIssueDetails.put("timeSlotList", lifePolicy
			 * .getInsuredPersonInfo().get(0).getTimeSlotList());
			 */

			paramIssueDetails.put("sumInsured", lifePolicy.getSumInsured());
			paramIssueDetails.put("customerName", lifePolicy.getInsuredPersonInfo().get(0).getFullName());
			paramIssueDetails.put("customerAddress", lifePolicy.getInsuredPersonInfo().get(0).getResidentAddress().getFullResidentAddress());
			paramIssueDetails.put("age", lifePolicy.getInsuredPersonInfo().get(0).getAge());
			paramIssueDetails.put("totalPremium", lifePolicy.getTotalPremium());
			paramIssueDetails.put("commenmanceDate", lifePolicy.getCommenmanceDate());
			paramIssueDetails.put("endDate", lifePolicy.getActivedPolicyEndDate());
			paramIssueDetails.put("startDate", lifePolicy.getActivedPolicyStartDate());
			paramIssueDetails.put("regNo", lifePolicy.getPolicyInsuredPersonList().get(0).getIdNo());
			paramIssueDetails.put("typesOfSport", lifePolicy.getPolicyInsuredPersonList().get(0).getTypesOfSport().getName());

			List<PolicyInsuredPersonBeneficiaries> benfList = lifePolicy.getInsuredPersonInfo().get(0).getPolicyInsuredPersonBeneficiariesList();

			if (benfList.size() > 1) {
				paramIssueDetails.put("policyInsuredPersonBeneficiariesList", null);
			} else {
				// paramIssueDetails.put("policyInsuredPersonBeneficiariesList",
				// benfList);
				paramIssueDetails.put("befName", benfList.get(0).getFullName());
				paramIssueDetails.put("befAge", benfList.get(0).getAge());
				paramIssueDetails.put("befRegNo", benfList.get(0).getIdNo());
				paramIssueDetails.put("befAddress", benfList.get(0).getResidentAddress().getFullResidentAddress());
				paramIssueDetails.put("befRelationship", benfList.get(0).getRelationship().getName());
			}

			InputStream inputStream2 = new FileInputStream("report-template/life/sportManPolicyIssue.jrxml");
			JasperReport jreport2 = JasperCompileManager.compileReport(inputStream2);
			JasperPrint jprint2 = JasperFillManager.fillReport(jreport2, paramIssueDetails, new JREmptyDataSource());
			jpList.add(jprint2);

			if (benfList.size() > 1) {
				Map paramMap = new HashMap();
				paramMap.put("policyNo", lifePolicy.getPolicyNo());
				paramMap.put("idNo", lifePolicy.getInsuredPersonInfo().get(0).getIdNo());
				paramMap.put("insuredPersonName", lifePolicy.getCustomerName());
				paramMap.put("nrc", lifePolicy.getInsuredPersonInfo().get(0).getIdNo());
				paramMap.put("listDataSource", new JRBeanCollectionDataSource(benfList));
				InputStream inputStream3 = new FileInputStream("report-template/life/lifePolicyBeneficiaries.jrxml");
				JasperReport jreport3 = JasperCompileManager.compileReport(inputStream3);
				JasperPrint jprint3 = JasperFillManager.fillReport(jreport3, paramMap, new JREmptyDataSource());
				jpList.add(jprint3);
			}
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
			FileHandler.forceMakeDirectory("D:/temp/");
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/lifePolicyBeneficiaries.pdf"));
			exporter.exportReport();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate document public life policy issue", e);
		}
	}
}
