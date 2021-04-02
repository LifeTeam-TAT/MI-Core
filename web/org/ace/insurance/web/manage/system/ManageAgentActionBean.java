/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;

import org.ace.insurance.common.FamilyInfo;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PermanentAddress;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentAttachment;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "ManageAgentActionBean")
public class ManageAgentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean createNew;
	private List<String> source;
	private List<String> target;
	private UploadedFile uploadedFile;
	private String temporyDir;
	private final String PROFILE_DIR = "/upload/agent/";
	private List<String> stateCodeList;
	private List<String> townshipCodeList;

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	private Agent agent;
	private List<RelationShip> relationShipList;
	private Map<String, FamilyInfoDTO> familyInfoDTOMap;
	private FamilyInfoDTO familyInfoDTO;
	private boolean createNewFamilyInfo;
	private String filePath;
	private List<Agent> agentList;

	@PostConstruct
	public void init() {
		initialization();
		temporyDir = "/temp/" + System.currentTimeMillis() + "/agent/";
		relationShipList = relationShipService.findAllRelationShip();
		loadRoleDataModel();
		stateCodeList = new ArrayList<>();
		townshipCodeList = new ArrayList<>();
		loadProvinceNo();
		loadAgent();
	}

	private void loadAgent() {
		if (agent != null) {
			createNew = false;
			agent.loadTransientIdNo();
			if (agent.getStateCode() != null)
				changeAgentStateCode();

			prepareAddNewFamilyInfo();
			familyInfoDTOMap = new HashMap<String, FamilyInfoDTO>();
			FamilyInfoDTO familyInfoDTO = null;
			for (FamilyInfo familyInfo : agent.getFamilyInfo()) {
				familyInfoDTO = new FamilyInfoDTO(familyInfo);
				familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
			}

		} else {
			createNewAgent();
		}
	}

	private void initialization() {
		agent = (Agent) getParam("agent");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getTemporyDir() {
		return temporyDir;
	}

	public boolean isCreateNewFamilyInfo() {
		return createNewFamilyInfo;
	}

	private void createNewFamilyInfo() {
		createNewFamilyInfo = true;
		familyInfoDTO = new FamilyInfoDTO();
	}

	public FamilyInfoDTO getFamilyInfoDTO() {
		return familyInfoDTO;
	}

	public void setFamilyInfoDTO(FamilyInfoDTO familyInfoDTO) {
		this.familyInfoDTO = familyInfoDTO;
	}

	public List<FamilyInfoDTO> getFamilyInfoDTOList() {
		if (familyInfoDTOMap.values() != null) {
			return new ArrayList<FamilyInfoDTO>(familyInfoDTOMap.values());
		}
		return new ArrayList<FamilyInfoDTO>();
	}

	public void prepareAddNewFamilyInfo() {
		createNewFamilyInfo();
	}

	public void prepareEditFamilyInfo(FamilyInfoDTO familyInfoDTO) {
		familyInfoDTO.loadTransientIdNo();
		if (familyInfoDTO.getStateCode() != null) {
			changeAgentStateCode();
		}
		this.familyInfoDTO = familyInfoDTO;
		this.createNewFamilyInfo = false;
	}

	public void saveFamilyInfo() {
		if (validateFamilyInfo(familyInfoDTO)) {
			familyInfoDTO.setFullIdNo(setFullIdNo());
			familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
			createNewFamilyInfo();
		}
	}

	private boolean validateFamilyInfo(FamilyInfoDTO familyInfoDTO) {
		boolean valid = true;
		String formID = "agentEntryForm";
		if (isEmpty(familyInfoDTO.getInitialId())) {
			addErrorMessage(formID + ":familyInitialId", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (isEmpty(familyInfoDTO.getName().getFirstName())) {
			addErrorMessage(formID + ":familyFirstName", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		return valid;
	}

	public void saveAndMoreFamilyInfo() {
		familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
		familyInfoDTO = new FamilyInfoDTO();
	}

	public void removeFamilyInfo(FamilyInfoDTO familyInfoDTO) {
		familyInfoDTOMap.remove(familyInfoDTO.getTempId());
	}

	public List<FamilyInfo> getFamilyInfoList() {
		List<FamilyInfo> result = new ArrayList<FamilyInfo>();
		if (familyInfoDTOMap.values() != null) {
			for (FamilyInfoDTO familyInfoDTO : familyInfoDTOMap.values()) {
				result.add(new FamilyInfo(familyInfoDTO.getInitialId(), familyInfoDTO.getFullIdNo(), familyInfoDTO.getIdType(), familyInfoDTO.getDateOfBirth(),
						familyInfoDTO.getName(), familyInfoDTO.getRelationShip(), familyInfoDTO.getIndustry(), familyInfoDTO.getOccupation()));
			}
		}
		return result;
	}

	public Gender[] getGenderSelectItemList() {
		return Gender.values();
	}

	public MaritalStatus[] getMaritalStatusSelectItemList() {
		return MaritalStatus.values();
	}

	public IdType[] getIdTypeSelectItemList() {
		return IdType.values();
	}

	public ProductGroupType[] getProductGroupTypeSelectItemList() {
		return ProductGroupType.values();
	}

	public void createNewAgent() {
		createNew = true;
		agent = new Agent();
		agent.setName(new Name());
		agent.setPermanentAddress(new PermanentAddress());
		agent.setGender(Gender.FEMALE);
		agent.setIdType(IdType.NRCNO);
		agent.setMaritalStatus(MaritalStatus.SINGLE);
		agent.setGroupType(ProductGroupType.LIFE);
		setUploadedFile(null);
		familyInfoDTOMap = new HashMap<String, FamilyInfoDTO>();
		prepareAddNewFamilyInfo();
	}

	public void prepareUpdateAgent(Agent agent) {
		createNew = false;
		if (agent != null) {
			if (agent.getAttachment() != null) {
				filePath = agent.getAttachment().getFilePath();
			}
		}
		uploadedFile = null;
		agent.loadTransientIdNo();
		this.agent = agent;
		if (this.agent.getStateCode() != null) {
			changeAgentStateCode();
		}
		familyInfoDTOMap = new HashMap<String, FamilyInfoDTO>();
		List<FamilyInfo> familyInfoList = agent.getFamilyInfo();
		for (FamilyInfo familyInfo : familyInfoList) {
			FamilyInfoDTO familyInfoDTO = new FamilyInfoDTO(familyInfo);
			familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
		}

	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName();
		String filePath = temporyDir + fileName;
		createFile(new File(getSystemPath() + filePath), uploadedFile.getContents());
		agent.setAttachment(new AgentAttachment(fileName, filePath));
	}

	public void removePhotoImage() {
		try {
			FileUtils.forceDelete(new File(getSystemPath() + agent.getAttachment().getFilePath()));
			agent.getAttachment().setFilePath(null);
			agent.getAttachment().setName(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isShowImage() {
		if (agent.getAttachment() != null && agent.getAttachment().getFilePath() != null && !agent.getAttachment().getFilePath().isEmpty())
			return true;
		else
			return false;
	}

	private void moveUploadedFiles() {
		try {
			if (uploadedFile != null) {
				AgentAttachment attachment = agent.getAttachment();
				FileHandler.moveFiles(getUploadPath(), temporyDir + attachment.getName(), PROFILE_DIR + agent.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addNewAgent() {
		try {
			if (checkExistingAgent()) {
				agent.setFullIdNo(setAgentFullIdNo());
				agent.setFamilyInfo(getFamilyInfoList());
				agentService.addNewAgent(agent);
				if (agent.getAttachment() != null) {
					AgentAttachment attachment = agent.getAttachment();
					String targetFilePath = PROFILE_DIR + agent.getId() + "/" + attachment.getName();
					attachment.setFilePath(targetFilePath);
				}
				moveUploadedFiles();
				loadRoleDataModel();
				addInfoMessage(null, MessageId.INSERT_SUCCESS, agent.getFullName());
				createNewAgent();
			} else {
				addErrorMessage(null, MessageId.EXISTING_AGENT, agent.getFullName());
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private boolean checkExistingAgent() {
		boolean result = agentService.checkExistingAgent(agent.getLiscenseNo());
		return result;
	}

	private String setAgentFullIdNo() {
		String result = "";
		if (agent.getIdType().equals(IdType.NRCNO)) {
			result = agent.getStateCode() + "/" + agent.getTownshipCode() + "(" + agent.getIdConditionType() + ")" + agent.getIdNo();
		} else if (agent.getIdType().equals(IdType.FRCNO) || agent.getIdType().equals(IdType.PASSPORTNO)) {
			result = agent.getIdNo();
		}
		return result;
	}

	public void updateAgent() {
		try {
			if (agent.getAttachment() != null) {
				AgentAttachment attachment = agent.getAttachment();
				String targetFilePath = PROFILE_DIR + agent.getId() + "/" + attachment.getName();
				attachment.setFilePath(targetFilePath);
			}
			agent.setFamilyInfo(getFamilyInfoList());
			agentService.updateAgent(agent);
			moveUploadedFiles();
			loadRoleDataModel();
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, agent.getFullAddress());
			createNewAgent();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteAgent() {
		try {
			agentService.deleteAgent(agent);
			loadRoleDataModel();
			gotoFirstTag = true;
			addInfoMessage(null, MessageId.DELETE_SUCCESS, agent.getFullAddress());
			createNewAgent();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	private void loadRoleDataModel() {
		agentList = agentService.findAllAgent();
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	public List<String> getTarget() {
		return target;
	}

	public void setTarget(List<String> target) {
		this.target = target;
	}

	public List<Agent> getAgentList() {
		return agentList;
	}

	public boolean gotoFirstTag;

	private boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		String formID = "agentEntryForm";
		if ("personalInfo".equals(event.getOldStep()) && "generalInfo".equals(event.getNewStep())) {
			if (isEmpty(agent.getInitialId())) {
				addErrorMessage(formID + ":initialId", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (isEmpty(agent.getName().getFirstName())) {
				addErrorMessage(formID + ":firstName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (isEmpty(agent.getGender().getLabel())) {
				addErrorMessage(formID + ":gender", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			// if (isEmpty(agent.getIdNo())) {
			// addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
			// valid = false;
			// }
			if (isEmpty(agent.getIdType().getLabel())) {
				addErrorMessage(formID + ":idType", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (isEmpty(agent.getFatherName())) {
				addErrorMessage(formID + ":fatherName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}

			if (agent.getDateOfBirth() == null) {
				addErrorMessage(formID + ":dob", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		if ("generalInfo".equals(event.getOldStep()) && "contactInfo".equals(event.getNewStep())) {
			if (agent.getCountry() == null) {
				addErrorMessage(formID + ":nationality", MessageId.REQUIRED_NATIONALITY);
				valid = false;
			}
			if (agent.getBranch() == null) {
				addErrorMessage(formID + ":branch", MessageId.REQUIRED_BRANCH);
				valid = false;
			}
			if (isEmpty(agent.getLiscenseNo())) {
				addErrorMessage(formID + ":liscenseNo", MessageId.REQUIRED_BRANCH);
				valid = false;
			}
		}
		if ("contactInfo".equals(event.getOldStep()) && "familyInfo".equals(event.getNewStep())) {
			if (isEmpty(agent.getResidentAddress().getResidentAddress())) {
				addErrorMessage(formID + ":residentAdd", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (agent.getResidentAddress().getTownship() == null) {
				addErrorMessage(formID + ":residentTownId", MessageId.REQUIRED_TOWNSHIP);
				valid = false;
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public void returnNationality(SelectEvent event) {
		Country nationality = (Country) event.getObject();
		agent.setCountry(nationality);
	}

	public void returnReligion(SelectEvent event) {
		Religion religion = (Religion) event.getObject();
		agent.setReligion(religion);
	}

	public void returnQualification(SelectEvent event) {
		Qualification qualification = (Qualification) event.getObject();
		agent.setQualification(qualification);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		agent.setOrganization(organization);
	}

	public void returnBankBranch(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		agent.setBankBranch(bankBranch);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		agent.setBranch(branch);
	}

	public void returnResidentTownship(SelectEvent event) {
		Township residentTownship = (Township) event.getObject();
		agent.getResidentAddress().setTownship(residentTownship);
	}

	public void returnPermanentTownship(SelectEvent event) {
		Township permanentTownship = (Township) event.getObject();
		agent.getPermanentAddress().setTownship(permanentTownship);
	}

	public void returnFamilyOccupation(SelectEvent event) {
		Occupation familyOccupation = (Occupation) event.getObject();
		familyInfoDTO.setOccupation(familyOccupation);
	}

	public void returnFamilyIndustry(SelectEvent event) {
		Industry familyIndustry = (Industry) event.getObject();
		familyInfoDTO.setIndustry(familyIndustry);
	}

	public void loadProvinceNo() {
		stateCodeList = provinceService.findAllProvinceNo();
	}

	public void changeAgentStateCode() {
		townshipCodeList = new ArrayList<>();
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(agent.getStateCode());
	}

	public void changeFamilyStateCode() {
		townshipCodeList = new ArrayList<>();
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(familyInfoDTO.getStateCode());
	}

	public List<String> getStateCodeList() {
		return stateCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
	}

	public String setFullIdNo() {
		String result = "";
		if (familyInfoDTO.getIdType().equals(IdType.NRCNO)) {
			result = familyInfoDTO.getStateCode() + "/" + familyInfoDTO.getTownshipCode() + "(" + familyInfoDTO.getIdConditionType() + ")" + familyInfoDTO.getIdNo();
		} else if (familyInfoDTO.getIdType().equals(IdType.FRCNO) || familyInfoDTO.getIdType().equals(IdType.PASSPORTNO)) {
			result = familyInfoDTO.getIdNo();
		}
		return result;
	}

}
