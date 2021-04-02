package org.ace.insurance.cashreceipt.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.cashreceipt.LifeCashReceiptListReportDTO;
import org.ace.insurance.cashreceipt.persistence.interfaces.ICashReceiptDAO;
import org.ace.insurance.cashreceipt.service.interfaces.ICashReceiptService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.CoinsuranceCashReceiptDTO;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CashReceiptService")
public class CashReceiptService implements ICashReceiptService {

	
	
	@Resource(name = "LifeProposalService")
	private ILifeProposalService lifeProposalService;

	@Resource(name = "CashReceiptDAO")
	private ICashReceiptDAO cashReceiptDAO;

	@Resource(name = "AcceptedInfoService")
	private IAcceptedInfoService acceptedInfoService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CashReceiptDTO> findConfirmationList(CashReceiptCriteria criteria, User user) throws SystemException {
		List<CashReceiptDTO> ret = null;
		try {
			switch (criteria.getReferenceType()) {
				
				case ENDOWMENT_LIFE: {
					// lifeConfirmationList = proxyService.find_LIF001(new
					// WorkflowCriteria(ReferenceType.LIFE_PROPOSAL,
					// WorkflowTask.CONFIRMATION, user), false);
					ret = cashReceiptDAO.findLifeConfirmationList(criteria, user);
					break;
				}
				case AGENT_COMMISSION:
					break;
				
				case LIFE_DIS_CLAIM:
					break;
				
				default:
					break;
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Confirmation List by CashReceiptCriteria", e);
		}
		return ret;
	}

	

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeCashReceiptListReportDTO> confirmLifeProposalsForCashReceipt(List<CashReceiptDTO> cashReceiptDTOList, ReferenceType type, PaymentDTO payment,
			User responsiblePerson, User currentUser, String remark) throws SystemException {
		List<LifeCashReceiptListReportDTO> result = new ArrayList<LifeCashReceiptListReportDTO>();
		WorkFlowDTO workFlowDTO = new WorkFlowDTO(null, currentUser.getLoginBranch().getId(), remark, WorkflowTask.PAYMENT, ReferenceType.ENDOWMENT_LIFE,
				TransactionType.UNDERWRITING, currentUser, responsiblePerson);
		for (CashReceiptDTO cashReceiptDTO : cashReceiptDTOList) {
			LifeProposal proposal = lifeProposalService.findLifeProposalById(cashReceiptDTO.getId());
			// prepare payment fields before confirmation
			// payment = prepareBeforeConfirm(proposal.getId(), payment, type);
			workFlowDTO.setReferenceNo(proposal.getId());
			// TODO add status for portal.
			List<Payment> paymentList = lifeProposalService.confirmLifeProposal(proposal, workFlowDTO, payment, userProcessService.getLoginUser().getBranch(), "");

			PaymentDTO paymentDTO = new PaymentDTO(paymentList);
			// CoinsuranceCashReceiptDTO coinsuranceCashReceipt =
			// paymentService.getCoinsuranceCashReceipt(proposal,
			// InsuranceType.LIFE, proposal.getBranch()).get(0);
			result.add(new LifeCashReceiptListReportDTO(proposal, paymentDTO, new CoinsuranceCashReceiptDTO()));
		}
		return result;
	}

}
