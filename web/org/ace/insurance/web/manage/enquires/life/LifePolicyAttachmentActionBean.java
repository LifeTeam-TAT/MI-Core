package org.ace.insurance.web.manage.enquires.life;

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

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.LifePolicyAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonAttachment;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "LifePolicyAttachmentActionBean")
public class LifePolicyAttachmentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/* Attachment */
	private final String POLICY_DIR = "/upload/life-proposal/";
	private String temporyDir;
	private String lifeProposalId;
	private Map<String, String> policyUploadedFileMap;
	private LifePolicy lifePolicy;
	private boolean showEntry;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	private Map<String, PolicyInsuredPerson> personMap;
	private Map<String, Map<String, String>> personAttchmentMap;
	private PolicyInsuredPerson policyInsuredPerson;

	@PostConstruct
	public void init() {
		lifePolicy = (LifePolicy) getParam("lifePolicy");
		policyUploadedFileMap = new HashMap<String, String>();
		personAttchmentMap = new HashMap<String, Map<String, String>>();
		personMap = new HashMap<String, PolicyInsuredPerson>();
		for (PolicyInsuredPerson per : lifePolicy.getPolicyInsuredPersonList()) {
			personMap.put(per.getId(), per);
		}

		for (LifePolicyAttachment att : lifePolicy.getAttachmentList()) {
			policyUploadedFileMap.put(att.getName(), att.getFilePath());
		}

		for (PolicyInsuredPerson pip : lifePolicy.getPolicyInsuredPersonList()) {
			personMap.put(pip.getId(), pip);
			Map<String, String> perAttMap = new HashMap<String, String>();
			for (PolicyInsuredPersonAttachment ipAtt : pip.getAttachmentList()) {
				perAttMap.put(ipAtt.getName(), ipAtt.getFilePath());
			}
			personAttchmentMap.put(pip.getId(), perAttMap);
		}
		lifeProposalId = lifePolicy.getLifeProposal().getId();
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
	}

	public void handlePolicyAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		String filePath = temporyDir + lifeProposalId + "/" + fileName;
		policyUploadedFileMap.put(fileName, filePath);
		createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
	}

	public List<String> getPolicyAttachmentList() {
		return new ArrayList<String>(policyUploadedFileMap.values());
	}

	public boolean isShowEntry() {
		return showEntry;
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

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public String uploadAttachment() {
		String result = null;
		try {
			loadAttachment();
			lifePolicyService.updatePolicyAttachment(this.lifePolicy);
			result = "goToLifeEnquiry";
			moveUploadedFiles();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	private void loadAttachment() {
		for (String fileName : policyUploadedFileMap.keySet()) {
			String filePath = POLICY_DIR + lifeProposalId + "/" + fileName;
			lifePolicy.addLifePolicyAttachment(new LifePolicyAttachment(fileName, filePath));
		}
		if (personAttchmentMap.keySet() != null) {
			for (String insuPersonId : personAttchmentMap.keySet()) {
				Map<String, String> personUploadedMap = personAttchmentMap.get(insuPersonId);
				if (personUploadedMap != null) {
					for (String fileName : personUploadedMap.keySet()) {
						String filePath = POLICY_DIR + lifeProposalId + "/" + insuPersonId + "/" + fileName;
						personMap.get(insuPersonId).addAttachment(new PolicyInsuredPersonAttachment(fileName, filePath));
					}
				}
			}
		}
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + lifeProposalId, POLICY_DIR + lifeProposalId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleInsurePersonAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		Map<String, String> insuredPersonFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
		String filePath = temporyDir + lifeProposalId + "/" + policyInsuredPerson.getId() + "/" + fileName;
		insuredPersonFileMap.put(fileName, filePath);
		createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
	}

	public List<String> getPersonUploadedFileList() {
		if (policyInsuredPerson != null) {
			Map<String, String> insuredPersonFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
			return new ArrayList<String>(insuredPersonFileMap.values());
		}
		return new ArrayList<String>();
	}

	public void removeInsuPersonUploadedFile(String filePath) {
		try {
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String fileName = getFileName(filePath);
			Map<String, String> vehFileMap = personAttchmentMap.get(policyInsuredPerson.getId());
			vehFileMap.remove(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isEmptyAtt(PolicyInsuredPerson policyInsuredPerson) {
		String vehId = policyInsuredPerson.getId();
		Map<String, String> personFileMap = personAttchmentMap.get(vehId);
		if (personFileMap == null || personFileMap.isEmpty()) {
			return true;
		}
		return false;
	}

	public void preparePersonAttachment(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
		showEntry = true;
		if (!personAttchmentMap.containsKey(policyInsuredPerson.getId())) {
			personAttchmentMap.put(policyInsuredPerson.getId(), new HashMap<String, String>());
		}
	}

}
