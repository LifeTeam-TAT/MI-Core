package org.ace.insurance.medical.claim.frontService.survey;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.frontService.survey.interfaces.IAddNewHospitalizedClaimSurveyFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalHospitalizedClaimDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "AddNewHospitalizedSurveyFrontService")
public class AddNewHospitalizedSurveyFrontService extends BaseService implements IAddNewHospitalizedClaimSurveyFrontService {

	@Resource(name = "MedicalHospitalizedClaimDAO")
	private IMedicalHospitalizedClaimDAO medicalHospitalizedClaimDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * addNewHospitalizedClaimSurvey(HospitalizedClaimSurvey
	 * hospitalizedClaimSurvey, WorkFlowDTO workFlowDTO) { try {
	 * workFlowDTOService.updateWorkFlow(workFlowDTO);
	 * medicalHospitalizedClaimDAO
	 * .insertHospitalizedClaimSurvey(hospitalizedClaimSurvey);
	 * medicalHospitalizedClaimDAO
	 * .updateHosPersonMedicalStatus(hospitalizedClaimSurvey
	 * .getHospitalizedClaim().getHospitalizedPerson());
	 * medicalHospitalizedClaimDAO
	 * .addHospitalizedAttachment(hospitalizedClaimSurvey
	 * .getHospitalizedClaim()); } catch (DAOException e) { throw new
	 * SystemException(e.getErrorCode(),
	 * "Faield to add a new Hospitalized Calim Survey", e); } }
	 */

}
