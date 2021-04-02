package org.ace.insurance.payment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.NarrationHandler;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.TlfFactory;
import org.ace.insurance.payment.enums.DoubleEntry;
import org.ace.insurance.payment.enums.NarrationType;
import org.ace.insurance.payment.enums.TlfCategory;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.ITlfProcessor;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TlfProcessor")
public class TlfProcessor extends BaseService implements ITlfProcessor {
	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	private final String TRCredit = KeyFactorIDConfig.getTRCredit();
	private final String TRDebit = KeyFactorIDConfig.getTRDebit();
	private final String CSCredit = KeyFactorIDConfig.getCSCredit();
	private final String CSDebit = KeyFactorIDConfig.getCSDebit();

	@Transactional(propagation = Propagation.REQUIRED)
	public void handleTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException {
		try {
			List<TLF> tlfList = new ArrayList<>();
			List<TLF> tempList = null;
			String narration = null;
			NarrationHandler handler = null;
			for (TlfData data : dataList) {
				handler = new NarrationHandler(data);

				/* Premium Tlf */
				narration = handler.getInstance(NarrationType.PREMIUM);
				tempList = generatePremiumTlf(data, branch, narration);
				tlfList.addAll(tempList);

				if (KeyFactorIDConfig.isReceivableSalPoint() && data.getReceivableDr() != null) {
					narration = handler.getInstance(NarrationType.RECEIVABLE_SP);
					tempList = generateSPReceivableTlf(data, branch, narration);
					tlfList.addAll(tempList);
				}

				/* Co Premium Tlf */
				if (data.getCoCodeCr() != null) {
					narration = handler.getInstance(NarrationType.CO_RETAIN_PREMIUM);
					tempList = generateCoRetainPremiumTlf(data, branch, narration);
					tlfList.addAll(tempList);
				}

				/* Agent Commission Payable Tlf */
				if (!data.getAgentCommissionList().isEmpty()) {
					tempList = generateAgentCommissionTlf(data, branch);
					tlfList.addAll(tempList);
				}

				/* Service Charges Tlf */
				if (data.getPayment().getServicesCharges() > 0) {
					narration = handler.getInstance(NarrationType.SERVICE_CHARGES);
					tempList = generateServiceChargesTlf(data, branch, narration);
					tlfList.addAll(tempList);
				}

				/* Administration fee Tlf */
				if (data.getPayment().getAdministrationFees() > 0) {
					narration = handler.getInstance(NarrationType.SERVICE_CHARGES);
					tempList = generateAdministrationFeeTlf(data, branch, narration);
					tlfList.addAll(tempList);
				}

				/* Stamp fee Tlf */
				if (data.getPayment().getStampFees() > 0) {
					narration = handler.getInstance(NarrationType.STAMP_FEE);
					tempList = generateStampTlf(data, branch, narration);
					tlfList.addAll(tempList);
				}

				/** Co Inward Commission */
				if (data.getCoCommCodeCr() != null) {
					narration = handler.getInstance(NarrationType.CO_COMMISSION);
					tempList = generateCoInwardCommTLF(data, branch, narration);
					tlfList.addAll(tempList);
				}
			}

			reverseNegativeTlf(tlfList);
			paymentDAO.insertTLFList(tlfList);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add Tlf for premium.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void handleLifeClaimTLFProcess(List<TlfData> dataList, Branch branch) throws SystemException {
		try {
			List<TLF> tlfList = new ArrayList<>();
			List<TLF> tempList = null;
			String narration = null;
			NarrationHandler handler = null;
			for (TlfData data : dataList) {
				handler = new NarrationHandler(data);

				narration = handler.getInstance(NarrationType.LIFE_CLAIM);
				tempList = generateClaimTlf(data, branch, narration);
				tlfList.addAll(tempList);

				if (data.getPayment().getMedicalFees() > 0) {
					narration = handler.getInstance(NarrationType.MEDICAL_FEES);
					tempList = generateMedicalFeesTlf(data, branch, narration, TlfCategory.MEDICAL_FEES_TLF);
					tlfList.addAll(tempList);
				}
			}
			paymentDAO.insertTLFList(tlfList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add Tlf for premium.", e);
		}
	}

	/** used for Agent Commission Payment */
	@Transactional(propagation = Propagation.REQUIRED)
	public void handleAgentCommTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException {
		try {
			List<TLF> tlfList = new ArrayList<>();
			List<TLF> tempList = null;
			for (TlfData data : dataList) {
				/* Agent Commission Tlf */
				if (!data.getAgentCommissionList().isEmpty()) {
					tempList = generatePaymentAgentCommissionTlf(data, branch);
					tlfList.addAll(tempList);
				}
			}

			reverseNegativeTlf(tlfList);
			paymentDAO.insertTLFList(tlfList);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add Tlf for premium.", e);
		}
	}

	private List<TLF> generateSPReceivableTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			if (PaymentChannel.CASHED.equals(paymentChannel)) {
				String currencyId = data.getPayment().getCurrency().getId();
				String branchId = branch.getId();

				/* Debit Tlf */
				String ccoaId = null;
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getReceivableDr(), branchId, currencyId);
				TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.RECEIVABLE_SP_TLF);
				tlf = factory.getInstance();
				tlf.setPayableTran(true);
				result.add(tlf);

				/* Credit Tlf */
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getPremiumCodeDr(), branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.RECEIVABLE_SP_TLF);
				tlf = factory.getInstance();
				tlf.setPayableTran(true);
				result.add(tlf);
			}
		} catch (DAOException exc) {
			throw new SystemException(exc.getErrorCode(), "Generate tlf for Receivable salePoint.", exc);
		}
		return result;
	}

	private List<TLF> generatePremiumTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			if (PaymentChannel.TRANSFER.equals(paymentChannel) && data.getPayment().getAccountBank() != null)
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
			else
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getPremiumCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.PREMIUM_TLF);
			tlf = factory.getInstance();
			result.add(tlf);

			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getPremiumCodeCr(), branchId, currencyId);
			factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.PREMIUM_TLF);
			tlf = factory.getInstance();
			result.add(tlf);

			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				/* Debit Tlf */
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.PREMIUM_TLF);
				tlf = factory.getInstance();
				tlf.setClearing(true);
				tlf.setPayableTran(true);
				tlf.setPaid(false);
				result.add(tlf);
				/* Credit Tlf */
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getChequeCodeCr(), branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.PREMIUM_TLF);
				tlf = factory.getInstance();
				tlf.setClearing(true);
				tlf.setPayableTran(true);
				tlf.setPaid(false);
				result.add(tlf);
			}
		} catch (DAOException exc) {
			throw new SystemException(exc.getErrorCode(), "Generate tlf for Premium.", exc);
		}
		return result;
	}

	private List<TLF> generateClaimTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getClaimCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.LIFE_CLAIM_TLF);
			tlf = factory.getInstance();
			tlf.setHomeAmount(tlf.getLocalAmount());
			tlf.setCurrencyId(currencyId);
			result.add(tlf);
			if (PaymentChannel.TRANSFER.equals(paymentChannel) && data.getPayment().getAccountBank() != null) {
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.LIFE_CLAIM_TLF);
				tlf = factory.getInstance();
				tlf.setCurrencyId(currencyId);
				result.add(tlf);
			} else {
				/* Credit Tlf */
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getClaimCodeCr(), branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.LIFE_CLAIM_TLF);
				tlf = factory.getInstance();
				tlf.setCurrencyId(currencyId);
				result.add(tlf);
			}

			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				/* Debit Tlf */
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.LIFE_CLAIM_TLF);
				tlf = factory.getInstance();
				tlf.setClearing(true);
				tlf.setPayableTran(true);
				tlf.setPaid(false);
				tlf.setCurrencyId(currencyId);
				result.add(tlf);
				/* Credit Tlf */
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getClaimChequeCodeCr(), branchId, currencyId);
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.LIFE_CLAIM_TLF);
				tlf = factory.getInstance();

				tlf.setClearing(true);
				tlf.setPayableTran(true);
				tlf.setPaid(false);
				result.add(tlf);
			}
		} catch (

		DAOException exc) {
			throw new SystemException(exc.getErrorCode(), "Generate tlf for Premium.", exc);
		}
		return result;
	}

	private List<TLF> generateCoRetainPremiumTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			/* Debit Tlf */
			String ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getCoCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(TRDebit, data, branchId, ccoaId, narration, TlfCategory.CO_PREMIUM_TLF);
			tlf = factory.getInstance();
			result.add(tlf);
			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getCoCodeCr(), branchId, currencyId);
			factory = new TlfFactory(TRCredit, data, branchId, ccoaId, narration, TlfCategory.CO_PREMIUM_TLF);
			tlf = factory.getInstance();
			result.add(tlf);

		} catch (DAOException exc) {
			throw new SystemException(exc.getErrorCode(), "Generate tlf for Premium Credit(Co-insurance).", exc);
		}
		return result;
	}

	private List<TLF> generateAgentCommissionTlf(TlfData data, Branch branch) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			/* Debit Tlf */
			String coaCodeDr = paymentDAO.findCheckOfAccountNameByCode(data.getAgentCodeDr(), branchId, currencyId);
			/* Credit Tlf */
			String coaCodeCr = paymentDAO.findCheckOfAccountNameByCode(data.getAgentCodeCr(), branchId, currencyId);
			for (AgentCommission agentComm : data.getAgentCommissionList()) {
				NarrationHandler handler = new NarrationHandler(data, agentComm);
				String narration = handler.getInstance(NarrationType.AGENT_COMMISSION);
				/* Debit Tlf */
				TlfFactory factory = new TlfFactory(TRDebit, data, agentComm, branchId, coaCodeDr, narration, TlfCategory.AGENTCOMMISION_TLF);
				tlf = factory.getInstance();
				tlf.setPayableTran(true);
				result.add(tlf);
				/* Credit Tlf */
				factory = new TlfFactory(TRCredit, data, agentComm, branchId, coaCodeCr, narration, TlfCategory.AGENTCOMMISION_TLF);
				tlf = factory.getInstance();
				tlf.setPayableTran(true);
				result.add(tlf);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for agent commission.", e);
		}
		return result;
	}

	private List<TLF> generatePaymentAgentCommissionTlf(TlfData data, Branch branch) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			String branchId = branch.getId();
			Payment payment = data.getPayment();
			for (AgentCommission agentComm : data.getAgentCommissionList()) {
				NarrationHandler handler = new NarrationHandler(data, agentComm);
				String narration = handler.getInstance(NarrationType.PAYMENT_AGENT_COMMISSION);

				String currencyId = agentComm.getCurrency().getId();
				/* Debit Tlf */
				String coaCodeDr = paymentDAO.findCheckOfAccountNameByCode(data.getAgentCodeDr(), branchId, currencyId);
				TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, agentComm, branchId, coaCodeDr, narration, TlfCategory.AGCOMMISSION_PAYMENT_TLF);
				tlf = factory.getInstance();
				result.add(tlf);
				/* Credit Tlf */
				String coaCodeCr;
				if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
					coaCodeCr = paymentDAO.findCCOAByCode(data.getAgentCodeCr(), branchId, currencyId);
				} else {
					coaCodeCr = paymentDAO.findCheckOfAccountNameByCode(data.getAgentCodeCr(), branchId, currencyId);
				}
				factory = new TlfFactory(DoubleEntry.CREDIT, data, agentComm, branchId, coaCodeCr, narration, TlfCategory.AGCOMMISSION_PAYMENT_TLF);
				tlf = factory.getInstance();
				result.add(tlf);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for agent commission.", e);
		}
		return result;
	}

	private List<TLF> generateServiceChargesTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			if (paymentChannel.equals(PaymentChannel.TRANSFER))
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
			else
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getServicesCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.SERVICE_CHARGES_TLF);
			tlf = factory.getInstance();
			result.add(tlf);

			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getServicesCodeCr(), branchId, currencyId);
			factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.SERVICE_CHARGES_TLF);
			tlf = factory.getInstance();
			result.add(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for service charges.", e);
		}
		return result;
	}

	private List<TLF> generateAdministrationFeeTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			if (paymentChannel.equals(PaymentChannel.TRANSFER))
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
			else
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getServicesCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.ADMINIS_FEE);
			tlf = factory.getInstance();
			result.add(tlf);

			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getServicesCodeCr(), branchId, currencyId);
			factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.ADMINIS_FEE);
			tlf = factory.getInstance();
			result.add(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for service charges.", e);
		}
		return result;
	}

	private List<TLF> generateStampTlf(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			/* Stamp Fee is Myanmar Kyat Only */
			String currencyId = KeyFactorIDConfig.getKYTCurrencyId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			if (paymentChannel.equals(PaymentChannel.TRANSFER))
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
			else
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getStampCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, ccoaId, narration, TlfCategory.STAMP_TLF);
			tlf = factory.getInstance();
			tlf.setRate(1);
			tlf.setHomeAmount(tlf.getLocalAmount());
			tlf.setCurrencyId(currencyId);
			result.add(tlf);

			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getStampCodeCr(), branchId, currencyId);
			factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, ccoaId, narration, TlfCategory.STAMP_TLF);
			tlf = factory.getInstance();
			tlf.setRate(1);
			tlf.setHomeAmount(tlf.getLocalAmount());
			tlf.setCurrencyId(currencyId);
			result.add(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for stamp fee.", e);
		}
		return result;
	}

	private List<TLF> generateMedicalFeesTlf(TlfData data, Branch branch, String narration, TlfCategory tlfCategory) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			PaymentChannel paymentChannel = data.getPayment().getPaymentChannel();
			/* Stamp Fee is Myanmar Kyat Only */
			String currencyId = KeyFactorIDConfig.getKYTCurrencyId();
			String branchId = branch.getId();
			String acCode = data.getPayment().getAccountBank() != null ? data.getPayment().getAccountBank().getAcode() : "";

			/* Debit Tlf */
			String ccoaId = null;
			if (paymentChannel.equals(PaymentChannel.TRANSFER))
				ccoaId = paymentDAO.findCCOAByCode(acCode, branchId, currencyId);
			else
				ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getMedicalFeesCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(TRDebit, data, branchId, ccoaId, narration, tlfCategory);
			tlf = factory.getInstance();
			tlf.setHomeAmount(tlf.getLocalAmount());
			tlf.setCurrencyId(currencyId);
			result.add(tlf);

			/* Credit Tlf */
			ccoaId = paymentDAO.findCheckOfAccountNameByCode(data.getMedicalFeesCodeCr(), branchId, currencyId);
			factory = new TlfFactory(TRCredit, data, branchId, ccoaId, narration, tlfCategory);
			tlf = factory.getInstance();
			tlf.setRate(1);
			tlf.setHomeAmount(tlf.getLocalAmount());
			tlf.setCurrencyId(currencyId);
			result.add(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for Medical fee.", e);
		}
		return result;
	}

	private List<TLF> reverseNegativeTlf(List<TLF> tlfList) {
		String tranTypeId = null;
		for (TLF tlf : tlfList) {
			if (tlf.getLocalAmount() < 0) {
				tlf.setLocalAmount(Math.abs(tlf.getLocalAmount()));
				tlf.setHomeAmount(Math.abs(tlf.getHomeAmount()));
				tranTypeId = tlf.getTranTypeId();
				/* Reverse Tran Type **/
				if (tranTypeId.equals(CSCredit))
					tranTypeId = CSDebit;
				else if (tranTypeId.equals(CSDebit))
					tranTypeId = CSCredit;
				else if (tranTypeId.equals(TRDebit))
					tranTypeId = TRCredit;
				else if (tranTypeId.equals(TRCredit))
					tranTypeId = TRDebit;

				tlf.setTranTypeId(tranTypeId);
			}
		}
		return tlfList;
	}

	private List<TLF> generateCoInwardCommTLF(TlfData data, Branch branch, String narration) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			String currencyId = data.getPayment().getCurrency().getId();
			String branchId = branch.getId();
			String coaCode = null;

			/* Debit Tlf */
			coaCode = paymentDAO.findCheckOfAccountNameByCode(data.getCoCommCodeDr(), branchId, currencyId);
			TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, coaCode, narration, TlfCategory.CO_COMMISION_TLF);
			tlf = factory.getInstance();
			result.add(tlf);

			/* Credit Tlf */
			coaCode = paymentDAO.findCheckOfAccountNameByCode(data.getCoCommCodeCr(), branchId, currencyId);
			factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, coaCode, narration, TlfCategory.CO_COMMISION_TLF);
			tlf = factory.getInstance();
			result.add(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for Co inward commission.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void handleClaimMedicalFeesTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException {
		try {
			List<TLF> tlfList = new ArrayList<>();
			List<TLF> tempList = null;
			NarrationHandler handler = null;
			for (TlfData data : dataList) {
				if (!data.getClaimMedicalFeesList().isEmpty()) {
					handler = new NarrationHandler(data);
					tempList = generatePaymentMedicalFeesTlf(data, branch, TlfCategory.MEDICAL_FEES_PAYMENT_TLF);
					tlfList.addAll(tempList);
				}
			}

			reverseNegativeTlf(tlfList);
			paymentDAO.insertTLFList(tlfList);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add Tlf for premium.", e);
		}
	}

	private List<TLF> generatePaymentMedicalFeesTlf(TlfData data, Branch branch, TlfCategory tlfCategory) {
		List<TLF> result = new ArrayList<>();
		TLF tlf = null;
		try {
			String branchId = branch.getId();
			Payment payment = data.getPayment();
			for (ClaimMedicalFees medFees : data.getClaimMedicalFeesList()) {
				NarrationHandler handler = new NarrationHandler(data, medFees);
				String narration = handler.getInstance(NarrationType.MEDICAL_FEES_PAYMENT);
				String currencyId = medFees.getCurrency().getId();
				/* Debit Tlf */
				String coaCodeDr = paymentDAO.findCheckOfAccountNameByCode(data.getMedicalFeesCodeDr(), branchId, currencyId);
				TlfFactory factory = new TlfFactory(DoubleEntry.DEBIT, data, branchId, coaCodeDr, narration, tlfCategory);
				tlf = factory.getInstance();
				result.add(tlf);
				/* Credit Tlf */
				String coaCodeCr;
				if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
					coaCodeCr = paymentDAO.findCCOAByCode(data.getMedicalFeesCodeCr(), branchId, currencyId);
				} else {
					coaCodeCr = paymentDAO.findCheckOfAccountNameByCode(data.getMedicalFeesCodeCr(), branchId, currencyId);
				}
				factory = new TlfFactory(DoubleEntry.CREDIT, data, branchId, coaCodeCr, narration, tlfCategory);
				tlf = factory.getInstance();
				result.add(tlf);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Generate tlf for agent commission.", e);
		}
		return result;
	}

}
