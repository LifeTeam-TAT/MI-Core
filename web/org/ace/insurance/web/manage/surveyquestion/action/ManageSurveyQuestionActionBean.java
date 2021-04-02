package org.ace.insurance.web.manage.surveyquestion.action;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.medical.surveyquestion.InputType;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.medical.surveyquestion.frontservice.interfaces.ISurveyQuestionFrontService;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.surveyquestion.ResourceQuestionDTO;
import org.ace.insurance.web.manage.surveyquestion.SurveyQuestionDTO;
import org.ace.insurance.web.manage.surveyquestion.factory.SurveyQuestionFactory;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageSurveyQuestionActionBean")
public class ManageSurveyQuestionActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{SurveyQuestionService}")
	private ISurveyQuestionService surveyQuestionService;

	public void setSurveyQuestionService(ISurveyQuestionService surveyQuestionService) {
		this.surveyQuestionService = surveyQuestionService;
	}

	@ManagedProperty(value = "#{SurveyQuestionValidator}")
	private DTOValidator<SurveyQuestionDTO> surveyQuestionValidator;

	public void setSurveyQuestionValidator(DTOValidator<SurveyQuestionDTO> surveyQuestionValidator) {
		this.surveyQuestionValidator = surveyQuestionValidator;
	}

	@ManagedProperty(value = "#{SurveyQuestionFrontService}")
	private ISurveyQuestionFrontService surveyQuestionFrontService;

	public void setSurveyQuestionFrontService(ISurveyQuestionFrontService surveyQuestionFrontService) {
		this.surveyQuestionFrontService = surveyQuestionFrontService;
	}

	private SurveyQuestionDTO surveyQuestion;
	private ResourceQuestionDTO resourceQuestion;
	private List<SurveyQuestion> surveyQuestionList;
	private boolean createNewSurveyQuestion;
	private boolean createNewResourceQuestion;
	private boolean isSelectionInput;
	private boolean isBoolenInput;
	private boolean isNumberInput;
	private boolean isDateInput;

	@PostConstruct
	public void init() {
		createSurveyQuestion();
	}

	public void createSurveyQuestion() {
		surveyQuestion = new SurveyQuestionDTO();
		surveyQuestion.setInputType(InputType.TEXT);
		createNewSurveyQuestion = true;
		createNewResourceQuestion();
		surveyQuestionList = surveyQuestionService.findAllSurveyQuestion();
		valueChangeEvent();
	}

	public void valueChangeEvent() {
		isSelectionInput = false;
		isBoolenInput = false;
		isNumberInput = false;
		isDateInput = false;
		switch (surveyQuestion.getInputType()) {
			case BOOLEAN:
				isBoolenInput = true;
				break;
			case SELECT_MANY_CHECKBOX:
			case SELECT_ONE_MENU:
			case SELECT_MANY_MENU:
			case SELECT_ONE_RADIO:
				isSelectionInput = true;
				break;
			case TEXT:
			case NUMBER:
				isNumberInput = true;
				break;
			default:
				break;
		}

	}

	public void createNewResourceQuestion() {
		resourceQuestion = new ResourceQuestionDTO();
		createNewResourceQuestion = true;
	}

	public void prepareUpdateSurveyQuestion(SurveyQuestion sQuestion) {
		this.surveyQuestion = SurveyQuestionFactory.getSurveyQuestionDTO(sQuestion);
		createNewSurveyQuestion = false;
		valueChangeEvent();
	}

	public void prepareDeleteSurveyQuestion(SurveyQuestion sQuestion) {
		this.surveyQuestion = SurveyQuestionFactory.getSurveyQuestionDTO(sQuestion);
	}

	public void addNewSurveyQuestion() {
		try {
			ValidationResult result = surveyQuestionValidator.validate(surveyQuestion);
			if (result.isVerified()) {
				SurveyQuestion entitySurveyQuestion = SurveyQuestionFactory.getSurveyQuestion(surveyQuestion);
				surveyQuestionFrontService.addNewSurveyQuestion(entitySurveyQuestion);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, entitySurveyQuestion.getId());
				createSurveyQuestion();
			} else {
				for (ErrorMessage message : result.getErrorMeesages()) {
					addErrorMessage(message.getId(), message.getErrorcode(), message.getParams());
				}
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateSurveyQuestion() {
		try {
			ValidationResult result = surveyQuestionValidator.validate(surveyQuestion);
			if (result.isVerified()) {
				SurveyQuestion entitySurveyQuestion = SurveyQuestionFactory.getSurveyQuestion(surveyQuestion);
				surveyQuestionFrontService.updateSurveyQuestion(entitySurveyQuestion);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, surveyQuestion.getId());
				createSurveyQuestion();
			} else {
				for (ErrorMessage message : result.getErrorMeesages()) {
					addErrorMessage(message.getId(), message.getErrorcode(), message.getParams());
				}
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private SurveyQuestion tempSurveyQuestion;

	public void loadTempSurveyQuestion(SurveyQuestion tempSurveyQuestion) {
		this.tempSurveyQuestion = tempSurveyQuestion;
	}

	public String deleteSurveyQuestion() {
		try {
			if (surveyQuestionFrontService.deleteSurveyQuestion(tempSurveyQuestion)) {
				addInfoMessage(null, MessageId.DELETE_SUCCESS, tempSurveyQuestion.getId());
			} else {
				addInfoMessage(null, MessageId.USE_SURVEYQUESTION_INFO, null);
			}
			createSurveyQuestion();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public void addNewResourceQuestion() {
		if (validateResourceQuestion()) {
			surveyQuestion.addResourceQuestion(resourceQuestion);
			createNewResourceQuestion();
		}
	}

	public void updateResourceQuestion() {
		if (validateResourceQuestion()) {
			for (ResourceQuestionDTO resourceQuestionDTO : surveyQuestion.getResourceQuestionList()) {
				if (resourceQuestionDTO.getId().equals(resourceQuestion.getId())) {
					surveyQuestion.removeResourceQuestion(resourceQuestionDTO);
					break;
				}
			}
			surveyQuestion.addResourceQuestion(resourceQuestion);
			createNewResourceQuestion();
		}
	}

	public boolean validateResourceQuestion() {
		String formID = "surveyQuestionEntryForm";
		boolean result = true;
		if (resourceQuestion.getName().isEmpty() || resourceQuestion.getName() == null) {
			addErrorMessage(formID + ":resourceName", UIInput.REQUIRED_MESSAGE_ID);
			result = false;
		}
		return result;
	}

	public void deleteResourceQuestion(ResourceQuestionDTO resourceQuestion) {
		surveyQuestion.removeResourceQuestion(resourceQuestion);
		createNewResourceQuestion();
	}

	public void prepareUpdateResourceQuestion(ResourceQuestionDTO resourceQuestion) {
		createNewResourceQuestion = false;
		this.resourceQuestion = resourceQuestion;
	}

	public SurveyQuestionDTO getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestionDTO surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public InputType[] getInputTypeList() {
		return InputType.values();
	}

	public ResourceQuestionDTO getResourceQuestion() {
		return resourceQuestion;
	}

	public void setResourceQuestion(ResourceQuestionDTO resourceQuestion) {
		this.resourceQuestion = resourceQuestion;
	}

	public boolean isCreateNewSurveyQuestion() {
		return createNewSurveyQuestion;
	}

	public boolean isCreateNewResourceQuestion() {
		return createNewResourceQuestion;
	}

	public boolean isSelectionInput() {
		return isSelectionInput;
	}

	public boolean isBoolenInput() {
		return isBoolenInput;
	}

	public boolean isNumberInput() {
		return isNumberInput;
	}

	public boolean isDateInput() {
		return isDateInput;
	}

	public List<SurveyQuestion> getSurveyQuestionList() {
		return surveyQuestionList;
	}
}
