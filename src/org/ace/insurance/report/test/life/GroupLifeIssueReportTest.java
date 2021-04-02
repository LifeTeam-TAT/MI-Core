package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class GroupLifeIssueReportTest {
	private static Logger logger = LogManager.getLogger(GroupLifeIssueReportTest.class);
	private static ILifePolicyService lifePolicyService;
	private static IPaymentService paymentService;

	@BeforeClass
	public static void init() {
		logger.info("GroupLifeIssueReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
		paymentService = (IPaymentService) factory.getBean("PaymentService");
		logger.info("GroupLifeIssueReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(GroupLifeIssueReportTest.class.getName());
	}

	// @Test
	public void generateLifePolicyIssueSingle() throws Exception {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo("GGLG/1307/0000000002/HO");
		PaymentDTO paymentDTO = new PaymentDTO(paymentService.findByProposal(lifePolicy.getLifeProposal().getId(), PolicyReferenceType.GROUP_LIFE_POLICY, true));

		Map coverMap = new HashMap();
		coverMap.put("policyNo", lifePolicy.getPolicyNo());
		coverMap.put("customerName", lifePolicy.getCustomerName());
		coverMap.put("totalSumInsured", lifePolicy.getTotalSumInsured());
		InputStream coverIS = new FileInputStream("report-template/life/lifePolicyIssueCover.jrxml");
		JasperReport coverJR = JasperCompileManager.compileReport(coverIS);
		JasperPrint coverPrint = JasperFillManager.fillReport(coverJR, coverMap, new JREmptyDataSource());

		Map paramIssueDetails = new HashMap();
		paramIssueDetails.put("policyNo", lifePolicy.getPolicyNo());
		paramIssueDetails.put("receiptNo", paymentDTO.getReceiptNo());
		paramIssueDetails.put("commenmanceDate", lifePolicy.getCommenmanceDate());
		if (lifePolicy.getAgent() != null) {
			paramIssueDetails.put("agent", lifePolicy.getAgent().getName() + "(" + lifePolicy.getAgent().getCodeNo() + ")");
		} else {
			paramIssueDetails.put("agent", "");
		}
		paramIssueDetails.put("customerName", lifePolicy.getCustomerName());
		paramIssueDetails.put("organizationName", lifePolicy.getOrganization().getName());
		paramIssueDetails.put("customerAddress", lifePolicy.getCustomerAddress());
		paramIssueDetails.put("totalInsuredPerson", lifePolicy.getInsuredPersonInfo().size());
		paramIssueDetails.put("totalSumInsured", lifePolicy.getTotalSumInsured());
		paramIssueDetails.put("startDate", lifePolicy.getActivedPolicyStartDate());
		paramIssueDetails.put("endDate", lifePolicy.getActivedPolicyEndDate());
		paramIssueDetails.put("periodYears", lifePolicy.getPeriodMonth());
		paramIssueDetails.put("totalPremium", lifePolicy.getTotalPremium());

		InputStream policyIS = new FileInputStream("report-template/life/groupLifePolicyIssue.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyPrint = JasperFillManager.fillReport(policyJR, paramIssueDetails, new JREmptyDataSource());

		Map insuredPesronMap = new HashMap();
		insuredPesronMap.put("policyNo", lifePolicy.getPolicyNo());
		insuredPesronMap.put("customerName", lifePolicy.getCustomerName());
		lifePolicy.getInsuredPersonInfo().get(0).getName().setFirstName("Zaw Than OO Zaw Than OO Zaw Than OO");
		lifePolicy.getInsuredPersonInfo().get(0).getPolicyInsuredPersonBeneficiariesList().get(0).getName().setFirstName("Zaw Than OO Zaw Than OO Zaw Than OO");
		insuredPesronMap.put("listDataSource", new JRBeanCollectionDataSource(lifePolicy.getInsuredPersonInfo()));
		InputStream insuredPersonIS = new FileInputStream("report-template/life/groupLifePolicyInsuredPerson.jrxml");
		JasperReport insuredPersonJR = JasperCompileManager.compileReport(insuredPersonIS);
		JasperPrint insuredPersonPrint = JasperFillManager.fillReport(insuredPersonJR, insuredPesronMap, new JREmptyDataSource());

		Map insuredPesronMap_2 = new HashMap();
		insuredPesronMap_2.put("policyNo", lifePolicy.getPolicyNo());
		insuredPesronMap_2.put("customerName", lifePolicy.getCustomerName());
		insuredPesronMap_2.put("listDataSource", new JRBeanCollectionDataSource(lifePolicy.getInsuredPersonInfo()));
		InputStream insuredPersonIS_2 = new FileInputStream("report-template/life/lifePolicyInsuredPerson.jrxml");
		JasperReport insuredPersonJR_2 = JasperCompileManager.compileReport(insuredPersonIS_2);
		JasperPrint insuredPersonPrint_2 = JasperFillManager.fillReport(insuredPersonJR_2, insuredPesronMap_2, new JREmptyDataSource());

		Map benfMap = new HashMap();
		benfMap.put("policyNo", lifePolicy.getPolicyNo());
		benfMap.put("listDataSource", new JRBeanCollectionDataSource(lifePolicy.getInsuredPersonInfo()));
		InputStream beneficiariesIS = new FileInputStream("report-template/life/groupLifePolicyBeneficiaries.jrxml");
		JasperReport beneficiariesISJR = JasperCompileManager.compileReport(beneficiariesIS);
		JasperPrint beneficiariesISPrint = JasperFillManager.fillReport(beneficiariesISJR, benfMap, new JREmptyDataSource());

		List jpList = new ArrayList();
		jpList.add(coverPrint);
		jpList.add(policyPrint);
		jpList.add(insuredPersonPrint);
		jpList.add(insuredPersonPrint_2);
		jpList.add(beneficiariesISPrint);
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/groupLifePolicyIssue.pdf"));
		exporter.exportReport();
	}

	@Test
	public void prepareGenerateLifePolicyIssueSingle() throws Exception {

		Map coverMap = new HashMap();
		coverMap.put("policyNo", "policyNo");
		coverMap.put("customerName", "customerName");
		coverMap.put("totalSumInsured", 1000.0);
		InputStream coverIS = new FileInputStream("report-template/life/lifePolicyIssueCover.jrxml");
		JasperReport coverJR = JasperCompileManager.compileReport(coverIS);
		JasperPrint coverPrint = JasperFillManager.fillReport(coverJR, coverMap, new JREmptyDataSource());

		Map paramIssueDetails = new HashMap();
		paramIssueDetails.put("policyNo", "policyNo");
		paramIssueDetails.put("receiptNo", "receiptNo");
		paramIssueDetails.put("commenmanceDate", new Date());
		paramIssueDetails.put("agent", "agent");
		paramIssueDetails.put("customerName", "customerName");
		paramIssueDetails.put("organizationName", "organizationName");
		paramIssueDetails.put("customerAddress", "customerAddress");
		paramIssueDetails.put("totalInsuredPerson", 2);
		paramIssueDetails.put("totalSumInsured", 1000.0);
		paramIssueDetails.put("startDate", new Date());
		paramIssueDetails.put("endDate", new Date());
		paramIssueDetails.put("periodYears", 1);
		paramIssueDetails.put("totalPremium", 1000.0);

		InputStream policyIS = new FileInputStream("report-template/life/groupLifePolicyIssue.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyPrint = JasperFillManager.fillReport(policyJR, paramIssueDetails, new JREmptyDataSource());

		Map insuredPesronMap = new HashMap();
		insuredPesronMap.put("policyNo", "policyNo");
		insuredPesronMap.put("customerName", "customerName");

		List<PolicyInsuredPerson> policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		PolicyInsuredPerson policyInsuredPerson = new PolicyInsuredPerson();
		Name name = new Name();
		name.setFirstName("firstName");
		policyInsuredPerson.setName(name);
		policyInsuredPerson.setIdNo("idNo");
		policyInsuredPerson.setFatherName("fatherName");
		Occupation occupation = new Occupation();
		occupation.setName("Occupation Name");
		policyInsuredPerson.setOccupation(occupation);
		policyInsuredPerson.setDateOfBirth(new Date());
		policyInsuredPerson.setAge(25);
		policyInsuredPerson.setSumInsured(1000.0);
		policyInsuredPerson.setPremium(100.0);
		List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		PolicyInsuredPersonBeneficiaries personBeneficiaries = new PolicyInsuredPersonBeneficiaries();
		personBeneficiaries.setName(name);
		personBeneficiaries.setPercentage(100f);
		RelationShip relationship = new RelationShip();
		relationship.setName("relationShipName");
		personBeneficiaries.setRelationship(relationship);
		personBeneficiaries.setIdNo("idNo");
		policyInsuredPersonBeneficiariesList.add(personBeneficiaries);
		policyInsuredPerson.setPolicyInsuredPersonBeneficiariesList(policyInsuredPersonBeneficiariesList);

		policyInsuredPersonList.add(policyInsuredPerson);

		insuredPesronMap.put("listDataSource", new JRBeanCollectionDataSource(policyInsuredPersonList));
		InputStream insuredPersonIS = new FileInputStream("report-template/life/groupLifePolicyInsuredPerson.jrxml");
		JasperReport insuredPersonJR = JasperCompileManager.compileReport(insuredPersonIS);
		JasperPrint insuredPersonPrint = JasperFillManager.fillReport(insuredPersonJR, insuredPesronMap, new JREmptyDataSource());

		Map insuredPesronMap_2 = new HashMap();
		insuredPesronMap_2.put("policyNo", "policyNo");
		insuredPesronMap_2.put("customerName", "customerName");
		insuredPesronMap_2.put("listDataSource", new JRBeanCollectionDataSource(policyInsuredPersonList));
		InputStream insuredPersonIS_2 = new FileInputStream("report-template/life/lifePolicyInsuredPerson.jrxml");
		JasperReport insuredPersonJR_2 = JasperCompileManager.compileReport(insuredPersonIS_2);
		JasperPrint insuredPersonPrint_2 = JasperFillManager.fillReport(insuredPersonJR_2, insuredPesronMap_2, new JREmptyDataSource());

		Map benfMap = new HashMap();
		benfMap.put("policyNo", "policyNo");
		benfMap.put("listDataSource", new JRBeanCollectionDataSource(policyInsuredPersonList));
		InputStream beneficiariesIS = new FileInputStream("report-template/life/groupLifePolicyBeneficiaries.jrxml");
		JasperReport beneficiariesISJR = JasperCompileManager.compileReport(beneficiariesIS);
		JasperPrint beneficiariesISPrint = JasperFillManager.fillReport(beneficiariesISJR, benfMap, new JREmptyDataSource());

		List jpList = new ArrayList();
		jpList.add(coverPrint);
		jpList.add(policyPrint);
		jpList.add(insuredPersonPrint);
		jpList.add(insuredPersonPrint_2);
		jpList.add(beneficiariesISPrint);
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/groupLifePolicyIssue.pdf"));
		exporter.exportReport();
	}
}
