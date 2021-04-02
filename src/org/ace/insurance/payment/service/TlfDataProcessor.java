package org.ace.insurance.payment.service;

import java.util.ArrayList;
import java.util.List;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.interfaces.IPolicy;
import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.agent.COACode;
import org.ace.java.component.SystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TlfDataProcessor")
public class TlfDataProcessor implements ITlfDataProcessor {

	public TlfData getInstance(PolicyReferenceType type, IPolicy policy, Payment payment, List<AgentCommission> agentCommissionList, boolean isRenewal) throws SystemException {
		TlfData tlfData = null;
		try {
			boolean isAgent = policy.getAgent() != null ? true : false;
			double sumInsured = policy.getTotalSumInsured();

			/* load Data */
			tlfData = loadTlfDataAccCode(type, payment.getPaymentChannel(), isAgent);
			String customerId = policy.getCustomerId();
			String customerName = policy.getCustomerName();
			tlfData.setSumInsured(sumInsured);
			tlfData.setCustomerId(customerId);
			tlfData.setCustomerName(customerName);
			tlfData.setPayment(payment);
			tlfData.setReceivableDr("CASH");
			tlfData.setSalePointName("HEAD OFFICE");
			tlfData.setAgentCommissionList(agentCommissionList);
			// tlfData.setCoinsu001(coinsu001);
			tlfData.setRenewal(isRenewal);
			tlfData.setUnit(policy.getTotalUnit());
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), "Faield to add a new TLF", e);
		}

		return tlfData;
	}

	public TlfData getInstance(PolicyReferenceType type, LifeClaimProposal lifeClaimProposal, Payment payment) throws SystemException {
		TlfData tlfData = null;
		try {
			double sumInsured = lifeClaimProposal.getTotalClaimAmont();

			/* load Data */
			tlfData = loadClaimTlfDataAccCode(type, payment.getPaymentChannel());
			String customerId = lifeClaimProposal.getLifePolicy().getCustomerId();
			String customerName = lifeClaimProposal.getLifePolicy().getCustomerName();
			tlfData.setSumInsured(sumInsured);
			tlfData.setCustomerId(customerId);
			tlfData.setCustomerName(customerName);
			tlfData.setPayment(payment);
			tlfData.setAgentCommissionList(new ArrayList<AgentCommission>());
			// tlfData.setReceivableDr(payment.getSalesPoints().getReceivableAcName());
			// tlfData.setSalePointName(payment.getSalesPoints().getName());
			tlfData.setRenewal(false);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), "Faield to add a new TLF", e);
		}

		return tlfData;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TlfData getAgentCommissionInstance(Payment payment, AgentCommission agentCommission) throws SystemException {
		TlfData tlfData = null;
		try {
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
			agentCommissionList.add(agentCommission);
			/* load Data */
			tlfData = loadAgentCommissionTlfDataAccCode(agentCommission.getReferenceType(), payment);
			String customerId = null;
			String customerName = null;
			if (agentCommission.getCustomer() != null) {
				customerId = agentCommission.getCustomer().getId();
				customerName = agentCommission.getCustomer().getFullName();
			} else {
				customerId = agentCommission.getOrganization().getId();
				customerName = agentCommission.getOrganization().getName();
			}
			tlfData.setCustomerId(customerId);
			tlfData.setCustomerName(customerName);
			tlfData.setAgentCommissionList(agentCommissionList);
			tlfData.setPayment(payment);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), "Faield to add a new TLF", e);
		}
		return tlfData;
	}

	private TlfData loadAgentCommissionTlfDataAccCode(PolicyReferenceType refType, Payment payment) {
		TlfData tlfData = new TlfData();
		String agentCodeCr = null;
		String agentCodeDr = null;
		switch (refType) {

			case ENDOWNMENT_LIFE_POLICY:
			case LIFE_BILL_COLLECTION:
				agentCodeDr = COACode.PUBLIC_LIFE_AGENT_PAYABLE;
				break;
			case SHORT_ENDOWMENT_LIFE_POLICY:
			case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
				agentCodeDr = COACode.SHORT_ENDOWMENT_AGENT_PAYABLE;
				break;
			case FARMER_POLICY:
				agentCodeDr = COACode.FARMER_AGENT_PAYABLE;
				break;
			case PA_POLICY:
				agentCodeDr = COACode.PA_AGENT_PAYABLE;
				break;
			case SNAKE_BITE_POLICY:
				agentCodeDr = COACode.SNAKE_BITE_AGENT_PAYABLE;
				break;
			case GROUP_LIFE_POLICY:
				agentCodeDr = COACode.GROUP_LIFE_AGENT_PAYABLE;
				break;
			case SPORT_MAN_POLICY:
				agentCodeDr = COACode.SPORT_MAN_AGENT_PAYABLE;
				break;
			case HEALTH_POLICY:
			case HEALTH_POLICY_BILL_COLLECTION:
				agentCodeDr = COACode.HEALTH_AGENT_PAYABLE;
				break;
			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
				agentCodeDr = COACode.CRITICAL_ILLNESS_AGENT_PAYABLE;
				break;
			case MICRO_HEALTH_POLICY:
				agentCodeDr = COACode.MICRO_HEALTH_AGENT_PAYABLE;
				break;
			case STUDENT_LIFE_POLICY:
			case STUDENT_LIFE_POLICY_BILL_COLLECTION:
				agentCodeDr = COACode.STUDENT_AGENT_PAYABLE;
				break;
			case SEAMAN_LIFE_POLICY:
				agentCodeDr = COACode.SEAMAN_AGENT_PAYABLE;
				break;
			case GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY:
				agentCodeDr = COACode.GOVERNMENT_SHORT_ENDOWMENT_AGENT_PAYABLE;
				break;
			default:
				break;
		}

		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			agentCodeCr = payment.getAccountBank().getAcode();
		} else if (payment.getPaymentChannel().equals(PaymentChannel.CASHED)) {
			agentCodeCr = COACode.CASH;
		}

		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;

	}

	private TlfData loadTlfDataAccCode(PolicyReferenceType type, PaymentChannel channel, boolean isAgent) {
		TlfData tlfData = null;
		switch (type) {

			case ENDOWNMENT_LIFE_POLICY:
			case LIFE_BILL_COLLECTION:
				tlfData = loadEndownmentLifeTlfDataAccCode(channel, isAgent);
				break;
			case SHORT_ENDOWMENT_LIFE_POLICY:
			case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
				tlfData = loadShortEndownmentTlfDataAccCode(channel, isAgent);
				break;
			case GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY:
			case GOVERNMENT_SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
				tlfData = loadGovernmentShortEndownmentTlfDataAccCode(channel, isAgent);
				break;
			case STUDENT_LIFE_POLICY:
			case STUDENT_LIFE_POLICY_BILL_COLLECTION:
				tlfData = loadStudentLifeTlfDataAccCode(channel, isAgent);
				break;
			case PUBLIC_TERM_LIFE_POLICY:
			case PUBLIC_TERM_LIFE_POLICY_BILL_COLLECTION:
				tlfData = loadPublicTermLifeTlfDataAccCode(channel, isAgent);
				break;
			case FARMER_POLICY:
				tlfData = loadFarmerTlfDataAccCode(channel, isAgent);
				break;
			case PA_POLICY:
				tlfData = loadPersonalAccidentTlfDataAccCode(channel, isAgent);
				break;
			case SNAKE_BITE_POLICY:
				tlfData = loadSnakebiteTlfDataAccCode(channel, isAgent);
				break;
			case GROUP_LIFE_POLICY:
				tlfData = loadGroupLifeTlfDataAccCode(channel, isAgent);
				break;
			case SPORT_MAN_POLICY:
				tlfData = loadSportManTlfDataAccCode(channel, isAgent);
				break;
			case SEAMAN_LIFE_POLICY:
				tlfData = loadSeamanTlfDataAccCode(channel, isAgent);
				break;
			case HEALTH_POLICY:
			case HEALTH_POLICY_BILL_COLLECTION:
				tlfData = loadHealthTLFDataAccCode(channel, isAgent);
				break;
			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
				tlfData = loadCriticalIllnessTLFDataAccCode(channel, isAgent);
				break;
			case MICRO_HEALTH_POLICY:
				tlfData = loadMicroHealthTLFDataAccCode(channel, isAgent);
				break;
			default:
				break;
		}

		return tlfData;
	}

	private TlfData loadClaimTlfDataAccCode(PolicyReferenceType type, PaymentChannel channel) {
		TlfData tlfData = null;
		switch (type) {

			case ENDOWNMENT_LIFE_CLAIM:
				tlfData = loadEndownmentLifeClaimTlfDataAccCode(channel);
				break;
			case SHORT_TERM_LIFE_CLAIM:
				tlfData = loadShortEndownmentClaimTlfDataAccCode(channel);
				break;
			case STUDENT_LIFE_CLAIM:
				tlfData = loadStudentLifeClaimTlfDataAccCode(channel);
				break;
			case PUBLIC_TERM_LIFE_CLAIM:
				tlfData = loadPublicTermLifeClaimTlfDataAccCode(channel);
				break;
			case PA_LIFE_CLAIM:
				tlfData = loadPersonalAccidentClaimTlfDataAccCode(channel);
				break;
			case SNAKE_BITE_LIFE_CLAIM:
				tlfData = loadSnakebiteClaimTlfDataAccCode(channel);
				break;
			case GROUP_LIFE_CLAIM:
				tlfData = loadGroupLifeClaimTlfDataAccCode(channel);
				break;
			case SPORT_MAN_CLAIM:
				tlfData = loadSportManClaimTlfDataAccCode(channel);
				break;
			default:
				break;
		}

		return tlfData;
	}

	private TlfData loadEndownmentLifeClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.PUBLIC_LIFE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_CLAIM_PAYABLE : null;
		/* Cheque */
		String claimChequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_CLAIM_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.PUBLIC_LIFE_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.PUBLIC_LIFE_MEDICAL_FEES_PAYABLE;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(claimChequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadShortEndownmentClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.SHORT_ENDOWMENT_LIFE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_LIFE_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_LIFE_PAYABLE : null;

		/* Medical Fee */
		String medicalFeesCodeDr = COACode.SHORT_ENDOWMENT_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.SHORT_ENDOWMENT_MEDICAL_FEES_PAYABLE;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadStudentLifeClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.STUDENT_LIFE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_LIFE_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_LIFE_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.STUDENT_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.STUDENT_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadPublicTermLifeClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.PUBLIC_TERM_LIFE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.PUBLIC_TERM_LIFE_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.PUBLIC_TERM_LIFE_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadSnakebiteClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.SNAKE_BITE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.SNAKE_BITE_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.SNAKE_BITE_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadPersonalAccidentClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.PA_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PA_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PA_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.PA_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.PA_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadGroupLifeClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.GROUP_LIFE_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.GROUP_LIFE_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.GROUP_LIFE_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadSportManClaimTlfDataAccCode(PaymentChannel param) {
		TlfData tlfData = new TlfData();
		/* Pay */
		String claimCodeDr = COACode.SPORT_MAN_CLAIM;
		String claimCodeCr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_PAYABLE : null;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_PAYABLE : null;
		/* Medical Fee */
		String medicalFeesCodeDr = COACode.SPORT_MAN_MEDICAL_FEES;
		String medicalFeesCodeCr = COACode.SPORT_MAN_MEDICAL_FEES_PAYABLE;
		// String medicalFeesCodeCr = COACode.MEDICAL_FEES;

		tlfData.setClaimCodeDr(claimCodeDr);
		tlfData.setClaimCodeCr(claimCodeCr);
		tlfData.setClaimChequeCodeCr(chequeCodeCr);
		tlfData.setMedicalFeesCodeDr(medicalFeesCodeDr);
		tlfData.setMedicalFeesCodeCr(medicalFeesCodeCr);
		return tlfData;
	}

	private TlfData loadMotorTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.MOTOR_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MOTOR_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.MOTOR_SUNDRY : COACode.MOTOR_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.MOTOR_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MOTOR_RECEIVABLE : null;
		String servicesCodeCr = COACode.MOTOR_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MOTOR_RECEIVABLE : null;
		String stampCodeCr = COACode.MOTOR_STAMP_FEE;
		/* Co Insurance Outward */
		String coCodeDr = isCoOutward ? COACode.MOTOR_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.MOTOR_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.MOTOR_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.MOTOR_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.MOTOR_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.MOTOR_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;

	}

	private TlfData loadFireTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.FIRE_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.FIRE_SUNDRY : COACode.FIRE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String servicesCodeCr = COACode.FIRE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String stampCodeCr = COACode.FIRE_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.FIRE_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.FIRE_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.FIRE_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.FIRE_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.FIRE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.FIRE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadDeclarationTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.FIRE_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.FIRE_SUNDRY : COACode.FIRE_DECLARATION_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String servicesCodeCr = COACode.FIRE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIRE_RECEIVABLE : null;
		String stampCodeCr = COACode.FIRE_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.FIRE_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.FIRE_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.FIRE_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.FIRE_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.FIRE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.FIRE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadCargoTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? "" : PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CARGO_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.CARGO_SUNDRY : COACode.CARGO_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.CARGO_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CARGO_RECEIVABLE : null;
		String servicesCodeCr = COACode.CARGO_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CARGO_RECEIVABLE : null;
		String stampCodeCr = COACode.CARGO_STAMP_FEE;
		/* Co Insurance Outward */
		String coCodeDr = isCoOutward ? COACode.CARGO_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.CARGO_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? "" : null;
		String coCommCodeCr = isCoInward ? "" : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.CARGO_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.CARGO_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;

	}

	private TlfData loadMarineHullTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.FIRE_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MARINE_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.MARINE_SUNDRY : COACode.MARINE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.MARINE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MARINE_RECEIVABLE : null;
		String servicesCodeCr = COACode.MARINE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MARINE_RECEIVABLE : null;
		String stampCodeCr = COACode.FIRE_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.MARINE_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.MARINE_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.FIRE_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.FIRE_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.MARINE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.MARINE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadCashInTransitTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.CASH_IN_TRANSIT_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_TRANSIT_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.CASH_IN_TRANSIT_SUNDRY : COACode.CASH_IN_TRANSIT_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_TRANSIT_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_TRANSIT_RECEIVABLE : null;
		String servicesCodeCr = COACode.CASH_IN_TRANSIT_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_TRANSIT_RECEIVABLE : null;
		String stampCodeCr = COACode.CASH_IN_TRANSIT_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.CASH_IN_TRANSIT_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.CASH_IN_TRANSIT_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.CASH_IN_TRANSIT_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.CASH_IN_TRANSIT_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.CASH_IN_TRANSIT_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.CASH_IN_TRANSIT_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadCashInSafeTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.CASH_IN_SAFE_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_TRANSIT_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.CASH_IN_SAFE_SUNDRY : COACode.CASH_IN_SAFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_SAFE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_SAFE_RECEIVABLE : null;
		String servicesCodeCr = COACode.CASH_IN_SAFE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CASH_IN_SAFE_RECEIVABLE : null;
		String stampCodeCr = COACode.CASH_IN_SAFE_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.CASH_IN_SAFE_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.CASH_IN_SAFE_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.CASH_IN_SAFE_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.CASH_IN_SAFE_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.CASH_IN_SAFE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.CASH_IN_SAFE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadFidelityTlfDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? COACode.FIDELITY_COINWARD_RECEIVABLE
				: PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIDELITY_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? COACode.FIDELITY_SUNDRY : COACode.FIDELITY_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.FIDELITY_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIDELITY_RECEIVABLE : null;
		String servicesCodeCr = COACode.FIDELITY_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FIDELITY_RECEIVABLE : null;
		String stampCodeCr = COACode.FIDELITY_STAMP_FEES;
		/* Co Insurance */
		String coCodeDr = isCoOutward ? COACode.FIDELITY_SUNDRY : null;
		String coCodeCr = isCoOutward ? COACode.FIDELITY_PREMIUM : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? COACode.FIDELITY_COINWARD_COMMISSION : null;
		String coCommCodeCr = isCoInward ? COACode.FIDELITY_COINWARD_PAYABLE : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.FIDELITY_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.FIDELITY_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadShortEndownmentTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_RECEIVABLE : null;
		String premiumCodeCr = COACode.SHORT_ENDOWMENT_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_RECEIVABLE : null;
		String servicesCodeCr = COACode.SHORT_ENDOWMENT_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SHORT_ENDOWMENT_RECEIVABLE : null;
		String stampCodeCr = COACode.SHORT_ENDOWMENT_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.SHORT_ENDOWMENT_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.SHORT_ENDOWMENT_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}
	
	private TlfData loadGovernmentShortEndownmentTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GOVERNMENT_SHORT_ENDOWMENT_RECEIVABLE : null;
		String premiumCodeCr = COACode.GOVERNMENT_SHORT_ENDOWMENT_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.GOVERNMENT_SHORT_ENDOWMENT_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GOVERNMENT_SHORT_ENDOWMENT_RECEIVABLE : null;
		String servicesCodeCr = COACode.GOVERNMENT_SHORT_ENDOWMENT_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GOVERNMENT_SHORT_ENDOWMENT_RECEIVABLE : null;
		String stampCodeCr = COACode.GOVERNMENT_SHORT_ENDOWMENT_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.GOVERNMENT_SHORT_ENDOWMENT_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.GOVERNMENT_SHORT_ENDOWMENT_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadStudentLifeTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_RECEIVABLE : null;
		String premiumCodeCr = COACode.STUDENT_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_RECEIVABLE : null;
		String servicesCodeCr = COACode.STUDENT_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.STUDENT_RECEIVABLE : null;
		String stampCodeCr = COACode.STUDENT_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.STUDENT_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.STUDENT_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadFarmerTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FARMER_RECEIVABLE : null;
		String premiumCodeCr = COACode.FARMER_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.FARMER_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FARMER_RECEIVABLE : null;
		String servicesCodeCr = COACode.FARMER_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.FARMER_RECEIVABLE : null;
		String stampCodeCr = COACode.FARMER_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.FARMER_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.FARMER_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadPersonalAccidentTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PA_RECEIVABLE : null;
		String premiumCodeCr = COACode.PA_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PA_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PA_RECEIVABLE : null;
		String servicesCodeCr = COACode.PA_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PA_RECEIVABLE : null;
		String stampCodeCr = COACode.PA_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.PA_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.PA_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadSnakebiteTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_RECEIVABLE : null;
		String premiumCodeCr = COACode.SNAKE_BITE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_RECEIVABLE : null;
		String servicesCodeCr = COACode.SNAKE_BITE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SNAKE_BITE_RECEIVABLE : null;
		String stampCodeCr = COACode.SNAKE_BITE_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.SNAKE_BITE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.SNAKE_BITE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadGroupLifeTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_RECEIVABLE : null;
		String premiumCodeCr = COACode.GROUP_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_RECEIVABLE : null;
		String servicesCodeCr = COACode.GROUP_LIFE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.GROUP_LIFE_RECEIVABLE : null;
		String stampCodeCr = COACode.GROUP_LIFE_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.GROUP_LIFE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.GROUP_LIFE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadSportManTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_RECEIVABLE : null;
		String premiumCodeCr = COACode.SPORT_MAN_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_RECEIVABLE : null;
		String servicesCodeCr = COACode.SPORT_MAN_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SPORT_MAN_RECEIVABLE : null;
		String stampCodeCr = COACode.SPORT_MAN_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.SPORT_MAN_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.SPORT_MAN_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}
	
	private TlfData loadSeamanTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SEAMAN_RECEIVABLE : null;
		String premiumCodeCr = COACode.SEAMAN_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.SEAMAN_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SEAMAN_RECEIVABLE : null;
		String servicesCodeCr = COACode.SEAMAN_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.SEAMAN_RECEIVABLE : null;
		String stampCodeCr = COACode.SEAMAN_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.SEAMAN_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.SEAMAN_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadEndownmentLifeTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_RECEIVABLE : null;
		String premiumCodeCr = COACode.PUBLIC_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_RECEIVABLE : null;
		String servicesCodeCr = COACode.PUBLIC_LIFE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_LIFE_RECEIVABLE : null;
		String stampCodeCr = COACode.PUBLIC_LIFE_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.PUBLIC_LIFE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.PUBLIC_LIFE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadPublicTermLifeTlfDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_RECEIVABLE : null;
		String premiumCodeCr = COACode.PUBLIC_TERM_LIFE_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_RECEIVABLE : null;
		String servicesCodeCr = COACode.PUBLIC_TERM_LIFE_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PUBLIC_TERM_LIFE_RECEIVABLE : null;
		String stampCodeCr = COACode.PUBLIC_TERM_LIFE_STAMP_FEE;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.PUBLIC_TERM_LIFE_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.PUBLIC_TERM_LIFE_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadPersonTravelLifeTLFDataAccCode(PaymentChannel param, boolean isCoOutward, boolean isCoInward, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = isCoInward ? "" : PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PERSON_TRAVEL_RECEIVABLE : null;
		String premiumCodeCr = isCoOutward ? "" : COACode.GENERAL_TRAVEL_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.PERSON_TRAVEL_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PERSON_TRAVEL_RECEIVABLE : null;
		String servicesCodeCr = COACode.GENERAL_TRAVEL_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.PERSON_TRAVEL_RECEIVABLE : null;
		String stampCodeCr = COACode.STAMP_FEES;
		/* Co Insurance Outward */
		String coCodeDr = isCoOutward ? "" : null;
		String coCodeCr = isCoOutward ? "" : null;
		/* Co Insurance Inward */
		String coCommCodeDr = isCoInward ? "" : null;
		String coCommCodeCr = isCoInward ? "" : null;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.GENERAL_TRAVEL_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.PERSON_TRAVEL_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setCoCodeDr(coCodeDr);
		tlfData.setCoCodeCr(coCodeCr);
		tlfData.setCoCommCodeDr(coCommCodeDr);
		tlfData.setCoCommCodeCr(coCommCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadTravelLifeTLFDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.TRAVEL_RECEIVABLE : null;
		String premiumCodeCr = COACode.TRAVEL_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.TRAVEL_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.TRAVEL_RECEIVABLE : null;
		String servicesCodeCr = COACode.TRAVEL_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.TRAVEL_RECEIVABLE : null;
		String stampCodeCr = COACode.STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.TRAVEL_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.TRAVEL_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadHealthTLFDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.HEALTH_RECEIVABLE : null;
		String premiumCodeCr = COACode.HEALTH_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.HEALTH_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.HEALTH_RECEIVABLE : null;
		String servicesCodeCr = COACode.HEALTHL_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.HEALTH_RECEIVABLE : null;
		String stampCodeCr = COACode.HEALTHL_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.HEALTH_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.HEALTH_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadCriticalIllnessTLFDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CRITICAL_ILLNESS_RECEIVABLE : null;
		String premiumCodeCr = COACode.CRITICAL_ILLNESS_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.CRITICAL_ILLNESS_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CRITICAL_ILLNESS_RECEIVABLE : null;
		String servicesCodeCr = COACode.CRITICAL_ILLNESS_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.CRITICAL_ILLNESS_RECEIVABLE : null;
		String stampCodeCr = COACode.CRITICAL_ILLNESS_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.CRITICAL_ILLNESS_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.CRITICAL_ILLNESS_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	private TlfData loadMicroHealthTLFDataAccCode(PaymentChannel param, boolean isAgent) {
		TlfData tlfData = new TlfData();
		/* Premium */
		String premiumCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MICRO_HEALTH_RECEIVABLE : null;
		String premiumCodeCr = COACode.MICRO_HEALTH_PREMIUM;
		/* Cheque */
		String chequeCodeCr = PaymentChannel.CHEQUE.equals(param) ? COACode.MICRO_HEALTH_RECEIVABLE : null;
		/* Service Charges */
		String servicesCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MICRO_HEALTH_RECEIVABLE : null;
		String servicesCodeCr = COACode.MICRO_HEALTH_SERVICE_CHARGES;
		/* Stamp Fee */
		String stampCodeDr = PaymentChannel.CASHED.equals(param) ? COACode.CASH : PaymentChannel.CHEQUE.equals(param) ? COACode.MICRO_HEALTH_RECEIVABLE : null;
		String stampCodeCr = COACode.MICRO_HEALTH_STAMP_FEES;
		/* Agent */
		String agentCodeDr = isAgent ? COACode.MICRO_HEALTH_AGENT_COMMISSION : null;
		String agentCodeCr = isAgent ? COACode.MICRO_HEALTH_AGENT_PAYABLE : null;

		tlfData.setPremiumCodeDr(premiumCodeDr);
		tlfData.setPremiumCodeCr(premiumCodeCr);
		tlfData.setChequeCodeCr(chequeCodeCr);
		tlfData.setServicesCodeDr(servicesCodeDr);
		tlfData.setServicesCodeCr(servicesCodeCr);
		tlfData.setStampCodeDr(stampCodeDr);
		tlfData.setStampCodeCr(stampCodeCr);
		tlfData.setAgentCodeDr(agentCodeDr);
		tlfData.setAgentCodeCr(agentCodeCr);
		return tlfData;
	}

	/**
	 * AccCode for Claim
	 */

	private TlfData loadMotorClaimTlfDataAccCode() {
		TlfData tlfData = new TlfData();
		String claimOutstCodeDr = COACode.MOTOR_CLAIM_OUTSTANDING;
		String claimOutstCodeCr = COACode.MOTOR_CLAIM_OUTSTANDING;
		tlfData.setClaimOutstCodeDr(claimOutstCodeDr);
		tlfData.setClaimOutstCodeCr(claimOutstCodeCr);
		return tlfData;
	}

	@Override
	public TlfData getClaimMedicalFeesInstance(Payment payment, ClaimMedicalFees claimMedicalFees) throws SystemException {
		TlfData tlfData = null;
		try {
			/* load Data */
			tlfData = loadClaimMedicalFeesTlfDataAccCode(claimMedicalFees.getReferenceType(), payment);
			String customerId = null;
			String customerName = null;
			if (claimMedicalFees.getCustomer() != null) {
				customerId = claimMedicalFees.getCustomer().getId();
				customerName = claimMedicalFees.getCustomer().getFullName();
			} else {
				customerId = claimMedicalFees.getOrganization().getId();
				customerName = claimMedicalFees.getOrganization().getName();
			}
			tlfData.setCustomerId(customerId);
			tlfData.setCustomerName(customerName);
			tlfData.setPayment(payment);
			tlfData.getClaimMedicalFeesList().add(claimMedicalFees);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), "Faield to add a new TLF", e);
		}
		return tlfData;
	}

	private TlfData loadClaimMedicalFeesTlfDataAccCode(PolicyReferenceType refType, Payment payment) {
		TlfData tlfData = new TlfData();
		String medCodeCr = null;
		String medCodeDr = null;
		switch (refType) {
			case ENDOWNMENT_LIFE_CLAIM:
				medCodeDr = COACode.PUBLIC_LIFE_MEDICAL_FEES_PAYABLE;
				break;
			case SHORT_TERM_LIFE_CLAIM:
				medCodeDr = COACode.SHORT_ENDOWMENT_MEDICAL_FEES_PAYABLE;
				break;
			case SPORT_MAN_CLAIM:
				medCodeDr = COACode.SPORT_MAN_MEDICAL_FEES_PAYABLE;
				break;
			case PA_LIFE_CLAIM:
				medCodeDr = COACode.PA_MEDICAL_FEES_PAYABLE;
				break;
			case SNAKE_BITE_LIFE_CLAIM:
				medCodeDr = COACode.SNAKE_BITE_MEDICAL_FEES_PAYABLE;
				break;
			case GROUP_LIFE_CLAIM:
				medCodeDr = COACode.GROUP_LIFE_MEDICAL_FEES_PAYABLE;
				break;
			case STUDENT_LIFE_CLAIM:
				medCodeDr = COACode.STUDENT_MEDICAL_FEES_PAYABLE;
				break;
			default:
				break;
		}

		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			medCodeCr = payment.getAccountBank().getAcode();
		} else if (payment.getPaymentChannel().equals(PaymentChannel.CASHED)) {
			medCodeCr = COACode.CASH;
		}

		tlfData.setMedicalFeesCodeDr(medCodeDr);
		tlfData.setMedicalFeesCodeCr(medCodeCr);
		return tlfData;

	}
}
