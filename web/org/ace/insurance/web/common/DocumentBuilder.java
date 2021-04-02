package org.ace.insurance.web.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.common.document.JasperTemplate;
import org.ace.insurance.web.common.myanmarLanguae.MyanmarLanguae;
import org.ace.insurance.web.common.ntw.mym.AbstractMynNumConvertor;
import org.ace.insurance.web.common.ntw.mym.DefaultConvertor;
import org.ace.insurance.web.common.ntw.mym.NumberToNumberConvertor;
import org.ace.insurance.web.manage.life.claim.LifeClaimDischargeFormDTO;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.web.ApplicationSetting;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class DocumentBuilder extends BasicDAO {
	public static void generateCustomerInfo(Customer customer, String dirPath, String fileName) {
		try {
			List<JasperPrint> jlist = new ArrayList<JasperPrint>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", customer.getFullName());
			params.put("dateOfBirth", customer.getDateOfBirth());
			params.put("idNo", customer.getFullIdNo());
			params.put("birthMark", customer.getBirthMark());
			params.put("maritalStatus", customer.getMaritalStatus().getLabel());
			params.put("nationality", customer.getCountry() == null ? "" : customer.getCountry().getName());
			params.put("religion", customer.getReligion() == null ? "" : customer.getReligion().getName());
			params.put("qualification", customer.getQualification() == null ? "" : customer.getQualification().getName());
			params.put("bankBranch", customer.getBankBranch() == null ? "" : customer.getBankBranch().getName());
			params.put("accountNo", customer.getBankAccountNo());
			params.put("industry", customer.getIndustry() == null ? "" : customer.getIndustry().getName());
			params.put("occupation", customer.getOccupation() == null ? "" : customer.getOccupation().getName());
			params.put("salary", customer.getSalary());
			params.put("residentAddress", customer.getFullAddress());
			params.put("officeAddress", customer.getOfficeAddress().getOfficeAddress());
			params.put("parmanentAddress", customer.getPermanentAddress().getFullAddress());
			params.put("mobile", customer.getContentInfo().getMobile());
			params.put("email", customer.getContentInfo().getEmail());
			params.put("fax", customer.getContentInfo().getFax());

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.CUS_INFO);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, params, new JREmptyDataSource());
			if (jprint.getPages().size() > 1) {
				jprint.getPages().remove(1);
			}

			jlist.add(jprint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jlist);
			FileHandler.forceMakeDirectory(dirPath);
			OutputStream outputStream = new FileOutputStream(dirPath + fileName);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			outputStream.close();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate CustomerInfo", e);
		}
	}

	public static void generatePrintSetUpLifePolicy(EndorsementLifePolicyPrint endorsementPolicyPrint, LifeProposal lifeProposal, LifePolicy lifePolicy,
			LifePolicyHistory lifePolicyHistory, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", lifePolicy.getLifeProposal().getProposalNo());
			paramMap.put("extraRegulation", endorsementPolicyPrint.getExtraRegulation());
			paramMap.put("currentDate", org.ace.insurance.common.Utils.getDateFormatString(new Date()));
			paramMap.put("submittedDate", org.ace.insurance.common.Utils.getDateFormatString(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("commenmanceDate", org.ace.insurance.common.Utils.getDateFormatString(lifePolicyHistory.getCommenmanceDate()));
			paramMap.put("policyNo", lifePolicyHistory.getPolicyNo());
			paramMap.put("customerName", lifePolicyHistory.getCustomerName());
			paramMap.put("customerAddress", lifePolicyHistory.getCustomerAddress());
			paramMap.put("endorsementDescription", endorsementPolicyPrint.getEndorsementDescription());
			paramMap.put("endorseChange", endorsementPolicyPrint.getEndorseChange());
			paramMap.put("endorseChangeDetail", endorsementPolicyPrint.getEndorseChangeDetail());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/".concat(JasperTemplate.LIFE_ENDORSE_ISSUE));
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			if (jprint.getPages().size() > 1) {
				jprint.removePage(1);
			}
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate PrintSetUp LifePolicy", e);
		}

	}

	public static void generateLifeDeathClaimAcceptanceLetter(LifeClaim lifeClaim, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("customerName", lifeClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("customerAddress", lifeClaim.getClaimInsuredPerson().getFullResidentAddress());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			String agentName = (lifeClaim.getLifePolicy().getAgent() == null) ? null : lifeClaim.getLifePolicy().getAgent().getFullName();
			paramMap.put("agent", agentName);
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			paramMap.put("liabilitiesAmount", claimAcceptedInfo.getClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getTotalLoanAmount());
			paramMap.put("loanInterest", lifeClaim.getTotalLoanInterest());
			paramMap.put("renewalAmount", lifeClaim.getTotalRenewelAmount());
			paramMap.put("renewalInterest", lifeClaim.getTotalRenewelInterest());
			// paramMap.put("totalAmount", lifeClaim.getTotalNetClaimAmount());
			paramMap.put("serviceCharges", claimAcceptedInfo.getServicesCharges());
			paramMap.put("totalAmount", claimAcceptedInfo.getTotalAmount());
			paramMap.put("publicLife", isPublicLife(lifeClaim));

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_CLAIM_ACCEPT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeClaimAcceptanceLetter", e);
		}
	}

	private static boolean isPublicLife(LifeClaim lifeClaim) {
		if (lifeClaim.getLifePolicy().getPolicyInsuredPersonList().size() == 1) {
			return true;
		}
		return false;
	}

	public static void generateLifeDeathClaimRejectLetter(LifeClaim lifeClaim, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("customerName", lifeClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			if (lifeClaim.getClaimInsuredPerson() != null) {
				paramMap.put("nrc", lifeClaim.getClaimInsuredPerson().getIdNo());
			} else {
				paramMap.put("nrc", "");
			}
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_CLAIM_REJECT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDeathClaimRejectLetter", e);
		}
	}

	public static void generateLifeDisabilityClaimAcceptanceLetter(LifeDisabilityClaim disabilityClaim, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("customerName", disabilityClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("customerAddress", disabilityClaim.getClaimInsuredPerson().getFullResidentAddress());
			paramMap.put("policyNo", disabilityClaim.getLifePolicy().getPolicyNo());
			String agentName = (disabilityClaim.getLifePolicy().getAgent() == null) ? null : disabilityClaim.getLifePolicy().getAgent().getFullName();
			paramMap.put("agent", agentName);
			paramMap.put("claimNo", disabilityClaim.getClaimRequestId());
			paramMap.put("currentDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			paramMap.put("liabilitiesAmount", claimAcceptedInfo.getClaimAmount());
			paramMap.put("loanAmount", disabilityClaim.getTotalLoanAmount());
			paramMap.put("loanInterest", disabilityClaim.getTotalLoanInterest());
			paramMap.put("renewalAmount", disabilityClaim.getTotalRenewelAmount());
			paramMap.put("renewalInterest", disabilityClaim.getTotalRenewelInterest());
			// paramMap.put("totalAmount", lifeClaim.getTotalNetClaimAmount());
			paramMap.put("serviceCharges", claimAcceptedInfo.getServicesCharges());
			paramMap.put("totalAmount", claimAcceptedInfo.getTotalAmount());
			paramMap.put("waitingPeriod", disabilityClaim.getWaitingPeriod());
			// paramMap.put("disabilityClaimType",disabilityClaim.getDisabilityClaimType());
			paramMap.put("publicLife", isPublicLife(disabilityClaim));

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_DIS_CLAIM_ACCEPT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeDisabilityClaimAcceptanceLetter", e);
		}
	}

	public static void generateLifeDisabilityClaimRejectLetter(LifeDisabilityClaim disabilityClaim, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("customerName", disabilityClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("currentDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			if (disabilityClaim.getClaimInsuredPerson() != null) {
				paramMap.put("nrc", disabilityClaim.getClaimInsuredPerson().getIdNo());
			} else {
				paramMap.put("nrc", "");
			}
			paramMap.put("policyNo", disabilityClaim.getLifePolicy().getPolicyNo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_DIS_CLAIM_REJECT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDisabilityClaimRejectLetter", e);
		}
	}

	public static void generateLifeDisabilityClaimCashReceipt(LifeClaim lifeClaim, PaymentDTO payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();

			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				paramMap.put("bankAccountNo", payment.getBankAccountNo());
				paramMap.put("bank", payment.getBank().getName());
				paramMap.put("chequePayment", Boolean.TRUE);
				paramMap.put("receiptType", "Cheque Payment");
				paramMap.put("receiptName", "Payment No.");
			} else {
				paramMap.put("chequePayment", Boolean.FALSE);
				paramMap.put("receiptType", "Cash Payment");
				paramMap.put("receiptName", "Payment No.");
			}
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			paramMap.put("cashReceiptNo", payment.getReceiptNo());
			paramMap.put("currentDate", payment.getConfirmDate());
			String agentName = (lifeClaim.getLifePolicy().getAgent() == null) ? null : lifeClaim.getLifePolicy().getAgent().getFullName();
			paramMap.put("agent", agentName);
			paramMap.put("insuredPerson", lifeClaim.getLifePolicy().getCustomerName());
			paramMap.put("customerAddress", lifeClaim.getLifePolicy().getCustomerAddress());
			paramMap.put("liabilitiesAmount", payment.getClaimAmount());
			paramMap.put("serviceCharges", payment.getServicesCharges());
			paramMap.put("totalAmount", payment.getTotalClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getClaimInsuredPerson().getLoanAmount());
			paramMap.put("loanInterest", payment.getLoanInterest());
			paramMap.put("renewelAmount", lifeClaim.getClaimInsuredPerson().getRenewelAmount());
			paramMap.put("renewelInterest", payment.getRenewalInterest());
			paramMap.put("reportLogo", ApplicationSetting.getReportLogo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_DIS_CLAIM_RECEIPT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);

			jasperListPDFExport(jasperPrintList, dirPath, fileName);
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate FireCashReceipt", e);
		}
	}

	public static void generateLifeDeathClaimCashReceipt(LifeClaim lifeClaim, PaymentDTO payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();

			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				paramMap.put("bankAccountNo", payment.getBankAccountNo());
				paramMap.put("bank", payment.getBank().getName());
				paramMap.put("chequePayment", Boolean.TRUE);
				paramMap.put("receiptType", "Cheque Payment");
				paramMap.put("receiptName", "Payment No.");
			} else {
				paramMap.put("chequePayment", Boolean.FALSE);
				paramMap.put("receiptType", "Cash Payment");
				paramMap.put("receiptName", "Payment No.");
			}
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			paramMap.put("cashReceiptNo", payment.getReceiptNo());
			paramMap.put("currentDate", payment.getConfirmDate());
			String agentName = (lifeClaim.getLifePolicy().getAgent() == null) ? null : lifeClaim.getLifePolicy().getAgent().getFullName();
			paramMap.put("agent", agentName);
			paramMap.put("insuredPerson", lifeClaim.getLifePolicy().getCustomerName());
			paramMap.put("customerAddress", lifeClaim.getLifePolicy().getCustomerAddress());
			paramMap.put("liabilitiesAmount", payment.getClaimAmount());
			paramMap.put("serviceCharges", payment.getServicesCharges());
			paramMap.put("totalAmount", payment.getTotalClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getClaimInsuredPerson().getLoanAmount());
			paramMap.put("loanInterest", payment.getLoanInterest());
			paramMap.put("renewelAmount", lifeClaim.getClaimInsuredPerson().getRenewelAmount());
			paramMap.put("renewelInterest", payment.getRenewalInterest());
			paramMap.put("reportLogo", ApplicationSetting.getReportLogo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_CLAIM_RECEIPT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);

			jasperListPDFExport(jasperPrintList, dirPath, fileName);
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate FireCashReceipt", e);
		}
	}

	public static void generateLifeDisabilityClaimDischargeCertificate(LifeClaimDischargeFormDTO discharge, String dirPath, String fileName) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();

			String presentDate = Utils.formattedDate(discharge.getPresentDate());
			String liabilitiesAmount = Utils.formattedCurrency(discharge.getClaimAmount());
			paramMap.put("policyNo", discharge.getPolicyNo());
			paramMap.put("customerName", discharge.getCustomerName());
			paramMap.put("liabilitiesAmount", liabilitiesAmount);
			paramMap.put("sumInsured", discharge.getSumInsured());
			paramMap.put("presentDate", presentDate);

			paramMap.put("beneficiaryName", discharge.getBeneficiaryName());
			// paramMap.put("disabilityDate",);
			paramMap.put("commencementDate", discharge.getCommenmanceDate());
			paramMap.put("premium", discharge.getRenewelAmount());
			paramMap.put("renewelInterest", discharge.getRenewelInterest());
			paramMap.put("loanAmount", discharge.getLoanAmount());
			paramMap.put("loanInterest", discharge.getLoanInterest());
			paramMap.put("serviceCharges", discharge.getServiceCharges());
			paramMap.put("netAmount", discharge.getNetClaimAmount());
			paramMap.put("witnessName", " ");
			paramMap.put("witnessAddress", " ");
			paramMap.put("nrc", discharge.getIdNo());
			paramMap.put("fatherName", discharge.getFatherName());
			paramMap.put("customerAddress", discharge.getAddress());

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_DISCHARGE_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);

			jasperListPDFExport(jasperPrintList, dirPath, fileName);
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate DischargeCertificate", e);
		}
	}

	public static void generateLifeClaimDischargeCertificate(LifeClaimDischargeFormDTO discharge, String dirPath, String fileName) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String presentDate = Utils.formattedDate(discharge.getPresentDate());
			String liabilitiesAmount = Utils.formattedCurrency(discharge.getClaimAmount());
			paramMap.put("policyNo", discharge.getPolicyNo());
			paramMap.put("customerName", discharge.getInsuredPersonName());
			paramMap.put("liabilitiesAmount", liabilitiesAmount);
			paramMap.put("beneficiaryName", discharge.getBeneficiaryName());
			paramMap.put("sumInsured", discharge.getSumInsured());
			paramMap.put("commencementDate", discharge.getCommenmanceDate());
			paramMap.put("premium", discharge.getRenewelAmount());
			paramMap.put("renewelInterest", discharge.getRenewelInterest());
			paramMap.put("loanAmount", discharge.getLoanAmount());
			paramMap.put("loanInterest", discharge.getLoanInterest());
			paramMap.put("netAmount", discharge.getNetClaimAmount());
			paramMap.put("nrc", discharge.getIdNo());
			paramMap.put("fatherName", discharge.getFatherName());
			paramMap.put("customerAddress", discharge.getAddress());
			paramMap.put("presentDate", presentDate);
			paramMap.put("serviceCharges", discharge.getServiceCharges());
			paramMap.put("maturityDate", discharge.getMaturityDate());
			paramMap.put("witnessName", " ");
			paramMap.put("witnessAddress", " ");

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_CLAIM_DISCHARGE_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);

			jasperListPDFExport(jasperPrintList, dirPath, fileName);
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate DischargeCertificate", e);
		}
	}

	// for generate TLFVoucher
	public static void generateTLFVoucher(List<TLFVoucherDTO> cashReceiptDTOList, String fullTemplateFilePath, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(cashReceiptDTOList));
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate AcceptanceLetter", e);
		}
	}

	// for generate ClaimVoucher
	public static void generateClaimVoucher(List<ClaimVoucherDTO> claimVoucherDTOList, String fullTemplateFilePath, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(claimVoucherDTOList));

			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate AcceptanceLetter", e);
		}
	}

	/* for Life Surrender Proposal Inform */
	public static void generateLifeSurrenderInformForm(LifeSurrenderProposal surrenderProposal, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		LifePolicy lifePolicy = surrenderProposal.getLifePolicy();
		double loanAmount = 0.0;
		double loanInterest = 0.0;
		double renewalAmount = 0.0;
		double renewalInterest = 0.0;
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("currentDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			paramMap.put("policyNo", surrenderProposal.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("customerAddress", lifePolicy.getCustomerAddress());
			paramMap.put("surrenderAmount", surrenderProposal.getSurrenderAmount());
			paramMap.put("proposalNo", surrenderProposal.getProposalNo());
			paramMap.put("serviceCharges", claimAcceptedInfo.getServicesCharges());
			paramMap.put("loanAmount", loanAmount);
			paramMap.put("loanInterest", loanInterest);
			paramMap.put("renewalAmount", renewalAmount);
			paramMap.put("renewalInterest", renewalInterest);
			paramMap.put("totalAmount", claimAcceptedInfo.getTotalAmount());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_INFORM_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate report lifeSurrenderInform", e);
		}
	}

	public static void generateLifeSurrenderRejectLetter(LifeSurrenderProposal surrenderProposal, ClaimAcceptedInfo claimAcceptedInfo, String rejectDirPath,
			String rejectFileName) {
		LifePolicy lifePolicy = surrenderProposal.getLifePolicy();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", surrenderProposal.getProposalNo());
			paramMap.put("informedDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("nrc", lifePolicy.getCustomerNRC());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_REJECT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(rejectDirPath);
			String outputFile = rejectDirPath + rejectFileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate report Life Surrender Reject Letter", e);
		}
	}

	public static void generateLifeSurrenderCashReceipt(LifeSurrenderProposal surrenderProposal, PaymentDTO payment, String dirPath, String fileName) {
		LifePolicy lifePolicy = surrenderProposal.getLifePolicy();
		double suminsured = lifePolicy.getSumInsured();
		Date startDate = lifePolicy.getActivedPolicyStartDate();
		Date endDate = lifePolicy.getActivedPolicyEndDate();
		int paidMonth = Utils.monthsBetween(startDate, endDate, false, true);
		String proposalNo = surrenderProposal.getProposalNo();
		String customerName = lifePolicy.getCustomerName();
		String customerAddress = lifePolicy.getCustomerAddress();
		String policyNo = surrenderProposal.getPolicyNo();
		double surrenderAmount = surrenderProposal.getSurrenderAmount();
		double lifePremium = surrenderProposal.getLifePremium();
		double serviceCharges = payment.getServicesCharges();
		double loan = 0.0;
		double loanInterest = 0.0;
		double teeMyaeInterest = 0.0;
		double totalAmount = payment.getTotalClaimAmount() - (loan + loanInterest + teeMyaeInterest + lifePremium);

		AbstractMynNumConvertor convertor = new DefaultConvertor();
		String totalAmountInMMText = convertor.getName(totalAmount);
		String totalAmountInMM = NumberToNumberConvertor.getMyanmarNumber(totalAmount, true);
		String surrenderAmountInMM = NumberToNumberConvertor.getMyanmarNumber(surrenderAmount, true);
		String loanInMM = NumberToNumberConvertor.getMyanmarNumber(loan, true);
		String loanInterestInMM = NumberToNumberConvertor.getMyanmarNumber(loanInterest, true);
		String lifePremiumInMM = NumberToNumberConvertor.getMyanmarNumber(lifePremium, true);
		String serviceChargesInMM = NumberToNumberConvertor.getMyanmarNumber(serviceCharges, true);
		String teeMyaeInterestInMM = NumberToNumberConvertor.getMyanmarNumber(teeMyaeInterest, true);

		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				paramMap.put("chequeNo", payment.getChequeNo());
				paramMap.put("bank", payment.getBank().getName());
				paramMap.put("chequePayment", Boolean.TRUE);
				paramMap.put("receiptType", "Cheque Payment");
				paramMap.put("receiptName", "Payment No.");
			} else {
				paramMap.put("chequePayment", Boolean.FALSE);
				paramMap.put("receiptType", "Cash Payment");
				paramMap.put("receiptName", "Payment No.");
			}
			paramMap.put("proposalNo", proposalNo);
			paramMap.put("policyNo", policyNo);
			paramMap.put("cashReceiptNo", payment.getReceiptNo());
			paramMap.put("surrenderAmount", surrenderAmount);
			paramMap.put("loan", loan);
			paramMap.put("loanInterest", loanInterest);
			paramMap.put("serviceCharges", payment.getServicesCharges());
			paramMap.put("lifePremium", lifePremium);
			paramMap.put("teeMyaeinterest", teeMyaeInterest);
			paramMap.put("suminsured", suminsured);
			paramMap.put("paidPremium", surrenderProposal.getReceivedPremium());
			paramMap.put("totalAmount", totalAmount);
			paramMap.put("confirmDate", payment.getConfirmDate());
			paramMap.put("insuredPerson", customerName);
			paramMap.put("claimType", "Surrender Claim");
			paramMap.put("customerAddress", customerAddress);
			paramMap.put("reportLogo", ApplicationSetting.getReportLogo());
			Currency currency = new Currency();
			currency.setCurrencyCode(CurrencyUtils.getCurrencyCode(null));
			paramMap.put("currencyFormat", CurrencyUtils.getCurrencyFormat(currency));
			paramMap.put("currencyCode", currency.getCurrencyCode());
			paramMap.put("currencySymbol", "KYT");
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_RECEIPT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);

			Map<String, Object> map = new HashMap<>();
			map.put("policyNo", policyNo);
			map.put("insuredPerson", customerName);
			map.put("totalAmountInMMText", totalAmountInMMText);
			map.put("totalAmountInMM", totalAmountInMM);
			map.put("confirmDate", payment.getConfirmDate());
			map.put("nrcNo", lifePolicy.getCustomerNRC());
			map.put("loanInMM", loanInMM);
			map.put("loanInterestInMM", loanInterestInMM);
			map.put("lifePremiumInMM", lifePremiumInMM);
			map.put("teeMyaeInterestInMM", teeMyaeInterestInMM);
			map.put("serviceChargesInMM", serviceChargesInMM);
			map.put("surrenderAmountInMM", surrenderAmountInMM);
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_RECEIPT_LETTER1);
			JasperReport report = JasperCompileManager.compileReport(stream);
			JasperPrint print = JasperFillManager.fillReport(report, map, new JREmptyDataSource());
			jasperPrintList.add(print);
			jasperListPDFExport(jasperPrintList, dirPath, fileName);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeSurrenderCashReceipt", e);
		}
	}

	public static void generateLifeSurrenderIssue(LifeSurrenderProposal proposal, String dirPath, String fileName) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			Product product = proposal.getLifePolicy().getPolicyInsuredPersonList().get(0).getProduct();
			boolean isPublicLife;
			boolean isShortermEndownment;
			boolean isStudentLife;
			boolean isPublicTermLife;
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isPublicLife = KeyFactorChecker.isPublicLife(product);
			isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
			paramMap.put("empty", " ");
			paramMap.put("policyNo", proposal.getPolicyNo());
			paramMap.put("todayDate", Utils.formattedDate(new Date()));
			paramMap.put("policyDate", Utils.formattedDate(proposal.getLifePolicy().getActivedPolicyStartDate()));
			paramMap.put("insuredPersonName", proposal.getLifePolicy().getInsuredPersonInfo().get(0).getFullName());
			paramMap.put("timesUpOrDeadDate", " ");
			paramMap.put("requestedPerson", proposal.getLifePolicy().getInsuredPersonInfo().get(0).getFullName());
			paramMap.put("sumInsured", Utils.getCurrencyFormatString(proposal.getLifePolicy().getSumInsured()));
			paramMap.put("premium", Utils.getCurrencyFormatString(proposal.getLifePremium()));
			// currently only premium , add the new minus values later
			double netAmountToMinus = proposal.getLifePremium();
			paramMap.put("netAmountToMinus", Utils.getCurrencyFormatString(netAmountToMinus));
			paramMap.put("netAmountToPay", Utils.getCurrencyFormatString(proposal.getSurrenderAmount()));
			// final amount = netAmountToPay - netAmountToMinus
			double finalAmount = proposal.getSurrenderAmount() - proposal.getLifePremium();
			paramMap.put("finalAmount", Utils.getCurrencyFormatString(finalAmount));
			paramMap.put("insuredPersonNRC", proposal.getLifePolicy().getInsuredPersonInfo().get(0).getIdNo());
			paramMap.put("reqAmount", Utils.getCurrencyFormatString(proposal.getSurrenderAmount()));
			paramMap.put("paymentDate", Utils.formattedDate(new Date()));
			AbstractMynNumConvertor convertor = new DefaultConvertor();
			paramMap.put("activedPolicyEndDate", Utils.formattedDate(proposal.getLifePolicy().getActivedPolicyEndDate()));
			paramMap.put("finalAmountText", convertor.getName(finalAmount));
			paramMap.put("finalAmountNumeric", Utils.getCurrencyFormatString(finalAmount));
			String productName = "";
			String productDescription = "";
			if (isPublicLife) {
				productName = MyanmarLanguae.getMyanmarLanguaeString("PUBLIC_LIFE_LABEL");
				productDescription = productName + MyanmarLanguae.getMyanmarLanguaeString("LIFE_SURRENDER_PRODUCT_LABEL");
			} else if (isShortermEndownment) {
				productName = MyanmarLanguae.getMyanmarLanguaeString("SHORT_TERM_LIFE_LABEL");
				productDescription = productName + MyanmarLanguae.getMyanmarLanguaeString("LIFE_SURRENDER_PRODUCT_LABEL");
				} else if (isStudentLife) {
				productName = MyanmarLanguae.getMyanmarLanguaeString("STUDENT_LIFE_LABEL");
				productDescription = productName + MyanmarLanguae.getMyanmarLanguaeString("LIFE_SURRENDER_PRODUCT_LABEL");
			} else if (isPublicTermLife) {
				productName = MyanmarLanguae.getMyanmarLanguaeString("PUBLIC_TERM_LIFE_LABEL");
				productDescription = productName + MyanmarLanguae.getMyanmarLanguaeString("LIFE_SURRENDER_PRODUCT_LABEL");
			} else {
				productName = MyanmarLanguae.getMyanmarLanguaeString("GROUP_LIFE_LABEL");
				productDescription = productName + MyanmarLanguae.getMyanmarLanguaeString("LIFE_SURRENDER_PRODUCT_LABEL");
			}
			paramMap.put("productDescription", productDescription);
			paramMap.put("productName", productName);
			
			/* For surrender jasper2 */
			paramMap2.put("product", productName);
			paramMap2.put("insuredPersonName", proposal.getLifePolicy().getInsuredPersonInfo().get(0).getFullName());
			paramMap2.put("policyNo", proposal.getPolicyNo());
			paramMap2.put("sumInsured", Utils.getCurrencyFormatString(proposal.getLifePolicy().getSumInsured()));
			paramMap2.put("activepolicystartdate",Utils.formattedDate(proposal.getLifePolicy().getActivedPolicyStartDate()));
			paramMap2.put("activepolicyenddate",Utils.formattedDate(proposal.getLifePolicy().getActivedPolicyEndDate()));
			paramMap2.put("policyperiod", proposal.getLifePolicy().getPeriodOfYears());
			paramMap2.put("paymentType",proposal.getLifePolicy().getPaymentType().getName());
			paramMap2.put("premium", Utils.getCurrencyFormatString(proposal.getLifePolicy().getInsuredPersonInfo().get(0).getPremium()));
			paramMap2.put("paymentyear", proposal.getPaymentYear());
			paramMap2.put("coveragedate", Utils.formattedDate(proposal.getLifePolicy().getCoverageDate()));
			paramMap2.put("receivePremium", Utils.getCurrencyFormatString(proposal.getReceivedPremium()));
			double si=proposal.getLifePolicy().getSumInsured();
			paramMap2.put("finalpremium",Utils.getCurrencyFormatString(((147*si)/1000)));
			paramMap2.put("todayDate", Utils.formattedDate(new Date()));

			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_ISSUE_LETTER);
			InputStream stream1 = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_ISSUE_LETTER1);
			JasperReport report = JasperCompileManager.compileReport(stream);
			JasperReport report1 = JasperCompileManager.compileReport(stream1);
			JasperPrint print = JasperFillManager.fillReport(report, paramMap, new JREmptyDataSource());
			JasperPrint print1 = JasperFillManager.fillReport(report1, paramMap2, new JREmptyDataSource());
			jasperPrintList.add(print);
			jasperPrintList.add(print1);
			jasperListPDFExport(jasperPrintList, dirPath, fileName);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeSurrenderIssue", e);
		}
	}

	/* Life PaidUp Proposal */
	public static void generateLifePaidUpInformForm(LifePaidUpProposal lifePaidUpProposal, ClaimAcceptedInfo claimAcceptedInfo, String dirPath, String fileName) {
		LifePolicy lifePolicy = lifePaidUpProposal.getLifePolicy();
		try {
			Map<String, Object> sanctionParamMap = new HashMap<>();
			sanctionParamMap.put("todayDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			sanctionParamMap.put("customerName", lifePolicy.getCustomerName());
			sanctionParamMap.put("policyNo", lifePaidUpProposal.getPolicyNo());
			sanctionParamMap.put("policyStartDate", Utils.formattedDate(lifePolicy.getActivedPolicyStartDate()));
			sanctionParamMap.put("policyPeriod", lifePaidUpProposal.getPolicyPeriod());
			sanctionParamMap.put("sumInsured", lifePaidUpProposal.getSumInsured());
			sanctionParamMap.put("paymentYear", lifePaidUpProposal.getPaymentYear());
			sanctionParamMap.put("paiedPremium", lifePaidUpProposal.getReceivedPremium());
			sanctionParamMap.put("paidUpAmount", lifePaidUpProposal.getRealPaidUpAmount());
			InputStream paidUpSactionIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_PAIDUP_SANCTION_LETTER);
			JasperReport paidUpSactionJR = JasperCompileManager.compileReport(paidUpSactionIS);
			JasperPrint paidUpSactionJP = JasperFillManager.fillReport(paidUpSactionJR, sanctionParamMap, new JREmptyDataSource());
			Map<String, Object> calculationParamMap = new HashMap<>();
			calculationParamMap.put("policyNo", lifePaidUpProposal.getPolicyNo());
			calculationParamMap.put("policyPeriod", lifePaidUpProposal.getPolicyPeriod());
			calculationParamMap.put("sumInsured", lifePaidUpProposal.getSumInsured());
			calculationParamMap.put("paymentYear", lifePaidUpProposal.getPaymentYear());
			calculationParamMap.put("paidUpAmount", lifePaidUpProposal.getPaidUpAmount());
			calculationParamMap.put("paymentType", lifePolicy.getPaymentType().getMonth());
			Date startDate = lifePolicy.getActivedPolicyStartDate();
			Date endDate = lifePolicy.getActivedPolicyEndDate();
			int paidMonth = Utils.monthsBetween(startDate, endDate, false, true);
			int reqPaymentTerm = paidMonth % 12;
			if (reqPaymentTerm >= 6) {
				reqPaymentTerm = 12 - reqPaymentTerm;
			}
			calculationParamMap.put("reqPaymentTerm", reqPaymentTerm);
			calculationParamMap.put("reqAmount", lifePaidUpProposal.getReqAmount());
			InputStream calculationIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_PAIDUP_CAL);
			JasperReport calculationJR = JasperCompileManager.compileReport(calculationIS);
			JasperPrint calculationJP = JasperFillManager.fillReport(calculationJR, calculationParamMap, new JREmptyDataSource());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("todayDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			paramMap.put("policyNo", lifePaidUpProposal.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("paidUpAmount", lifePaidUpProposal.getPaidUpAmount());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_PAIDUP_INFORM_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			List<JasperPrint> jpList = new ArrayList<>();
			jpList.add(paidUpSactionJP);
			jpList.add(calculationJP);
			jpList.add(jprint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
			FileHandler.forceMakeDirectory(dirPath);
			OutputStream outputStream = new FileOutputStream(dirPath + fileName);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			outputStream.close();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate report lifePaidUpInform", e);
		}
	}

	public static void generateLifePaidUpRejectLetter(LifePaidUpProposal lifePaidUpProposal, ClaimAcceptedInfo claimAcceptedInfo, String rejectDirPath, String rejectFileName) {
		LifePolicy lifePolicy = lifePaidUpProposal.getLifePolicy();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", lifePaidUpProposal.getProposalNo());
			paramMap.put("informedDate", Utils.formattedDate(claimAcceptedInfo.getInformDate()));
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("nrc", lifePolicy.getCustomerNRC());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.LIFE_SURR_REJECT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(rejectDirPath);
			String outputFile = rejectDirPath + rejectFileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate report Life PaidUp Reject Letter", e);
		}
	}

	/* Student Life */
	public static void generateStudentLifeSanction(LifeProposal lifeProposal, String dirPath, String fileName) {
		try {
			ProposalInsuredPerson insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", lifeProposal.getProposalNo());
			paramMap.put("customerName", lifeProposal.getCustomer().getFullName());
			paramMap.put("insuredPersonName", insuredPerson.getFullName());
			paramMap.put("age", lifeProposal.getCustomer().getAgeForNextYear());
			paramMap.put("paymentType", lifeProposal.getPaymentType().getName());
			paramMap.put("insuredPersonAge", insuredPerson.getAge());
			paramMap.put("sumInsured", Utils.formattedCurrency(lifeProposal.getSumInsured()));
			paramMap.put("period", lifeProposal.getPeriodOfYears());
			paramMap.put("premiumTerm", lifeProposal.getPremiumTerm());
			paramMap.put("medicalInfo", lifeProposal.getCustomerClsOfHealth() != null ? lifeProposal.getCustomerClsOfHealth().getLabel() : "");
			double totalTermPremim = lifeProposal.getTotalTermPremium();
			double kyat = Math.floor(totalTermPremim);
			double pyar = (totalTermPremim - kyat) * 100;
			DecimalFormat formatter1 = new DecimalFormat("#,###.00");
			String termPremiumKyat = formatter1.format(kyat);
			DecimalFormat formatter2 = new DecimalFormat("00");
			String termPremiumPyar = formatter2.format(pyar);
			double totalPremium = lifeProposal.getTotalPremium();
			paramMap.put("totalPremium", formatter1.format(totalTermPremim));
			paramMap.put("premiumKyat", termPremiumKyat);
			paramMap.put("premiumPyar", termPremiumPyar);
			paramMap.put("reportLogo", "report-template/TAT-logo.png");
			paramMap.put("reportAddress", ApplicationSetting.getGGIAddress());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.STUDENTLIFE_SANCTION_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Student Life Sanction", e);
		}
	}

	public static void generateStudentLifeAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			ProposalInsuredPerson insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("customerName", lifeProposal.getCustomerName());
			paramMap.put("childName", insuredPerson.getFullName());
			paramMap.put("customerAddress", lifeProposal.getCustomerAddress());
			paramMap.put("phoneNo", lifeProposal.getPhoneNo());
			paramMap.put("date", org.ace.insurance.common.Utils.getDateFormatString(acceptedInfo.getInformDate()));
			paramMap.put("proposalNo", lifeProposal.getProposalNo());
			paramMap.put("periodYear", lifeProposal.getPeriodOfYears());
			paramMap.put("premiumTerm", lifeProposal.getPremiumTerm());
			paramMap.put("sumInsured", Utils.formattedCurrency(lifeProposal.getApprovedSumInsured()));
			paramMap.put("totalPremium", Utils.formattedCurrency(acceptedInfo.getTotalPremium()));
			paramMap.put("netPremium", Utils.formattedCurrency(acceptedInfo.getNetPremium()));
			paramMap.put("netTermPremium", Utils.formattedCurrency(acceptedInfo.getNetTermPremium()));
			paramMap.put("netTermAmount", Utils.formattedCurrency(acceptedInfo.getNetTermAmount()));
			paramMap.put("paymentType", lifeProposal.getPaymentType().getName());
			if (lifeProposal.getAgent() != null) {
				paramMap.put("agent", lifeProposal.getAgent().getFullName() + "( " + lifeProposal.getAgent().getLiscenseNo() + " )");
			} else {
				paramMap.put("agent", "( - )");
			}
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.STUDENTLIFE_INFORM_ACCEPTED_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Student Life AcceptanceLetter", e);
		}
	}

	public static void generateStudentLifeRejectLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (lifeProposal.getCustomer() != null) {
				if (lifeProposal.getCustomer().getFullIdNo() != null) {
					paramMap.put("nrc", lifeProposal.getCustomer().getFullIdNo());
				} else {
					paramMap.put("nrc", "");
				}
			}
			if (lifeProposal.getAgent() != null) {
				paramMap.put("agent", lifeProposal.getAgent().getFullName() + "( " + lifeProposal.getAgent().getLiscenseNo() + " )");
			} else {
				paramMap.put("agent", "( - )");
			}
			paramMap.put("customerName", lifeProposal.getCustomerName());
			paramMap.put("customerAddress", lifeProposal.getCustomerAddress());
			paramMap.put("phoneNo", lifeProposal.getPhoneNo());
			paramMap.put("todayDate", org.ace.insurance.common.Utils.getDateFormatString(new Date()));
			paramMap.put("proposalNo", lifeProposal.getProposalNo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(JasperTemplate.STUDENTLIFE_INFORM_REJECT_LETTER);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Student Life RejectLetter", e);
		}
	}

	
}
