package org.ace.insurance.web.manage.life.claim.proposal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.DisabilityLifeClaimPartLink;
import org.ace.insurance.life.claim.LCBP001;
import org.ace.insurance.life.claim.LifeClaimBeneficiaryPerson;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.claim.service.interfaces.ILifePolicyClaimService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.claim.MedicalBeneficiaryRole;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.productDisabilityPartLink.service.interfaces.IProductDisabilityPartLinkService;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "AddNewLifeClaimProposalActionBean")
public class AddNewLifeClaimProposalActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{LifePolicyClaimService}")
	private ILifePolicyClaimService lifePolicyClaimService;

	public void setLifePolicyClaimService(ILifePolicyClaimService lifePolicyClaimService) {
		this.lifePolicyClaimService = lifePolicyClaimService;
	}

	@ManagedProperty(value = "#{ProductDisabilityPartLinkService}")
	private IProductDisabilityPartLinkService productDisabilityPartService;

	public void setProductDisabilityPartService(IProductDisabilityPartLinkService productDisabilityPartService) {
		this.productDisabilityPartService = productDisabilityPartService;
	}

	private User user;
	private LifeClaimNotification lifeClaimNotification;
	private LifeClaimProposal claimProposal;
	private LifePolicyClaim lifePolicyClaim;
	private LifeDeathClaim deathClaim;
	private LifeHospitalizedClaim hospitalizedClaim;
	private DisabilityLifeClaim disabilityLifeClaim;
	private List<DisabilityLifeClaimPartLink> disabilityLifeClaimPartList;
	private DisabilityLifeClaimPartLink disabilityLifeClaimPart;
	private boolean isDeathClaim;
	private boolean isHospitalClaim;
	private boolean isDisibilityClaim;
	private String[] selectedClaimTypes;
	private User responsiblePerson;
	private String remark;
	private List<LCBP001> beneficiaryList;
	private LifeClaimBeneficiaryPerson claimSuccessor;
	private boolean isSuccessor;
	private LCBP001 selectedBeneficiary;
	private List<RelationShip> relationShipList;
	private List<ProductDisabilityRate> productDisabilityRateList;
	private double previousPercentage;
	private double maximumClaimPercentage;
	private final String reportName = "LifePolicyInform";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private String fileName = "LifeClaimApproval.pdf";
	private boolean isDisPrint;
	private Map<String, String> claimAttchmentMap;
	private String temporyDir;
	private final String PROPOSAL_DIR = "/upload/claim-proposal/";
	private double remainPremium;
	private double netClaimAmount;
	private boolean isShortTerm;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaimNotification = (lifeClaimNotification == null) ? (LifeClaimNotification) getParam("lifeClaimNotification") : lifeClaimNotification;
	}

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	// @PreDestroy
	// public void destroy() {
	// removeParam("lifeClaimNotification");
	// }

	@PostConstruct
	public void init() {
		initializeInjection();
		temporyDir = "/temp/" + System.currentTimeMillis() + "/claim-proposal/";
		createLifePolicyClaimProposal();
		bindLifeNotificationdate();
		relationShipList = relationShipService.findAllRelationShip();

	}

	public double calculateRemainPremium() {
		int paidMonths = Utils.monthsBetween(claimProposal.getLifePolicy().getActivedPolicyStartDate(), claimProposal.getLifePolicy().getCoverageDate(), false, false);
		double remainingRemium = 0.0;
		int remainMonths = 0;
		if (paidMonths < 12) {
			remainMonths = 12 - paidMonths;
			if (claimProposal.getLifePolicy().getPaymentType().getMonth() == 1) {
				double calculatePremium = (claimProposal.getClaimPerson().getBasicTermPremium() * remainMonths);
				remainingRemium = calculatePremium;
			} else {
				double oneYearPremium = claimProposal.getClaimPerson().getPremium();
				double monthlyPremium = oneYearPremium / 12;
				double calculatePremium = monthlyPremium * remainMonths;
				remainingRemium = calculatePremium;
			}
		}
		return remainingRemium;
	}

	private void createLifePolicyClaimProposal() {
		isDisPrint = true;
		claimProposal = new LifeClaimProposal();
		lifePolicyClaim = new LifePolicyClaim();
		deathClaim = new LifeDeathClaim();
		hospitalizedClaim = new LifeHospitalizedClaim();
		disabilityLifeClaim = new DisabilityLifeClaim();
		disabilityLifeClaimPartList = new ArrayList<DisabilityLifeClaimPartLink>();
		claimAttchmentMap = new HashMap<String, String>();
	}

	private void bindLifeNotificationdate() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lifeClaimNotification.getLifePolicyNo());
		claimProposal.setNotificationNo(lifeClaimNotification.getNotificationNo());
		claimProposal.setLifePolicy(lifePolicy);
		claimProposal.setClaimPerson(lifeClaimNotification.getClaimPerson());
		claimProposal.setProduct(lifeClaimNotification.getProduct());
		claimProposal.setBranch(lifeClaimNotification.getBranch());
		claimProposal.setSubmittedDate(new Date());
		claimProposal.setOccuranceDate(lifeClaimNotification.getOccuranceDate());
		claimProposal.setReportDate(lifeClaimNotification.getReportedDate());
		claimProposal.setSalePoint(lifeClaimNotification.getSalePoint());
		PolicyInsuredPerson insuredPerson = lifePolicy.getInsuredPersonInfo().get(0);
		isShortTerm = KeyFactorChecker.isShortTermEndowment(insuredPerson.getProduct().getId());
		if (isShortTerm) {
			claimProposal.setRemainPremium(calculateRemainPremium());
		}
	}

	public void calculateAdmissionFee(AjaxBehaviorEvent event) {
		if (hospitalizedClaim.getStartDate() != null && hospitalizedClaim.getEndDate() != null) {
			int totalHospitalizedDays = Utils.daysBetween(hospitalizedClaim.getStartDate(), hospitalizedClaim.getEndDate(), false, true);
			hospitalizedClaim.setNoOfdays(totalHospitalizedDays);
			double admissionFee = claimProposal.getClaimPerson().getSumInsured() * 0.02 * totalHospitalizedDays;
			hospitalizedClaim.setHospitalizedAmount(admissionFee);
		}
	}

	public void changeDisabilityAmount(DisabilityLifeClaimPartLink claimPartLink) {
		double percentage = claimPartLink.getDisabilityAmount() / claimProposal.getClaimPerson().getSumInsured() * 100.00;
		claimPartLink.setPercentage(percentage);
		netClaimAmount = claimPartLink.getDisabilityAmount() - claimProposal.getRemainPremium();
		DisabilityLifeClaimPartLink removeClaim = null;
		for (DisabilityLifeClaimPartLink link : disabilityLifeClaimPartList) {
			if (link.getDisabilityPart().getId().equals(claimPartLink.getDisabilityPart().getId())) {
				removeClaim = link;
			}
		}
		disabilityLifeClaimPartList.remove(removeClaim);
		disabilityLifeClaimPartList.add(claimPartLink);
		getNetDisabilityClaimAmount();
		sortDisabilityLifeClaimList();
	}

	public double getNetDisabilityClaimAmount() {
		double totalClaimAmount = 0.0;
		double result = 0.0;
		if (disabilityLifeClaimPartList.size() > 0) {
			for (DisabilityLifeClaimPartLink link : disabilityLifeClaimPartList) {
				totalClaimAmount += link.getDisabilityAmount();
			}

		}
		if (totalClaimAmount > 0) {
			result = totalClaimAmount - claimProposal.getRemainPremium();
		}
		return result;
	}

	public void changeDisabilityPercentage(DisabilityLifeClaimPartLink claimPartLink) {
		double disabilityAmt = claimProposal.getClaimPerson().getSumInsured() * claimPartLink.getPercentage() / 100.00;
		claimPartLink.setDisabilityAmount(disabilityAmt);
		DisabilityLifeClaimPartLink removeClaim = null;
		for (DisabilityLifeClaimPartLink link : disabilityLifeClaimPartList) {
			if (link.getDisabilityPart().getId().equals(claimPartLink.getDisabilityPart().getId())) {
				removeClaim = link;
			}
		}
		disabilityLifeClaimPartList.remove(removeClaim);
		disabilityLifeClaimPartList.add(claimPartLink);
		getNetDisabilityClaimAmount();
		sortDisabilityLifeClaimList();

	}

	private void sortDisabilityLifeClaimList() {
		Collections.sort(disabilityLifeClaimPartList, new Comparator<DisabilityLifeClaimPartLink>() {
			public int compare(DisabilityLifeClaimPartLink o1, DisabilityLifeClaimPartLink o2) {
				if (o1.getDisabilityPart().getName() == null || o2.getDisabilityPart().getName() == null)
					return 0;
				return o2.getDisabilityPart().getName().compareTo(o1.getDisabilityPart().getName());
			}
		});
	}

	public void addNewLifeClaimProposal() {
		try {
			addLifeClaimBeneficiaryPerson();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO("", user.getLoginBranch().getId(), remark, WorkflowTask.APPROVAL, ReferenceType.LIFE_CLAIM, TransactionType.CLAIM, user,
					responsiblePerson);
			if (isDeathClaim) {
				lifeClaimProposalService.addNewLifeClaimProposal(deathClaim, claimProposal, lifeClaimNotification, workFlowDTO, claimAttchmentMap, PROPOSAL_DIR);
			}
			if (isHospitalClaim) {
				lifeClaimProposalService.addNewLifeClaimProposal(hospitalizedClaim, claimProposal, lifeClaimNotification, workFlowDTO, claimAttchmentMap, PROPOSAL_DIR);
			}
			if (isDisibilityClaim) {
				lifeClaimProposalService.addNewLifeClaimProposal(disabilityLifeClaim, claimProposal, lifeClaimNotification, workFlowDTO, claimAttchmentMap, PROPOSAL_DIR);
			}
			isDisPrint = false;
			addInfoMessage(null, MessageId.LIFE_ClAIM_PROCESS_SUCCESS, claimProposal.getClaimProposalNo());
			if (getClaimUploadedFileList().size() > 0) {
				moveUploadedFiles();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private void addLifeClaimBeneficiaryPerson() {
		if (isDeathClaim) {
			for (LCBP001 lcbp001 : beneficiaryList) {
				if (!lcbp001.isDeath()) {
					LifeClaimBeneficiaryPerson claimBeneficiaryPerson = new LifeClaimBeneficiaryPerson();
					claimBeneficiaryPerson.setBeneficiaryRole(MedicalBeneficiaryRole.BENEFICIARY);
					claimBeneficiaryPerson.setBeneficiaryPerson(lcbp001.getBeneficiaryPerson());
					claimProposal.addBeneficiary(claimBeneficiaryPerson);
				}
			}
		} else if (claimSuccessor != null) {
			claimProposal.addBeneficiary(claimSuccessor);
		} else {
			LifeClaimBeneficiaryPerson claimBeneficiaryPerson = new LifeClaimBeneficiaryPerson();
			claimBeneficiaryPerson.setBeneficiaryRole(MedicalBeneficiaryRole.INSURED_PERSON);
			claimBeneficiaryPerson.setPolicyInsuredPerson(claimProposal.getClaimPerson());
			claimProposal.addBeneficiary(claimBeneficiaryPerson);
		}
	}

	public void generatePrintLifePolicyInform() {
		DocumentBuilder.generateDisabilityLifeClaimInformLetter(claimProposal, dirPath, fileName);
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		String formID = "lifeClaimProposalForm";
		PolicyInsuredPerson claimPerson = claimProposal.getClaimPerson();

		if ("claimProposal".equals(event.getOldStep())) {
			if (isDeathClaim && isDisibilityClaim) {
				addErrorMessage(formID + ":selectClaimType", MessageId.DEATH_DISABILITY_CHOOSE);
				valid = false;
			}
		}
		if ("claimProposal".equals(event.getOldStep()) && "claimType".equals(event.getNewStep())) {
			previousPercentage = lifeClaimProposalService.findTotalDisabilityClaimPercentageByClaimPersonId(claimPerson.getId(), claimProposal.getLifePolicy().getId());
			maximumClaimPercentage = 100 - previousPercentage;
			if (isDeathClaim) {
				double deathClaimAmt = 0;
				if (maximumClaimPercentage > 0) {
					deathClaimAmt = claimPerson.getSumInsured() * maximumClaimPercentage / 100;
				} else {
					deathClaimAmt = claimPerson.getSumInsured();
				}
				netClaimAmount = deathClaimAmt - claimProposal.getRemainPremium();
				deathClaim.setDeathClaimAmount(deathClaimAmt);

				// deathClaim.setPolicyNo(claimProposal.getLifePolicy().getPolicyNo());
			} else if (isDisibilityClaim) {
				productDisabilityRateList = productDisabilityPartService.findAllDisabilityRateByProduct(claimProposal.getProduct().getId());
			}
			PrimeFaces.current().resetInputs("lifeClaimProposalForm:claimType");
		}
		if ("claimType".equals(event.getOldStep()) && "beneficiaryTab".equals(event.getNewStep())) {
			if (isDeathClaim) {
				beneficiaryList = new ArrayList<LCBP001>();
				for (PolicyInsuredPersonBeneficiaries benefi : claimPerson.getPolicyInsuredPersonBeneficiariesList()) {
					LCBP001 lcbp = new LCBP001(benefi, false);
					beneficiaryList.add(lcbp);
				}
			} else if (isDisibilityClaim) {
				if (disabilityLifeClaimPartList.size() < 1) {
					addErrorMessage("lifeClaimProposalForm:disabilityRateTable", MessageId.REQUIRED_DISABILITY_PERSONINFO);
					valid = false;
				}
				if (valid) {
					double rate = 0;
					for (DisabilityLifeClaimPartLink link : disabilityLifeClaimPartList) {
						rate += link.getPercentage();
						if (link.getPercentage() == 0) {
							addErrorMessage("lifeClaimProposalForm:disabilityRateTable", MessageId.REQUIRED_DISABILITY_PERCENTAGE);
							valid = false;
							break;
						} else if (link.getCauseofdisability() == null) {
							addErrorMessage("lifeClaimProposalForm:disabilityRateTable", MessageId.CAUSE_OF_DISABILITY_IS_REQUIRED);
							valid = false;
							break;
						} else if (link.getCauseofPropose() == null) {
							addErrorMessage("lifeClaimProposalForm:disabilityRateTable", MessageId.CAUSE_OF_PROPOSE_IS_REQUIRED);
							valid = false;
							break;
						}
					}
					if (rate > maximumClaimPercentage) {
						addErrorMessage("lifeClaimProposalForm:disabilityRateTable", MessageId.MAXIMUM_DISABILITY_PERCENTAGE, maximumClaimPercentage);
						valid = false;
					}
					if (valid) {
						disabilityLifeClaim.setDisabilityLifeClaimList(disabilityLifeClaimPartList);
					}
				}
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public void returnMedicalPlaceDialog(SelectEvent event) {
		Hospital medicalPlace = (Hospital) event.getObject();
		hospitalizedClaim.setMedicalPlace(medicalPlace);
	}

	public void changeClaimType(AjaxBehaviorEvent event) {
		if (selectedClaimTypes != null) {
			isHospitalClaim = false;
			isDeathClaim = false;
			isDisibilityClaim = false;

			for (String claimtype : selectedClaimTypes) {
				if (claimtype.equalsIgnoreCase("Hospitalization")) {
					isHospitalClaim = true;
				} else if (claimtype.equalsIgnoreCase("Disability")) {
					isDisibilityClaim = true;
				} else if (claimtype.equalsIgnoreCase("Death")) {
					isDeathClaim = true;
				}
			}
		}
	}

	public void prepareEditDeathBeneficiary(LCBP001 person) {
		this.selectedBeneficiary = person;
	}

	public void updateDeathBeneficiary() {
		if (selectedBeneficiary.isDeath()) {
			isSuccessor = true;
			claimSuccessor = new LifeClaimBeneficiaryPerson();
			claimSuccessor.setPercentage(selectedBeneficiary.getBeneficiaryPerson().getPercentage());
			// claimSuccessor.setClaimAmount(claimAmount); to add Claim Amount
		} else {
			isSuccessor = false;
			claimSuccessor = null;
		}
		PrimeFaces.current().executeScript("PF('beneficiaryDeathDialog').hide()");
	}

	public LifeClaimNotification getLifeClaimNotification() {
		return lifeClaimNotification;
	}

	public LifePolicyClaim getLifePolicyClaim() {
		return lifePolicyClaim;
	}

	public LifeDeathClaim getDeathClaim() {
		return deathClaim;
	}

	public LifeHospitalizedClaim getHospitalizedClaim() {
		return hospitalizedClaim;
	}

	public void setLifePolicyClaim(LifePolicyClaim lifePolicyClaim) {
		this.lifePolicyClaim = lifePolicyClaim;
	}

	public void setDeathClaim(LifeDeathClaim deathClaim) {
		this.deathClaim = deathClaim;
	}

	public void setHospitalizedClaim(LifeHospitalizedClaim hospitalizedClaim) {
		this.hospitalizedClaim = hospitalizedClaim;
	}

	public LifeClaimProposal getClaimProposal() {
		return claimProposal;
	}

	public void setClaimProposal(LifeClaimProposal claimProposal) {
		this.claimProposal = claimProposal;
	}

	public String[] getSelectedClaimTypes() {
		return selectedClaimTypes;
	}

	public void setSelectedClaimTypes(String[] selectedClaimTypes) {
		this.selectedClaimTypes = selectedClaimTypes;
	}

	public boolean getIsDeathClaim() {
		return isDeathClaim;
	}

	public boolean getIsHospitalClaim() {
		return isHospitalClaim;
	}

	public boolean getIsDisibilityClaim() {
		return isDisibilityClaim;
	}

	public void setDeathClaim(boolean isDeathClaim) {
		this.isDeathClaim = isDeathClaim;
	}

	public void setHospitalClaim(boolean isHospitalClaim) {
		this.isHospitalClaim = isHospitalClaim;
	}

	public void setDisibilityClaim(boolean isDisibilityClaim) {
		this.isDisibilityClaim = isDisibilityClaim;
	}

	public DisabilityLifeClaim getDisabilityLifeClaim() {
		return disabilityLifeClaim;
	}

	public void setDisabilityLifeClaim(DisabilityLifeClaim disabilityLifeClaim) {
		this.disabilityLifeClaim = disabilityLifeClaim;
	}

	public List<DisabilityLifeClaimPartLink> getDisabilityLifeClaimPartList() {
		return disabilityLifeClaimPartList;
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

	public boolean getIsSuccessor() {
		return isSuccessor;
	}

	public void setSuccessor(boolean isSuccessor) {
		this.isSuccessor = isSuccessor;
	}

	public DisabilityLifeClaimPartLink getDisabilityLifeClaimPart() {
		return disabilityLifeClaimPart;
	}

	public void setDisabilityLifeClaimPart(DisabilityLifeClaimPartLink disabilityLifeClaimPart) {
		this.disabilityLifeClaimPart = disabilityLifeClaimPart;
	}

	public void selectUser() {
		selectUser(WorkflowTask.SURVEY, WorkFlowType.LIFE);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public LifeClaimBeneficiaryPerson getClaimSuccessor() {
		return claimSuccessor;
	}

	public List<LCBP001> getBeneficiaryList() {
		return beneficiaryList;
	}

	public void setBeneficiaryList(List<LCBP001> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public LCBP001 getSelectedBeneficiary() {
		return selectedBeneficiary;
	}

	public void setSelectedBeneficiary(LCBP001 selectedBeneficiary) {
		this.selectedBeneficiary = selectedBeneficiary;
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public double getPreviousPercentage() {
		return previousPercentage;
	}

	public void deleteDisabilityClaim(DisabilityLifeClaimPartLink disabilityClaimPart) {
		disabilityLifeClaimPartList.remove(disabilityClaimPart);
	}

	public void handleClaimAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!claimAttchmentMap.containsKey(fileName)) {
			String filePath = temporyDir + fileName;
			claimAttchmentMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir, PROPOSAL_DIR + claimProposal.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeClaimUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			claimAttchmentMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (claimAttchmentMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getClaimUploadedFileList() {
		return new ArrayList<String>(claimAttchmentMap.values());
	}

	public void returnDisabilityPartRate(SelectEvent event) {
		DisabilityLifeClaimPartLink disabilityLifeClaimPartLink;
		List<ProductDisabilityRate> selectedDisabilityRateList = (List<ProductDisabilityRate>) event.getObject();
		if (disabilityLifeClaimPartList.size() > 0) {
			List<DisabilityLifeClaimPartLink> selectedDisabilityLifeClaimList = new ArrayList<DisabilityLifeClaimPartLink>();
			List<ProductDisabilityRate> removeRateList = new ArrayList<ProductDisabilityRate>();
			selectedDisabilityLifeClaimList.addAll(disabilityLifeClaimPartList);
			for (DisabilityLifeClaimPartLink claim : selectedDisabilityLifeClaimList) {
				for (ProductDisabilityRate rate : selectedDisabilityRateList) {
					if (claim.getDisabilityPart().getId().equals(rate.getDisabilityPart().getId())) {
						removeRateList.add(rate);
					}
				}
			}
			selectedDisabilityRateList.removeAll(removeRateList);
			for (ProductDisabilityRate rate : selectedDisabilityRateList) {
				disabilityLifeClaimPartLink = new DisabilityLifeClaimPartLink(rate.getDisabilityPart(), rate.getPercentage());
				disabilityLifeClaimPartLink.setCauseofdisability(disabilityLifeClaimPartList.get(0).getCauseofdisability());
				changeDisabilityPercentage(disabilityLifeClaimPartLink);
			}
		} else {
			for (ProductDisabilityRate rate : selectedDisabilityRateList) {
				disabilityLifeClaimPartLink = new DisabilityLifeClaimPartLink(rate.getDisabilityPart(), rate.getPercentage());
				changeDisabilityPercentage(disabilityLifeClaimPartLink);
			}
		}
		disabilityLifeClaim = new DisabilityLifeClaim();
		sortDisabilityLifeClaimList();
	}

	public void selectDisabilityPart() {
		List<ProductDisabilityRate> disabilityRateList = new ArrayList<ProductDisabilityRate>();
		int i = 0;
		for (DisabilityLifeClaimPartLink claim : disabilityLifeClaimPartList) {
			for (ProductDisabilityRate rate : productDisabilityRateList) {

				if (rate.getDisabilityPart() != null && rate.getDisabilityPart().getId().equals(claim.getDisabilityPart().getId())) {
					disabilityRateList.add(rate);
				}
			}
		}
		selectDisabilityPartRate(claimProposal.getProduct().getId(), disabilityRateList);
	}

	public double getMaximumClaimPercentage() {
		return maximumClaimPercentage;
	}

	public boolean isDisPrint() {
		return isDisPrint;
	}

	public double getRemainPremium() {
		return remainPremium;
	}

	public void setRemainPremium(double remainPremium) {
		this.remainPremium = remainPremium;
	}

	public double getNetClaimAmount() {
		return netClaimAmount;
	}

	public void setNetClaimAmount(double netClaimAmount) {
		this.netClaimAmount = netClaimAmount;
	}

	public boolean isShortTerm() {
		return isShortTerm;
	}

	public void setShortTerm(boolean isShortTerm) {
		this.isShortTerm = isShortTerm;
	}

	public void returnHospital(SelectEvent event) {
		Hospital medicalPlace = (Hospital) event.getObject();
		claimProposal.setHospital(medicalPlace);
	}

}
