package org.ace.insurance.web.manage.life.survey;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ProductProcessIDConfig;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.proposal.ClassificationOfHealth;
import org.ace.insurance.life.proposal.InsuredPersonAttachment;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalAttachment;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.proposal.SurveyType;
import org.ace.insurance.medical.surveyAnswer.ResourceQuestionAnswer;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "AddNewLifeSurveyActionBean")
public class AddNewLifeSurveyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{SurveyQuestionService}")
	private ISurveyQuestionService surveyQuestionService;

	public void setSurveyQuestionService(ISurveyQuestionService surveyQuestionService) {
		this.surveyQuestionService = surveyQuestionService;
	}

	private User user;
	private LifeSurvey lifeSurvey;
	private Map<String, String> proposalUploadedFileMap;
	private Map<String, ProposalInsuredPerson> personMap;
	private Map<String, Map<String, String>> personAttchmentMap;
	private boolean showEntry;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isShortTermEndowment;
	private boolean isGovernmentShortTermEndowment;
	private boolean isPublicTermLife;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isSeaman;
	private boolean isAllowApprove;
	private String remark;
	private User responsiblePerson;
	private final String PROPOSAL_DIR = "/upload/life-proposal/";
	private String temporyDir;
	private String lifeProposalId;
	private ProposalInsuredPerson proposalInsuredPerson;
	private LifeEndorseInfo lifeEndorseInfo;
	private List<SurveyTeam> surveyTeamList;
	private List<SurveyMember> surveyMemberList;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeSurvey = (lifeSurvey == null) ? (LifeSurvey) getParam("lifeSurvey") : lifeSurvey;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeSurvey");
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		/* Create temp directory for upload */
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";

		LifeProposal lifeProposal = lifeSurvey.getLifeProposal();
		this.lifeProposalId = lifeProposal.getId();
		loadRender();
		createNewAttMap();
		saveIntoTempFolder(lifeProposal);
	}

	private void saveIntoTempFolder(LifeProposal lifeProposal) {
		String srcPath = getUploadPath() + PROPOSAL_DIR + lifeProposalId;
		String destPath = getUploadPath() + temporyDir + lifeProposalId;
		try {
			FileHandler.copyDirectory(srcPath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String filePath = null;
		// rename filePath
		for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
			filePath = attach.getFilePath();
			filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
			attach.setFilePath(filePath);
		}
		for (ProposalInsuredPerson newProposalInsuredPerson : lifeProposal.getProposalInsuredPersonList()) {
			for (InsuredPersonAttachment attach : newProposalInsuredPerson.getAttachmentList()) {
				filePath = attach.getFilePath();
				filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
				attach.setFilePath(filePath);
			}
			personMap.put(newProposalInsuredPerson.getId(), newProposalInsuredPerson);
			Map<String, String> perAttMap = new HashMap<String, String>();
			for (InsuredPersonAttachment ipAtt : newProposalInsuredPerson.getAttachmentList()) {
				perAttMap.put(ipAtt.getName(), ipAtt.getFilePath());
			}
			personAttchmentMap.put(newProposalInsuredPerson.getId(), perAttMap);
		}

		for (LifeProposalAttachment att : lifeProposal.getAttachmentList()) {
			proposalUploadedFileMap.put(att.getName(), att.getFilePath());
		}
	}

	private void loadRender() {
		Product product = lifeSurvey.getLifeProposal().getProposalInsuredPersonList().get(0).getProduct();
		isAllowApprove = false;
		isPersonalAccident = KeyFactorChecker.isPersonalAccident(product) ? true : false;
		isFarmer = KeyFactorChecker.isFarmer(product) ? true : false;
		isShortTermEndowment = KeyFactorChecker.isShortTermEndowment(product.getId()) ? true : false;
		isGovernmentShortTermEndowment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId()) ? true : false;
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
		isSeaman = KeyFactorChecker.isSeaMenLife(product.getId());
	}

	private void createNewAttMap() {
		proposalUploadedFileMap = new HashMap<String, String>();
		personAttchmentMap = new HashMap<String, Map<String, String>>();
		personMap = new HashMap<String, ProposalInsuredPerson>();
	}

	// prepare
	public void copyAttachment(LifeProposal lifeProposal) {
		LifeProposal oldProposal = lifeProposal.getLifePolicy().getLifeProposal();
		if (lifeProposal.getAttachmentList().size() > 0) {
			String filePath = lifeProposal.getAttachmentList().get(0).getFilePath();
			String newPath = filePath.replace(filePath.substring(filePath.lastIndexOf("/")), "");
			try {
				FileHandler.copyDirectory(getUploadPath() + newPath, getUploadPath() + temporyDir + lifeProposal.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (ProposalInsuredPerson oldProposalInsuredPerson : oldProposal.getProposalInsuredPersonList()) {
				for (ProposalInsuredPerson newProposalInsuredPerson : lifeProposal.getProposalInsuredPersonList()) {
					if (oldProposalInsuredPerson.getInsPersonCodeNo().equals(newProposalInsuredPerson.getInsPersonCodeNo())) {
						String srcPath = getUploadPath() + temporyDir + lifeProposal.getId() + "/" + oldProposalInsuredPerson.getId();
						String destPath = getUploadPath() + temporyDir + lifeProposal.getId() + "/" + newProposalInsuredPerson.getId();
						FileHandler.renameFile(srcPath, destPath);
					}
				}
			}
			String fPath = null;
			for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
				fPath = attach.getFilePath();
				fPath = fPath.replaceAll("/upload/life-proposal/", temporyDir);
				fPath = fPath.replaceAll(oldProposal.getId(), lifeProposal.getId());
				attach.setFilePath(fPath);
			}
			for (ProposalInsuredPerson oldProposalInsuredPerson : oldProposal.getProposalInsuredPersonList()) {
				for (ProposalInsuredPerson newProposalInsuredPerson : lifeProposal.getProposalInsuredPersonList()) {
					if (oldProposalInsuredPerson.getInsPersonCodeNo().equals(newProposalInsuredPerson.getInsPersonCodeNo())) {
						for (InsuredPersonAttachment attach : newProposalInsuredPerson.getAttachmentList()) {
							fPath = attach.getFilePath();
							fPath = fPath.replaceAll("/upload/life-proposal/", temporyDir);
							fPath = fPath.replaceAll(oldProposal.getId(), lifeProposal.getId());
							fPath = fPath.replaceAll(oldProposalInsuredPerson.getId(), newProposalInsuredPerson.getId());
							attach.setFilePath(fPath);
						}
					}
				}
			}
		}
	}

	// end prepare
	public String getTemporyDir() {
		return temporyDir;
	}

	public boolean isShowEntry() {
		return showEntry;
	}

	public LifeSurvey getSurvey() {
		return lifeSurvey;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SurveyTeam> getSurveyTeamList() {
		return surveyTeamList;
	}

	public void setSurveyTeamList(List<SurveyTeam> surveyTeamList) {
		this.surveyTeamList = surveyTeamList;
	}

	public List<SurveyMember> getSurveyMemberList() {
		return surveyMemberList;
	}

	public void setSurveyMemberList(List<SurveyMember> surveyMemberList) {
		this.surveyMemberList = surveyMemberList;
	}

	public List<String> getProposalUploadedFileList() {
		return new ArrayList<String>(proposalUploadedFileMap.values());
	}

	public List<String> getPersonUploadedFileList() {
		if (proposalInsuredPerson != null) {
			Map<String, String> insuredPersonFileMap = personAttchmentMap.get(proposalInsuredPerson.getId());
			return new ArrayList<String>(insuredPersonFileMap.values());
		}
		return new ArrayList<String>();
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public void handleProposalAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!proposalUploadedFileMap.containsKey(fileName)) {
			String filePath = temporyDir + lifeProposalId + "/" + fileName;
			proposalUploadedFileMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void handleInsurePersonAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		Map<String, String> insuredPersonFileMap = personAttchmentMap.get(proposalInsuredPerson.getId());
		if (!insuredPersonFileMap.containsKey(fileName)) {
			String filePath = temporyDir + lifeProposalId + "/" + proposalInsuredPerson.getId() + "/" + fileName;
			insuredPersonFileMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	@SuppressWarnings("unchecked")
	public void returnSurveyTeamList(SelectEvent selectEvent) {
		surveyMemberList = new ArrayList<>();
		int count = 1;
		this.surveyTeamList = (List<SurveyTeam>) selectEvent.getObject();
		for (SurveyTeam surveyTeam : surveyTeamList) {
			for (SurveyMember surveyMember : surveyTeam.getSurveyMemberList()) {
				surveyMember.setInclude(true);
				surveyMember.setCount(count++);
				surveyMemberList.add(surveyMember);
			}
		}
	}

	private void loadAllSurveyMember() {
		List<SurveyMember> surMemberList = new ArrayList<>();
		if (surveyMemberList != null)
			for (SurveyMember surveyMember : surveyMemberList) {
				if (surveyMember.isInclude()) {
					surMemberList.add(surveyMember);
				}
			}
		lifeSurvey.setSurveyMemberList(surMemberList);
	}

	// end perpare
	public void preparePersonAttachment(ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
		showEntry = true;
		if (!personAttchmentMap.containsKey(proposalInsuredPerson.getId())) {
			personAttchmentMap.put(proposalInsuredPerson.getId(), new HashMap<String, String>());
		}
	}

	public void updateClassificationOfHealth() {
		showEntry = false;
	}

	public void preparePersonAttachmentDetails(ProposalInsuredPerson proposalPerson) {
		this.proposalInsuredPerson = proposalPerson;
		showEntry = false;
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + lifeProposalId, PROPOSAL_DIR + lifeProposalId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeSurvey.getLifeProposal().getId());
	}

	private void loadAttachment() {
		if (lifeSurvey.getLifeProposal().getAttachmentList().size() > 0) {
			lifeSurvey.getLifeProposal().setAttachmentList(new ArrayList<LifeProposalAttachment>());

			for (ProposalInsuredPerson ipa : lifeSurvey.getLifeProposal().getProposalInsuredPersonList()) {
				ipa.setAttachmentList(new ArrayList<InsuredPersonAttachment>());
			}
		}
		LifeProposal lifeProposal = lifeSurvey.getLifeProposal();
		for (String fileName : proposalUploadedFileMap.keySet()) {
			String filePath = PROPOSAL_DIR + lifeProposalId + "/" + fileName;
			lifeProposal.addAttachment(new LifeProposalAttachment(fileName, filePath));
		}
		if (personAttchmentMap.keySet() != null) {
			for (String insuPersonId : personAttchmentMap.keySet()) {
				Map<String, String> personUploadedMap = personAttchmentMap.get(insuPersonId);
				if (personUploadedMap != null) {
					for (String fileName : personUploadedMap.keySet()) {
						String filePath = PROPOSAL_DIR + lifeProposalId + "/" + insuPersonId + "/" + fileName;
						personMap.get(insuPersonId).addAttachment(new InsuredPersonAttachment(fileName, filePath));
					}
				}
			}
		}
	}

	public String addNewSurvey() {
		String result = null;
		try {
			loadAllSurveyMember();
			loadAttachment();
			WorkflowTask workflowTask = WorkflowTask.APPROVAL;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isShortTermEndowment ? ReferenceType.SHORT_ENDOWMENT_LIFE
									:isGovernmentShortTermEndowment ? ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE
													: isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE 
															: isPublicTermLife ? ReferenceType.PUBLIC_TERM_LIFE 
																	:isSeaman ? ReferenceType.SEAMEN_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = null;
			if (isShortTermEndowment && isGovernmentShortTermEndowment) {
				if (isAllowApprove) {
					workFlowDTO = new WorkFlowDTO(lifeSurvey.getLifeProposal().getId(), lifeSurvey.getLifeProposal().getBranch().getId(), remark, workflowTask, referenceType,
							TransactionType.UNDERWRITING, user, responsiblePerson);
					lifeProposalService.addNewSurvey(lifeSurvey, workFlowDTO);

					ExternalContext extContext = getFacesContext().getExternalContext();
					extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.SURVEY_PROCESS_SUCCESS);
					extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeSurvey.getLifeProposal().getProposalNo());
					result = "dashboard";
					moveUploadedFiles();
				} else {
					String formID = "surveyEntryForm";
					addErrorMessage(formID + ":personTable", MessageId.ANSWER_SURVERY_QUESTION);
				}
			} else {
				workFlowDTO = new WorkFlowDTO(lifeSurvey.getLifeProposal().getId(), lifeSurvey.getLifeProposal().getBranch().getId(), remark, workflowTask, referenceType,
						TransactionType.UNDERWRITING, user, responsiblePerson);
				lifeProposalService.addNewSurvey(lifeSurvey, workFlowDTO);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.SURVEY_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeSurvey.getLifeProposal().getProposalNo());
				result = "dashboard";
				moveUploadedFiles();
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public boolean validLifeSurvey(LifeSurvey lifeSurvey) {
		boolean valid = true;
		String formID = "surveyEntryForm";
		if (lifeSurvey.getSurveyMemberList() == null || lifeSurvey.getSurveyMemberList().size() <= 0) {
			addErrorMessage(formID + ":surveyTeamMember", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}

		return valid;
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeSurvey.getLifeProposal());
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public ClassificationOfHealth[] getClassificationHealthList() {
		return ClassificationOfHealth.values();
	}

	public boolean isEmptyAtt(ProposalInsuredPerson proposalInsuredPerson) {
		String vehId = proposalInsuredPerson.getId();
		Map<String, String> personFileMap = personAttchmentMap.get(vehId);
		if (personFileMap == null || personFileMap.isEmpty()) {
			return true;
		}
		return false;
	}

	public void removeInsuPersonUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			Map<String, String> vehFileMap = personAttchmentMap.get(proposalInsuredPerson.getId());
			vehFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String path = temporyDir + lifeProposalId + "/" + proposalInsuredPerson.getId();
			if (vehFileMap.isEmpty() && proposalUploadedFileMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeProposalUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			proposalUploadedFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (proposalUploadedFileMap.isEmpty() && personAttchmentMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectUser() {
		WorkflowTask workflowTask = WorkflowTask.APPROVAL;
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isFarmer ? WorkFlowType.FARMER 
						: isShortTermEndowment ? WorkFlowType.SHORT_ENDOWMENT 
							:isGovernmentShortTermEndowment ? WorkFlowType.GOVERNMENT_SHORT_ENDOWMENT 
									:isPublicTermLife ? WorkFlowType.PUBLIC_TERM_LIFE 
											:isSeaman ? WorkFlowType.SEAMEN : WorkFlowType.LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		lifeSurvey.setTownship(township);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public String getPageHeader() {
		return (isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : isShortTermEndowment ? "Short Term Endowment Life" : isGovernmentShortTermEndowment ? "Government Short Term Endowment Life":"Life") + " Proposal Survey";
	}

	public void addSurveyQuestion() {
		isAllowApprove = true;
		PrimeFaces.current().executeScript("PF('questionDialog').hide();");
	}

	/* SurveyQuestionAnswer in ShortTermEndowLife */
	public void setProposalInsuredPerson(ProposalInsuredPerson insuredPerson) {
		List<ProductProcessQuestionLink> ppQueLinkList = null;
		for (ProposalInsuredPerson pip : lifeSurvey.getLifeProposal().getProposalInsuredPersonList()) {
			if (pip.getSurveyQuestionAnswerList().isEmpty()) {
				if (isPublicTermLife) {
					ProductProcessCriteriaDTO dto = new ProductProcessCriteriaDTO();
					dto.setAge(pip.getAgeForNextYear());
					dto.setSumInsured(pip.getProposedSumInsured());
					ppQueLinkList = surveyQuestionService.findPProQueByPPId(KeyFactorChecker.getPublicTermLifeId(), ProductProcessIDConfig.getProposalProcessId(), dto);
				} else {
					ppQueLinkList = surveyQuestionService.findProductProcessQuestionLinkList(pip.getProduct().getId(), ProductProcessIDConfig.getProposalProcessId(), null);
				}
				pip.getSurveyQuestionAnswerList().clear();
				SurveyType type = SurveyType.SHORT_ENDOWMENT_LIFE_SURVEY;
				if (isPublicTermLife) {
					if (pip.getApprovedSumInsured() >= 5000000 && pip.getAgeForNextYear() >= 18 && pip.getAgeForNextYear() <= 60) {
						type = SurveyType.PUBLIC_TERM_LIFE_OVER_5_MILLION;
					} else if (pip.getApprovedSumInsured() >= 3000000 && pip.getAgeForNextYear() >= 61 && pip.getAgeForNextYear() <= 75) {
						type = SurveyType.PUBLIC_TERM_LIFE_OVER_3_MILLION;
					}
				}
				for (ProductProcessQuestionLink ppQuesLink : ppQueLinkList) {
					SurveyQuestionAnswer surveyAnswer = new SurveyQuestionAnswer(ppQuesLink);
					surveyAnswer.setSurveyType(type);
					pip.getSurveyQuestionAnswerList().add(surveyAnswer);
				}
			}
		}
		this.proposalInsuredPerson = insuredPerson;
	}

	public ProposalInsuredPerson getProposalInsuredPerson() {
		return proposalInsuredPerson;
	}

	public void changeResourceQuestion(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = proposalInsuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswer resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}
		int index = resourceQuestionAnswerList.indexOf(surveyQuestionAnswer.getSelectResQuesAns());
		ResourceQuestionAnswer selectedResourceQAns = resourceQuestionAnswerList.get(index);
		selectedResourceQAns.setValue(1);
		surveyQuestionAnswer.setSelectResQuesAns(selectedResourceQAns);
	}

	public void changeResourceQuestionList(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = proposalInsuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswer resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}

		for (ResourceQuestionAnswer resourceQuestionAnswer : surveyQuestionAnswer.getSelectResQuesAnsList()) {
			int index = resourceQuestionAnswerList.indexOf(resourceQuestionAnswer);
			ResourceQuestionAnswer selectedResourceQAns = resourceQuestionAnswerList.get(index);
			selectedResourceQAns.setValue(1);
			surveyQuestionAnswer.setSelectResQuesAns(selectedResourceQAns);
		}
	}

	public void changeBooleanValue(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = proposalInsuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		ResourceQuestionAnswer resourceQuestionAnswer = resourceQuestionAnswerList.get(0);
		resourceQuestionAnswer.setName(surveyQuestionAnswer.getTureLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswer.setValue(1);
		} else {
			resourceQuestionAnswer.setValue(0);
		}
		resourceQuestionAnswer = resourceQuestionAnswerList.get(1);
		resourceQuestionAnswer.setName(surveyQuestionAnswer.getFalseLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswer.setValue(0);
		} else {
			resourceQuestionAnswer.setValue(1);
		}
	}

	public void changeDate(SelectEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = proposalInsuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(surveyQuestionAnswer.getAnswerDate()));
	}

	public void resetDate(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
		surveyQuestionAnswer.setAnswerDate(null);
		surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null);
	}

	public void returnHospital(SelectEvent event) {
		Hospital hospital = (Hospital) event.getObject();
		lifeSurvey.setHospital(hospital);
		lifeSurvey.setAddress(hospital.getAddress().getPermanentAddress());
		lifeSurvey.setTownship(hospital.getAddress().getTownship());
	}

	public void removeHospital() {
		lifeSurvey.setHospital(null);
		lifeSurvey.setAddress(null);
		lifeSurvey.setTownship(null);
	}

	public boolean isShortTermEndowment() {
		return isShortTermEndowment;
	}
	
	public boolean isGovernmentShortTermEndowment() {
		return isGovernmentShortTermEndowment;
	}
	
	public boolean isEndownmentLife() {
		return isEndownmentLife;
	}

	public boolean isFarmer() {
		return isFarmer;
	}

	public boolean isPublicTermLife() {
		return isPublicTermLife;
	}

	public boolean isAllowApprove() {
		return isAllowApprove;
	}

}
