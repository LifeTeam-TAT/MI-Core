package org.ace.insurance.life.proposalhistory.service;

import javax.annotation.Resource;

import org.ace.insurance.common.ProposalHistoryEntryType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposalhistory.LifeProposalHistory;
import org.ace.insurance.life.proposalhistory.LifeProposalInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.proposalhistory.LifeProposalInsuredPersonHistory;
import org.ace.insurance.life.proposalhistory.presistence.interfaces.ILifeProposalHistoryDAO;
import org.ace.insurance.life.proposalhistory.service.interfaces.ILifeProposalHistoryService;
import org.ace.insurance.product.Product;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.exception.CustomIDGeneratorException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeProposalHistoryService")
public class LifeProposalHistoryService extends BaseService implements ILifeProposalHistoryService {

	@Resource(name = "LifeProposalHistoryDAO")
	private ILifeProposalHistoryDAO lifeProposalHistoryDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposalHistory addNewLifeProposalHistory(LifeProposalHistory lifeProposalHistory, WorkFlowDTO workFlowDTO, String status) {
		try {
			LifeProposalHistory mpHistory = lifeProposalHistoryDAO.insert(lifeProposalHistory);
			workFlowDTO.setReferenceNo(mpHistory.getId());
			// FIXME
			workFlowDTO.setReferenceType(ReferenceType.ENDOWMENT_LIFE);
			workFlowDTOService.addNewWorkFlow(workFlowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposalHistory", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposalHistory", e);
		}
		return lifeProposalHistory;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifeProposalHistory(LifeProposal lifeProposal) {
		try {
			LifeProposalHistory mpHistory = new LifeProposalHistory(lifeProposal);
			if (workFlowDTOService.findWorkFlowByRefNo(lifeProposal.getId()).getWorkflowTask().toString().equalsIgnoreCase((WorkflowTask.PAYMENT).toString())) {
				mpHistory.setEntryType(ProposalHistoryEntryType.WORKFLOWCHANGER);
			} else {
				mpHistory.setEntryType(ProposalHistoryEntryType.UNDERWRITING);
			}
			mpHistory.setWorkflowTask(workFlowDTOService.findWorkFlowByRefNo(lifeProposal.getId()).getWorkflowTask());
			lifeProposalHistoryDAO.insert(mpHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposalHistory", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposalHistory", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposalHistory updateLifeProposalHistory(LifeProposalHistory lifeProposalHistory, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposalHistory.getLifeProposalInsuredPersonHistoryList().get(0).getProduct();
			// Custom ID GEN For Proposal Insured Person and Beneficiary
			for (LifeProposalInsuredPersonHistory person : lifeProposalHistory.getLifeProposalInsuredPersonHistoryList()) {
				if (person.getInsPersonCodeNo() == null) {
					insPersonCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_CODENO, null);
					person.setInsPersonCodeNo(insPersonCodeNo);
				}
				if (isGroupLife(product)) {
					if (person.getInPersonGroupCodeNo() == null) {
						inPersonGroupCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_GROUP_CODENO, null);
						person.setInPersonGroupCodeNo(inPersonGroupCodeNo);
					}
				}
				for (LifeProposalInsuredPersonBeneficiariesHistory beneficiary : person.getLifeProposalInsuredPersonBeneficiariesHistoryList()) {
					if (beneficiary.getBeneficiaryNo() == null) {
						beneficiaryNo = customIDGenerator.getNextId(SystemConstants.LIFE_BENEFICIARY_NO, null);
						beneficiary.setBeneficiaryNo(beneficiaryNo);
					}
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposalHistory", e);
		}
		return lifeProposalHistory;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifeProposalHistory(LifeProposalHistory lifeProposalHistory) {
		try {
			lifeProposalHistoryDAO.delete(lifeProposalHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifeProposalHistory", e);
		}
	}
}