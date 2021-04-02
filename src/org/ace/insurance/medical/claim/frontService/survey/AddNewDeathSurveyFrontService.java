package org.ace.insurance.medical.claim.frontService.survey;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.frontService.survey.interfaces.IAddNewDeathClaimSurveyFrontService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "AddNewDeathSurveyFrontService")
public class AddNewDeathSurveyFrontService extends BaseService implements IAddNewDeathClaimSurveyFrontService {

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * addNewDeathClaimSurvey(DeathClaimSurvey deathClaimSurvey, WorkFlowDTO
	 * workFlowDTO) { try { workFlowDTOService.updateWorkFlow(workFlowDTO);
	 * medicalDeathClaimDAO.insertDeathClaimSurvey(deathClaimSurvey);
	 * medicalDeathClaimDAO
	 * .addDeathAttachment(deathClaimSurvey.getDeathClaim()); } catch
	 * (DAOException e) { throw new SystemException(e.getErrorCode(),
	 * "Faield to add a new Hospitalized Calim Survey", e); } }
	 */

}
