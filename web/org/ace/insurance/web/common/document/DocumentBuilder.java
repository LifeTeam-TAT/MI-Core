package org.ace.insurance.web.common.document;

import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.proxy.MEDFEES001;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.web.common.document.life.LifeDocumentBuilder;
import org.ace.insurance.web.common.document.life.LifeEndorsementDocumentBuilder;
import org.ace.insurance.web.common.document.medical.MedicalDocumentBuilder;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionCashReceiptDTO;
import org.ace.java.component.SystemException;

import net.sf.jasperreports.engine.JasperPrint;

public class DocumentBuilder {
	/* Life Acceptance Letter */
	public static <T> void generateLifeAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifeAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Life Acceptance Letter", e);
		}
	}

	public static <T> void generateSnakeBikeAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSnakeBikeAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Snake Bike Acceptance Letter", e);
		}
	}
	
	public static <T> void generatePersonalAccidentAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generatePersonalAccidentAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate PA Acceptance Letter", e);
		}
	}
	public static <T> void generateSeaMenAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSeaMenAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate PA Acceptance Letter", e);
		}
	}
	
	public static <T> void generateSportManAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSportManAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Sport Man Acceptance Letter", e);
		}
	}

	public static <T> void generateFarmerAcceptanceLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateFarmerAcceptanceLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Farmer Acceptance Letter", e);
		}
	}

	/* Life Reject Letter */
	public static <T> void generatePARejectLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generatePersonalAccidentRejectLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate PA Reject Letter", e);
		}
	}

	public static <T> void generateFarmerRejectLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateFarmerRejectLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Farmer Reject Letter", e);
		}
	}

	public static <T> void generateLifeRejectLetter(LifeProposal lifeProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifeRejectLetter(lifeProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Life Reject Letter", e);
		}
	}

	/* Life Invoice Letter */
	public static <T> void generateLifePaymentInvoice(LifeProposal lifeProposal, PaymentDTO payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifeNewInvoice(lifeProposal, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Life payment invoice Letter", e);
		}
	}

	public static <T> void generateLifeInvoice(LifeProposal lifeProposal, PaymentDTO payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifePaymentInvoice(lifeProposal, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Life invoice Letter", e);
		}
	}

	/* Life Receipt Letter */
	public static <T> void generateLifeReceiptLetter(LifeProposal lifeProposal, Payment payment, Boolean isSportsMan, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifeReceiptLetter(lifeProposal, payment, isSportsMan);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Short Term Life Receipt Letter", e);
		}
	}

	/* Life Policy Letter */
	public static <T> void generateSportManAboradCertificatAndInvoice(List<SportManTravelAbroad> sportManTravelAbroadList, Payment payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSportManAbroadCertificatAndInvoice(sportManTravelAbroadList, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Sport Man Letter", e);
		}
	}

	public static <T> void generateShortTermLifePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateShortTermLifePolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Short Term Life Policy Issue Letter", e);
		}
	}
	
	public static <T> void generateGovernmentShortTermLifePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateGovernmentShortTermLifePolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Short Term Life Policy Issue Letter", e);
		}
	}

	public static <T> void generateGroupLifePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateGroupLifePolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate GroupLife Policy Issue Letter", e);
		}
	}

	public static <T> void generatePAPolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generatePersonalAccidentPolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Personal Accident Policy Issue Letter", e);
		}
	}

	public static <T> void generateFarmerPolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateFarmerPolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Farmer Policy Issue Letter", e);
		}
	}

	public static <T> void generateSnakeBitePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSnakeBitePolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate SnakeBite Policy Issue Letter", e);
		}
	}

	public static <T> void generateSportManPolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSportManPolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Sport Man Policy Issue Letter", e);
		}
	}
	
	public static <T> void generateSeaManPolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateSeaManPolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Seaman Policy Issue Letter", e);
		}
	}

	public static <T> void generatePublicLifePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generatePublicLifePolicyLetter(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Public Life Policy Issue Letter", e);
		}
	}

	public static <T> void generateStudentLifePolicyLetter(LifePolicy lifePolicy, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateStudentLifePolicyJasperPrint(lifePolicy, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Public Life Policy Issue Letter", e);
		}
	}

	/* Life Bill Collection Receipt Letter */
	public static <T> void generateLifeBillCollectionCashReceipt(LifePolicy policy, Payment payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifeBillCashReceipt(policy, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Life Cash Receipt Letter", e);
		}
	}

	/* Life Policy Noti Letter */
	public static <T> void generateLifePolicyNotificationLetters(List<LifePolicy> policies, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifePolicyNotification(policies, dirPath, fileName);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifePolicyNotification Report ", e);
		}
	}

	/* Life Policy Renewal Letter */
	public static <T> void generateLifePolicyRenewalLetters(List<LifePolicy> policies, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifePolicyRenewal(policies, dirPath, fileName);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifePolicyRenewal Report ", e);
		}
	}

	public static <T> void generateMedicalPolicyRenewalLetters(List<MedicalPolicy> policies, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateMedicalPolicyRenewal(policies, dirPath, fileName);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifePolicyRenewal Report ", e);
		}
	}

	/* Medical Acceptance Letter */
	public static <T> void generateMedicalAcceptanceLetter(MedicalProposal medicalProposal, AcceptedInfo acceptedInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalAcceptanceLetter(medicalProposal, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Accepted Letter", e);
		}
	}

	/* Medical Reject Letter */
	public static <T> void generateMedicalRejectLetter(MedicalProposal medicalProposal, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalRejectLetter(medicalProposal);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Reject Letter", e);
		}
	}

	/* Medical Invoice Letter */
	public static <T> void generateMedicalPaymentInvoice(MedicalProposal medicalProposal, PaymentDTO payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateHealthPaymentInvoice(medicalProposal, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Payment Invoice Letter", e);
		}
	}

	/* Medical Receipt Letter */
	public static <T> void generateMedicalReceiptLetter(MedicalProposal medicalProposal, Payment payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalReceipt(medicalProposal, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Cash Receive Letter", e);
		}
	}

	/* Medical Policy Letter */
	public static <T> void generateMedicalPolicyIssueLetter(MedicalPolicy medicalPolicy, Payment payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalHealthPolicyIssue(medicalPolicy, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Policy Issue Letter", e);
		}
	}

	/* Life, Medical Bill Collection Invoice Letter */
	public static <T> void generateBillCollectionPaymentInvoice(List<BillCollectionCashReceiptDTO> cashReceiptDTOs, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = null;
			ReferenceType referenceType = cashReceiptDTOs.get(0).getReferenceType();
			if (ReferenceType.HEALTH.equals(referenceType) || ReferenceType.CRITICAL_ILLNESS.equals(referenceType) || ReferenceType.MICRO_HEALTH.equals(referenceType)) {
				printList = MedicalDocumentBuilder.generateBillCollectionPaymentInvoice(cashReceiptDTOs);
			} else if (ReferenceType.ENDOWMENT_LIFE.equals(referenceType) || ReferenceType.SHORT_ENDOWMENT_LIFE.equals(referenceType)
					|| ReferenceType.STUDENT_LIFE.equals(referenceType)) {
				printList = LifeDocumentBuilder.generateLifeBillPaymentInvoice(cashReceiptDTOs);
			}
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Bill Collection Payment Invoice Letter", e);
		}
	}

	/* Medical Bill Collection Receipt Letter */
	public static <T> void generateMedicalBillCollectionCashReceipt(MedicalPolicy policy, Payment payment, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalBillcollectionCashReceipt(policy, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Cash Receipt Letter", e);
		}
	}

	/* Medical Policy Noti Letter */
	public static <T> void generateMedicalPolicyNotificationLetters(List<MedicalPolicy> medicalPolicyList, String dirPath, String fileName, ReferenceType referenceType) {
		try {
			List<JasperPrint> printList = MedicalDocumentBuilder.generateMedicalPolicyNotification(medicalPolicyList, dirPath, fileName, referenceType);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Medical Policy Notification Report ", e);
		}
	}

	/* Agent Sanction Letter */
	public static <T> void generateAgentSanctionReport(List<AgentSanctionInfo> sanctionInfoList, Agent agent, AgentSanctionCriteria criteria, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = AgentDocumentBuilder.generateAgentSanctionRepor(sanctionInfoList, agent, criteria);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Agent Sanction Report ", e);
		}
	}

	public static void generateDisabilityLifeClaimLetter(LifeClaimProposal lifeClaimProposal, PaymentDTO paymentDTO, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateDisabilityLifeClaimLetter(lifeClaimProposal, paymentDTO);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Public Life Policy Issue Letter", e);
		}
	}

	public static void generateDisabilityLifeClaimInformLetter(LifeClaimProposal lifeClaimProposal, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateDisabilityLifeClaimInformLetter(lifeClaimProposal);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Public Life Policy Issue Letter", e);
		}
	}

	/* Life Endorse Letter */
	public static <T> void generateEndorseChangesLetters(LifePolicy lifePolicy, LifePolicyHistory policyHistory, LifeEndorseInfo endorseInfo, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeEndorsementDocumentBuilder.EndorseChangesLetters(lifePolicy, policyHistory, endorseInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Endorse Changes Letter ", e);
		}
	}

	public static <T> void generateLifeClaimPaymentSlipLetters(LifeClaimProposal lifeClaimProposal, String dirPath, String fileName, int claimCount,
			ClaimAcceptedInfo acceptedInfo) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateLifClaimPaymentSlipLetter(lifeClaimProposal, claimCount, acceptedInfo);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Endorse Changes Letter ", e);
		}
	}

	public static <T> void generateLifeClaimPaymentLetters(LifeClaimProposal lifeClaimProposal, String dirPath, String fileName, Payment payment) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateClaimPaymentLetter(lifeClaimProposal, payment);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Endorse Changes Letter ", e);
		}
	}

	public static <T> void generateLifeClaimMedicalFeesPaymentLetters(MEDFEES001 dto, String dirPath, String fileName, String pyaee,
			List<PolicyInsuredPerson> policyInsuredPersonList) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateClaimMedicalFeesPaymentLetter(dto, pyaee, policyInsuredPersonList);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Endorse Changes Letter ", e);
		}
	}

	public static <T> void generateClaimMedicalFeesSanctionReport(List<LifeClaimMedicalFeeDTO> claimMedicalInfoList, Hospital hospital, String dirPath, String fileName) {
		try {
			List<JasperPrint> printList = LifeDocumentBuilder.generateClaimMedicalFeesSanction(claimMedicalInfoList, hospital);
			JasperFactory.exportReportToPdfFile(printList, dirPath, fileName);
		} catch (SystemException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Agent Sanction Report ", e);
		}
	}

}
