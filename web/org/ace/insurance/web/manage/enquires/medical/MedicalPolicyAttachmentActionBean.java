package org.ace.insurance.web.manage.enquires.medical;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyAttachment;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAttachment;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAttachment;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author HK
 *
 */
@ViewScoped
@ManagedBean(name = "MedicalPolicyAttachmentActionBean")
public class MedicalPolicyAttachmentActionBean extends BaseBean {

	/* Attachment */
	private final String POLICY_DIR = "/upload/medical-proposal/";
	private final String temporyDir = "/temp/" + System.currentTimeMillis() + "/";
	private String medicalProposalId;
	private Map<String, String> policyUploadedFileMap;
	private MedicalPolicy medicalPolicy;
	private boolean showEntry;
	private boolean showButton;
	private Map<String, MedicalPolicyInsuredPerson> personMap;
	private Map<String, Map<String, String>> personAttchmentMap;
	private MedicalPolicyInsuredPerson policyInsuredPerson;

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalproposalService) {
		this.medicalProposalService = medicalproposalService;
	}

	@PostConstruct
	public void init() {
		medicalPolicy = (MedicalPolicy) getParam("medicalPolicy");
		medicalProposalId = medicalPolicy.getMedicalProposal().getId();
		createNewInstances();
		loadOldAttachment();

	}

	private void loadOldAttachment() {
		String srcPath = getUploadPath() + POLICY_DIR + medicalProposalId;
		String destPath = getUploadPath() + temporyDir + medicalProposalId;
		String filePath = null;

		try {
			FileUtils.copyDirectory(new File(srcPath), new File(destPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (MedicalPolicyAttachment att : medicalPolicy.getAttachmentList()) {
			filePath = att.getFilePath();
			filePath = filePath.replaceAll("/upload/medical-proposal/", temporyDir);
			att.setFilePath(filePath);
			policyUploadedFileMap.put(att.getName(), att.getFilePath());
		}

		for (MedicalPolicyInsuredPerson per : medicalPolicy.getPolicyInsuredPersonList()) {
			if (per.getAttachmentList().size() > 0) {
				Map<String, String> perAttMap = new HashMap<String, String>();
				for (MedicalPolicyInsuredPersonAttachment ipAtt : per.getAttachmentList()) {
					filePath = ipAtt.getFilePath();
					filePath = filePath.replaceAll("/upload/medical-proposal/", temporyDir);
					ipAtt.setFilePath(filePath);
					perAttMap.put(ipAtt.getName(), ipAtt.getFilePath());
				}
				personAttchmentMap.put(per.getId(), perAttMap);
				personMap.put(per.getId(), per);
			}
		}

	}

	private void createNewInstances() {
		policyUploadedFileMap = new HashMap<String, String>();
		personAttchmentMap = new HashMap<String, Map<String, String>>();
		personMap = new HashMap<String, MedicalPolicyInsuredPerson>();
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalPolicy");
	}

	public String uploadAttachment() {
		String result = null;
		try {
			loadAttachment();
			medicalPolicyService.updatePolicyAttachment(this.medicalPolicy);
			MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(medicalPolicy.getMedicalProposal().getId());
			if (medicalProposal != null) {
				copyMedicalProposalAttachment(medicalProposal);
				medicalProposalService.updateMedicalProposalAttachment(medicalProposal);
			}
			moveUploadedFiles();
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.ATTACHMENT_PROCESS_SUCCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	private void copyMedicalProposalAttachment(MedicalProposal medicalProposal) {
		medicalProposal.getAttachmentList().clear();
		for (MedicalPolicyAttachment policyAttachment : medicalPolicy.getAttachmentList()) {
			MedicalProposalAttachment proposalAttachment = new MedicalProposalAttachment(policyAttachment);
			medicalProposal.addAttachment(proposalAttachment);
		}

		for (MedicalPolicyInsuredPerson policyInsuredPerson : medicalPolicy.getPolicyInsuredPersonList()) {
			for (MedicalProposalInsuredPerson proposalInsuredPerson : medicalProposal.getMedicalProposalInsuredPersonList()) {
				if (policyInsuredPerson.getInsPersonCodeNo().equals(proposalInsuredPerson.getInsPersonCodeNo())) {
					proposalInsuredPerson.getAttachmentList().clear();
					for (MedicalPolicyInsuredPersonAttachment insuredPersonAttach : policyInsuredPerson.getAttachmentList()) {
						MedicalProposalInsuredPersonAttachment proposalInsuPersonAttach = new MedicalProposalInsuredPersonAttachment(insuredPersonAttach);
						proposalInsuredPerson.addAttachment(proposalInsuPersonAttach);
					}
					break;
				}
			}
		}
	}

	private void loadAttachment() {
		medicalPolicy.getAttachmentList().clear();
		for (String fileName : policyUploadedFileMap.keySet()) {
			String filePath = POLICY_DIR + medicalProposalId + "/" + fileName;
			medicalPolicy.addMedicalPolicyAttachment(new MedicalPolicyAttachment(fileName, filePath));
		}
		if (personAttchmentMap.keySet() != null) {
			for (String insuPersonId : personAttchmentMap.keySet()) {
				Map<String, String> personUploadedMap = personAttchmentMap.get(insuPersonId);
				if (personUploadedMap != null) {
					personMap.get(insuPersonId).getAttachmentList().clear();
					for (String fileName : personUploadedMap.keySet()) {
						String filePath = POLICY_DIR + medicalProposalId + "/" + insuPersonId + "/" + fileName;
						personMap.get(insuPersonId).addAttachment(new MedicalPolicyInsuredPersonAttachment(fileName, filePath));
					}
				}
			}
		}
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + medicalProposalId, POLICY_DIR + medicalProposalId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void preparePersonAttachment(MedicalPolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
		showEntry = true;
	}

	public List<String> getPolicyAttachmentList() {
		return new ArrayList<String>(policyUploadedFileMap.values());
	}

	public List<String> getPersonUploadedFileList() {
		if (policyInsuredPerson != null) {
			Map<String, String> personAttFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
			if (personAttFileMap == null)
				return new ArrayList<String>();
			return new ArrayList<String>(personAttFileMap.values());
		}
		return new ArrayList<String>();
	}

	public void removePolicyUploadedFile(String filePath) {
		try {
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String fileName = getFileName(filePath);
			policyUploadedFileMap.remove(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handlePolicyAttachment(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		String fileName = uploadFile.getFileName().replace("\\s", "_");
		String filePath = temporyDir + medicalProposalId + "/" + fileName;
		policyUploadedFileMap.put(fileName, filePath);
		createFile(new File(getUploadPath() + filePath), uploadFile.getContents());
	}

	public void removeInsuPersonUploadedFile(String filePath) {
		try {
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String fileName = getFileName(filePath);
			Map<String, String> personAttFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
			personAttFileMap.remove(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleInsurePersonAttachment(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		String fileName = uploadFile.getFileName().replace("\\s", "_");
		String filePath = temporyDir + medicalProposalId + "/" + policyInsuredPerson.getId() + "/" + fileName;
		Map<String, String> personAttFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
		if (personAttFileMap == null)
			personAttFileMap = new HashMap<String, String>();
		personAttFileMap.put(fileName, filePath);
		personAttchmentMap.put(policyInsuredPerson.getId(), personAttFileMap);
		personMap.put(policyInsuredPerson.getId(), policyInsuredPerson);
		createFile(new File(getUploadPath() + filePath), uploadFile.getContents());
	}

	public boolean isEmptyAtt(MedicalPolicyInsuredPerson policyInsuredPerson) {
		String insuId = policyInsuredPerson.getId();
		Map<String, String> personFileMap = personAttchmentMap.get(insuId);
		if (personFileMap == null || personFileMap.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isShowEntry() {
		return showEntry;
	}

	public MedicalPolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public MedicalPolicy getMedicalPolicy() {
		return medicalPolicy;
	}

	public boolean isShowButton() {
		return showButton;
	}

}
