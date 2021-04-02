package org.ace.insurance.life.proposal.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.LPL001;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalTemp;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonInfoDTO;
import org.ace.insurance.web.mobileforlife.LifeProposalDTO;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.IDGen;

public interface ILifeProposalService {
	public LifeProposal calculatePremium(LifeProposal lifeProposal);

	public void calculateTermPremium(LifeProposal lifeProposal);
	
	public LifeProposal addNewLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status);

	public LifeProposal renewalGroupLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status);

	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO);

	public void rejectLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO);

	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status);
	
	public List<Payment> confirmSkipPaymentTLFLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status);

	public void informProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status);

	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> payment, Branch userBranch, String status);

	public LifeProposal updateLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) throws SystemException;

	public void issueLifeProposal(LifeProposal lifeProposal);

	public void deleteLifeProposal(LifeProposal lifeProposal);

	public LifeProposal findLifeProposalById(String id);

	public List<LifeProposal> findLifeProposal(List<String> proposalIdList);

	public List<LifeProposal> findByDate(Date startDate, Date endDate);

	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO);

	public LifeProposal updateLifeProposal(LifeProposal lifeProposal);

	public List<LifeProposal> findAllLifeProposal();

	public List<LPL001> findLifeProposalByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList);

	public LifeProposal findLifePortalById(String id);

	public ProposalInsuredPerson findProposalInsuredPersonById(String id);

	public LifeProposal overWriteLifeProposal(LifeProposal lifeProposal);

	public LifeSurvey findSurveyByProposalId(String proposalId);

	public void deletePayment(LifePolicy lifePolicy, WorkFlowDTO workFlowDTO);

	public List<PolicyInsuredPerson> findPolicyInsuredPersonByParameters(Name name, String idNo, String fatherName, Date dob);

	public String findStatusById(String id);

	public void updatePortalStatus(String status, String id);

	public LifeProposal findLifeProposalByProposalNo(String proposalNo);

	public void updateCodeNo(List<ProposalInsuredPerson> proposalPersonList, List<PolicyInsuredPerson> policyPersonList, List<InsuredPersonBeneficiaries> proposalBeneList,
			List<PolicyInsuredPersonBeneficiaries> policyBeneList, List<IDGen> idGenList);

	public void updateLifeProposalAttachment(LifeProposal lifeProposal);

	public boolean findOverMaxSIByMotherNameAndNRCAndInsuNameAndNRC(LifeProposal lifeProposal, InsuredPersonInfoDTO insuredPersonInfoDTO);
	
	public List<LifeProposalDTO> findLifeProposal();
	
	public LifeProposalTemp findLifeProposalByProposalNoFromTerm(String proposalNo);

}
