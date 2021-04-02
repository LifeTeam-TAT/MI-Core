package org.ace.insurance.web.mobileforlife;


import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalTemp;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.product.Product;
import org.ace.insurance.proxy.LIF001;
import org.ace.insurance.proxy.MED001;
import org.ace.insurance.proxy.WF001;
import org.ace.insurance.proxy.WorkflowCriteria;
import org.ace.insurance.proxy.service.interfaces.IProxyService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageMoblieForLifeActionBean")
public class ManageMoblieForLifeActionBean extends BaseBean {
	
	
	@ManagedProperty(value = "#{ProxyService}")
	private IProxyService proxyService;

	public void setProxyService(IProxyService proxyService) {
		this.proxyService = proxyService;
	}

	
	
	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}
	
	
	private User user;
	private User responsiblePerson;
	private List<LifeProposalDTO> lifeProposalList;
	private LifeProposal lifeproposal;
	private LifeProposalTemp lifeProposalTemp;
	private boolean isSportMan;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSnakeBite;
	private boolean isPublicTermLife;
	private boolean isEndorse;
	private boolean isShortTermEndowment;
	private boolean isConfirmEdit;
	private boolean isSurveyAgain;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private Product product;
	private String remark;
	
	private WF001 selectedTask;
	
	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		loadLifePropoposalList();
	}
	
	private void loadLifePropoposalList() {
		lifeProposalList = lifeProposalService.findLifeProposal();
	}
	
	public void convertTempToProposal(String proposalNo) {
		lifeProposalTemp = lifeProposalService.findLifeProposalByProposalNoFromTerm(proposalNo);
		WorkFlowDTO workFlowDTO = null;
		WorkflowTask workflowTask = null;
		ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
				: isFarmer ? ReferenceType.FARMER
						: isSnakeBite ? ReferenceType.SNAKE_BITE
								: isShortTermEndowment ? ReferenceType.SHORT_ENDOWMENT_LIFE
										: isGroupLife ? ReferenceType.GROUP_LIFE
												: isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : isPublicTermLife ? ReferenceType.PUBLIC_TERM_LIFE : ReferenceType.SPORT_MAN;
//		lifeProposalTemp.setPeriodMonth(getPeriodOfMonths());
		workflowTask = (isSportMan || isSnakeBite || isPersonalAccident) ? WorkflowTask.APPROVAL
				: (isConfirmEdit && !isSurveyAgain) ? WorkflowTask.APPROVAL : WorkflowTask.SURVEY;
		
		workFlowDTO = new WorkFlowDTO(lifeProposalTemp.getId(), lifeProposalTemp.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
				responsiblePerson);
		
		lifeProposalTemp.setProposalType(ProposalType.UNDERWRITING);
		lifeproposal = addLifeProposalFromTemp(lifeProposalTemp);
		lifeProposalService.addNewLifeProposal(lifeproposal, workFlowDTO, RequestStatus.PROPOSED.name());
	}
	
	public int getPeriodOfMonths() {
		if (KeyFactorChecker.isPersonalAccident(product)) {
			return lifeProposalTemp.getPeriodMonth();
		} else {
			return lifeProposalTemp.getPeriodMonth() * 12;
		}
	}
	
	public LifeProposal addLifeProposalFromTemp(LifeProposalTemp temp) {
		
		lifeproposal.setAgent(temp.getAgent());
		lifeproposal.setAttachmentList(temp.getAttachmentList());
		lifeproposal.setBranch(temp.getBranch());
		lifeproposal.setComplete(temp.getComplete());
		lifeproposal.setCurrencyRate(temp.getCurrencyRate());
		lifeproposal.setCustomer(temp.getCustomer());
		lifeproposal.setCustomerClsOfHealth(temp.getCustomerClsOfHealth());
		lifeproposal.setCustomerMedicalCheckUpAttachmentList(temp.getCustomerMedicalCheckUpAttachmentList());
		lifeproposal.setCustomerSurveyQuestionAnswerList(temp.getCustomerSurveyQuestionAnswerList());
		lifeproposal.setEndDate(temp.getEndDate());
		lifeproposal.setId(temp.getId());
		lifeproposal.setNonFinancialEndorse(temp.isNonFinancialEndorse());
		lifeproposal.setSkipPaymentTLF(temp.isSkipPaymentTLF());
		lifeproposal.setLifePolicy(temp.getLifePolicy());
		lifeproposal.setOrganization(temp.getOrganization());
		lifeproposal.setPaymentTerm(temp.getPaymentTerm());
		lifeproposal.setPaymentType(temp.getPaymentType());
		lifeproposal.setPeriodMonth(temp.getPeriodMonth());
		lifeproposal.setPortalId(temp.getPortalId());
		lifeproposal.setProposalInsuredPersonList(temp.getProposalInsuredPersonList());
		lifeproposal.setProposalNo(temp.getProposalNo());
		lifeproposal.setProposalStatus(temp.getProposalStatus());
		lifeproposal.setProposalType(temp.getProposalType());
		lifeproposal.setSaleChannelType(temp.getSaleChannelType());
		lifeproposal.setSalesPoints(temp.getSalesPoints());
		lifeproposal.setSpecialDiscount(temp.getSpecialDiscount());
		lifeproposal.setStartDate(temp.getStartDate());
		lifeproposal.setSubmittedDate(temp.getSubmittedDate());
		lifeproposal.setVersion(temp.getVersion());
		
		return lifeproposal;
	}
	
	
	protected Object getParam(String key) {
		return getSessionMap().get(key);
	}
	
	protected Map<String, Object> getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	protected static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	

	public  LifeProposal getLifeproposal() {
		return lifeproposal;
	}

	public void setLifeproposal(LifeProposal lifeproposal) {
		this.lifeproposal = lifeproposal;
	}

	public List<LifeProposalDTO> getLifeProposalList() {
		return lifeProposalList;
	}

	public void setLifeProposalList(List<LifeProposalDTO> lifeProposalList) {
		this.lifeProposalList = lifeProposalList;
	}

	public ILifeProposalService getLifeProposalService() {
		return lifeProposalService;
	}


	protected void putParam(String key, Object obj) {
		getSessionMap().put(key, obj);
	}
	
	
	private List<LIF001> lifeTasks;

	public List<LIF001> getLifeTasks() {
		return lifeTasks;
	}
	
	

	private List<MED001> healthTasks;

	public List<MED001> getHealthTasks() {
		return healthTasks;
	}


	public void selectUser() {
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isSnakeBite ? WorkFlowType.SNAKE_BITE
						: isFarmer ? WorkFlowType.FARMER
								: isShortTermEndowment ? WorkFlowType.SHORT_ENDOWMENT : isPublicTermLife ? WorkFlowType.PUBLIC_TERM_LIFE : WorkFlowType.LIFE;
		WorkflowTask workflowTask = isEndorse ? WorkflowTask.SURVEY
				: (isSportMan || isSnakeBite || isPersonalAccident) ? WorkflowTask.APPROVAL : (isConfirmEdit && !isSurveyAgain) ? WorkflowTask.APPROVAL : WorkflowTask.SURVEY;
		selectUser(workflowTask, workFlowType, isEndorse ? TransactionType.ENDORSEMENT : TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}


	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public WF001 getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(WF001 selectedTask) {
		this.selectedTask = selectedTask;
	}

	public IProxyService getProxyService() {
		return proxyService;
	}

	public void setLifeTasks(List<LIF001> lifeTasks) {
		this.lifeTasks = lifeTasks;
	}

	public boolean isSportMan() {
		return isSportMan;
	}

	public void setSportMan(boolean isSportMan) {
		this.isSportMan = isSportMan;
	}

	public boolean isPersonalAccident() {
		return isPersonalAccident;
	}

	public void setPersonalAccident(boolean isPersonalAccident) {
		this.isPersonalAccident = isPersonalAccident;
	}

	public boolean isFarmer() {
		return isFarmer;
	}

	public void setFarmer(boolean isFarmer) {
		this.isFarmer = isFarmer;
	}

	public boolean isSnakeBite() {
		return isSnakeBite;
	}

	public void setSnakeBite(boolean isSnakeBite) {
		this.isSnakeBite = isSnakeBite;
	}

	public boolean isPublicTermLife() {
		return isPublicTermLife;
	}

	public void setPublicTermLife(boolean isPublicTermLife) {
		this.isPublicTermLife = isPublicTermLife;
	}

	public boolean isEndorse() {
		return isEndorse;
	}

	public void setEndorse(boolean isEndorse) {
		this.isEndorse = isEndorse;
	}

	public boolean isShortTermEndowment() {
		return isShortTermEndowment;
	}

	public void setShortTermEndowment(boolean isShortTermEndowment) {
		this.isShortTermEndowment = isShortTermEndowment;
	}

	public boolean isConfirmEdit() {
		return isConfirmEdit;
	}

	public void setConfirmEdit(boolean isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
	}

	public boolean isSurveyAgain() {
		return isSurveyAgain;
	}

	public void setSurveyAgain(boolean isSurveyAgain) {
		this.isSurveyAgain = isSurveyAgain;
	}

	public boolean isGroupLife() {
		return isGroupLife;
	}

	public void setGroupLife(boolean isGroupLife) {
		this.isGroupLife = isGroupLife;
	}

	public boolean isEndownmentLife() {
		return isEndownmentLife;
	}

	public void setEndownmentLife(boolean isEndownmentLife) {
		this.isEndownmentLife = isEndownmentLife;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setHealthTasks(List<MED001> healthTasks) {
		this.healthTasks = healthTasks;
	}
	
	
}
