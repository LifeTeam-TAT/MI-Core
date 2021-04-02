//package org.ace.insurance.report.test.life;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//
//import org.ace.insurance.common.ErrorCode;
//import org.ace.insurance.common.KeyFactorIDConfig;
//import org.ace.insurance.common.WorkflowTask;
//import org.ace.insurance.life.policy.LifePolicy;
//import org.ace.insurance.life.policy.PolicyInsuredPerson;
//import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
//import org.ace.insurance.life.proposal.LifeProposal;
//import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
//import org.ace.insurance.web.common.DocumentBuilder;
//import org.ace.insurance.web.util.FileHandler;
//import org.ace.insurance.workflow.WorkFlowHistory;
//import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
//import org.ace.java.component.SystemException;
//import org.ace.java.web.common.BaseBean;
//import org.ace.java.web.common.MessageId;
//import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.primefaces.context.RequestContext;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class LifePolicyLedgerReportTest extends BaseBean {
//	private static Logger logger = LogManager.getLogger(LifePolicy.class);
//	private static LifeProposal lifeProposal;
//	private static ILifePolicyService lifePolicyService;
//	private static ILifeProposalService lifeProposalService;
//	private static IWorkFlowService workFlowService;
//
//	@BeforeClass
//	public static void init() {
//		logger.info("LifePolicyLedgerReportTest is started.........................................");
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
//		BeanFactory factory = context;
//		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
//		workFlowService = (IWorkFlowService) factory.getBean("WorkFlowService");
//		logger.info("LifePolicyLedgerReportTest instance has been loaded.");
//	}
//
//	protected String getWebRootPath() {
//		Object context = getFacesContext().getExternalContext().getContext();
//		String systemPath = ((ServletContext) context).getRealPath("/");
//		return systemPath;
//	}
//
//	public static void main(String[] args) {
//		org.junit.runner.JUnitCore.main(LifePolicyLedgerReportTest.class.getName());
//	}
//
//	String pdfDirPath = "";
//	String fileName = "";
//	String message = "";
//
//	public String getReportStream() {
//		return pdfDirPath + fileName;
//	}
//
//	// @Test
//	public void generateLifePolicyLedger() {
//
//		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById("ISLIF001HO000000001218062013");
//		System.out.println(lifeProposal.getCustomerId() + "*****");
//		if (allowToPrint(lifeProposal, WorkflowTask.ISSUING, WorkflowTask.PAYMENT)) {
//			LifePolicy lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId("ISLIF001HO000000001218062013");
//			if (checkGroupLife(lifePolicy)) {
//				// TODO : to generate GroupLifePolicyLedger
//			} else {
//				Date surveydate = lifeProposalService.findSurveyByProposalId("ISLIF001HO000000001218062013").getDate();
//
//				String reportName = "LifePolicyLedger";
//				pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
//				String dirPath = getWebRootPath() + pdfDirPath;
//				fileName = reportName + ".pdf";
//
//				DocumentBuilder.generatePublicLifePolicyLedger(lifePolicy, surveydate, dirPath, fileName);
//			}
//			RequestContext.getCurrentInstance().execute("documentPrintDailog.show()");
//		} else {
//			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.CONFIRMATION.getLabel().toLowerCase());
//		}
//	}
//
//	@Test
//	public void prepareGenerateLifePolicyLedger() {
//		try {
//			Map paramMap = new HashMap();
//			paramMap.put("policyNo", "policyNo");
//			paramMap.put("parameter1", "parameter1");
//
//			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyLedgerReport.jrxml");
//			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
//			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
//			FileHandler.forceMakeDirectory("D:/temp/");
//			String outputFile = "D:/temp/lifePolicyLedgerReportTest.pdf";
//			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
//		} catch (Exception e) {
//			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifePolicyLedgerReport", e);
//		}
//
//	}
//
//	// @Test
//	public boolean allowToPrint(LifeProposal lifeProposal, WorkflowTask... workflowTasks) {
//		List<WorkFlowHistory> wfhList = workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId(), workflowTasks);
//		if (wfhList == null || wfhList.isEmpty()) {
//			RequestContext.getCurrentInstance().execute("informationDialog.show()");
//			return false;
//		} else {
//			this.lifeProposal = lifeProposal;
//			return true;
//		}
//	}
//
//	public boolean checkGroupLife(LifePolicy lifePolicy) {
//		boolean ans = false;
//		for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
//			if (person.getProduct().getId().equals(KeyFactorIDConfig.getGroupLifeId())) {
//				ans = true;
//			}
//		}
//		return ans;
//	}
//
// }
