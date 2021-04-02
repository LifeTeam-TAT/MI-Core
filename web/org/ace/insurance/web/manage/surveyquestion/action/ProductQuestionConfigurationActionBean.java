package org.ace.insurance.web.manage.surveyquestion.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.medical.process.Process;
import org.ace.insurance.medical.productprocess.PriorityControlType;
import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.medical.productprocess.StudentAgeType;
import org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.product.ProductProcessDTO;
import org.ace.insurance.web.manage.surveyquestion.ProductProcessQuestionLinkDTO;
import org.ace.insurance.web.manage.surveyquestion.factory.ProductProcessFactory;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ProductQuestionConfigurationActionBean")
public class ProductQuestionConfigurationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductProcessConfigFrontService}")
	private IProductProcessConfigFrontService productProcessConfigFrontService;

	public void setProductProcessConfigFrontService(IProductProcessConfigFrontService productProcessConfigFrontService) {
		this.productProcessConfigFrontService = productProcessConfigFrontService;
	}

	@ManagedProperty(value = "#{ProductProcessService}")
	private IProductProcessService productProcessService;

	public void setProductProcessService(IProductProcessService productProcessService) {
		this.productProcessService = productProcessService;
	}

	@ManagedProperty(value = "#{SurveyQuestionService}")
	private ISurveyQuestionService surveyQuestionService;

	public void setSurveyQuestionService(ISurveyQuestionService surveyQuestionService) {
		this.surveyQuestionService = surveyQuestionService;
	}

	@ManagedProperty(value = "#{ProductProcessValidator}")
	private DTOValidator<ProductProcessDTO> productProcessValidator;

	public void setProductProcessValidator(DTOValidator<ProductProcessDTO> productProcessValidator) {
		this.productProcessValidator = productProcessValidator;
	}

	private List<SurveyQuestion> selectedQuestionList;
	private List<SurveyQuestion> surveyQuestionList;
	private List<ProductProcess> productProcessList;
	private ProductProcessDTO productProcessDto;
	private ProductProcess selectedProductProcess;
	private boolean createNew;
	private PriorityControlType priorityControlType;
	private boolean checked;
	private boolean isStudentLife;
	private boolean isPublicTermLife;

	@PostConstruct
	public void init() {
		createNewProductProcess();
	}

	public void createNewProductProcess() {
		productProcessDto = new ProductProcessDTO();
		selectedProductProcess = new ProductProcess();
		selectedQuestionList = new ArrayList<SurveyQuestion>();
		productProcessList = productProcessService.findProPByActiveStatus();
		surveyQuestionList = surveyQuestionService.findAllSurveyQuestion();
		createNew = true;
	}

	public void addQuestion() {
		priorityControlType = PriorityControlType.CUSTOMIZE;
		this.checked = false;
		List<ProductProcessQuestionLinkDTO> oldProductProcessQuestionList = new ArrayList<ProductProcessQuestionLinkDTO>();
		for (ProductProcessQuestionLinkDTO ppqlDto : productProcessDto.getProductProcessQuestionLinkList()) {
			oldProductProcessQuestionList.add(ppqlDto);
		}

		productProcessDto.setProductProcessQuestionLinkList(new ArrayList<ProductProcessQuestionLinkDTO>());
		for (SurveyQuestion surveyQuestion : selectedQuestionList) {
			ProductProcessQuestionLinkDTO proQueDto = new ProductProcessQuestionLinkDTO(surveyQuestion);
			for (ProductProcessQuestionLinkDTO ppqlDto : oldProductProcessQuestionList) {
				if (ppqlDto.getSurveyQuestion().getId().equals(surveyQuestion.getId())) {
					proQueDto.setPriority(ppqlDto.getPriority());
					proQueDto.setOption(ppqlDto.isOption());
					break;
				}

			}
			productProcessDto.addProductProcessQuestionLink(proQueDto);
		}

	}

	public void addNewProductProcess() {
		try {
			ValidationResult result = productProcessValidator.validate(productProcessDto);
			if (result.isVerified()) {
				ProductProcess productProcess = ProductProcessFactory.getProductProcess(productProcessDto);
				productProcessConfigFrontService.addNewProductProcessConfig(productProcess);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, productProcess.getId());
				createNewProductProcess();
			} else {
				for (ErrorMessage message : result.getErrorMeesages()) {
					addErrorMessage(message.getId(), message.getErrorcode(), message.getParams());
				}
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateProductProcess() {
		try {
			ValidationResult result = productProcessValidator.validate(productProcessDto);
			if (result.isVerified()) {
				ProductProcess productProcess = ProductProcessFactory.getProductProcess(productProcessDto);
				productProcessConfigFrontService.updateProductProcessConfig(productProcess);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, productProcess.getId());
				createNewProductProcess();
			} else {
				for (ErrorMessage message : result.getErrorMeesages()) {
					addErrorMessage(message.getId(), message.getErrorcode(), message.getParams());
				}
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteProductProcess() {
		try {
			productProcessConfigFrontService.deleteProductProcessConfig(selectedProductProcess);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, selectedProductProcess.getId());
			createNewProductProcess();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public void prepareUpdateProductProcess(ProductProcess productProcess) {
		this.productProcessDto = ProductProcessFactory.getProductProcessDTO(productProcess);
		selectedQuestionList = new ArrayList<SurveyQuestion>();
		for (ProductProcessQuestionLinkDTO ppqlDto : productProcessDto.getProductProcessQuestionLinkList()) {
			selectedQuestionList.add(ppqlDto.getSurveyQuestion());
		}
		createNew = false;
	}

	public void changeQuestionOption(AjaxBehaviorEvent e) {
		for (ProductProcessQuestionLinkDTO ppqlDto : productProcessDto.getProductProcessQuestionLinkList()) {
			ppqlDto.setOption(checked);
		}
	}

	public void changeQuestionOptionButton(AjaxBehaviorEvent e) {
		this.checked = false;
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		productProcessDto.setProduct(product);
		isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
		isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
	}

	public void removeProduct() {
		productProcessDto.setProduct(null);
		isStudentLife = false;
		isPublicTermLife = false;
	}

	public void returnProcess(SelectEvent event) {
		Process process = (Process) event.getObject();
		productProcessDto.setProcess(process);
	}

	public ProductProcessDTO getProductProcessDto() {
		return productProcessDto;
	}

	public void setProductProcessDto(ProductProcessDTO productProcessDto) {
		this.productProcessDto = productProcessDto;
	}

	public List<SurveyQuestion> getQuestionList() {
		return surveyQuestionList;
	}

	public List<ProductProcessQuestionLink> getSortQuestionList() {
		return sortByValue(selectedProductProcess.getProductProcessQuestionLinkList());
	}

	public List<ProductProcess> getConfigAndDeactivatePPList() {
		if (selectedProductProcess.getProcess() == null && selectedProductProcess.getProduct() == null) {
			List<ProductProcess> processeList = new ArrayList<ProductProcess>();
			return processeList;
		}
		return productProcessConfigFrontService.findConfigAndDeactivatePP(selectedProductProcess.getProduct().getId(), selectedProductProcess.getProcess().getId());
	}

	public List<SurveyQuestion> getSelectedQuestionList() {
		return selectedQuestionList;
	}

	public void setSelectedQuestionList(List<SurveyQuestion> selectedQuestionList) {
		this.selectedQuestionList = selectedQuestionList;
	}

	public List<ProductProcess> getProductProcessList() {
		return productProcessList;
	}

	public ProductProcess getSelectedProductProcess() {
		return selectedProductProcess;
	}

	public void setSelectedProductProcess(ProductProcess selectedProductProcess) {
		this.selectedProductProcess = selectedProductProcess;
	}

	public PriorityControlType getPriorityControlType() {
		return priorityControlType;
	}

	public void setPriorityControlType(PriorityControlType priorityControlType) {
		this.priorityControlType = priorityControlType;
	}

	public PriorityControlType[] getPriorityControlTypes() {
		return PriorityControlType.values();
	}

	public BuildingOccupationType[] getBuildingOccupationTypes() {
		return BuildingOccupationType.values();
	}

	public void deleteQuestion(ProductProcessQuestionLinkDTO processQuestionLink) {
		productProcessDto.removeProductProcessQuestionLink(processQuestionLink);
		if (selectedQuestionList.contains(processQuestionLink.getSurveyQuestion())) {
			selectedQuestionList.remove(processQuestionLink.getSurveyQuestion());
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	private List<ProductProcessQuestionLink> sortByValue(List<ProductProcessQuestionLink> linkList) {
		List<ProductProcessQuestionLink> list = new LinkedList<ProductProcessQuestionLink>(linkList);
		Collections.sort(list, new Comparator<ProductProcessQuestionLink>() {
			public int compare(ProductProcessQuestionLink o1, ProductProcessQuestionLink o2) {
				int l1 = o1.getPriority();
				int l2 = o2.getPriority();
				if (l1 > l2) {
					return 1;
				} else if (l1 < l2) {
					return -1;
				} else {
					return 0;
				}
			}

		});

		List<ProductProcessQuestionLink> result = new LinkedList<ProductProcessQuestionLink>();
		for (ProductProcessQuestionLink ppq : list) {
			result.add(ppq);
		}
		return result;
	}

	public void changePriorityControl(AjaxBehaviorEvent e) {
		List<ProductProcessQuestionLinkDTO> prodProQueDTOList = productProcessDto.getProductProcessQuestionLinkList();
		if (priorityControlType.equals(PriorityControlType.CUSTOMIZE)) {
			for (ProductProcessQuestionLinkDTO prodProQueDTO : prodProQueDTOList) {
				prodProQueDTO.setPriority(0);
			}
		} else if (priorityControlType.equals(PriorityControlType.ACENDING)) {
			for (int i = 0; i < prodProQueDTOList.size(); i++) {
				prodProQueDTOList.get(i).setPriority(i + 1);
			}
		} else {
			int i = prodProQueDTOList.size();
			for (ProductProcessQuestionLinkDTO prodProQueDTO : prodProQueDTOList) {
				prodProQueDTO.setPriority(i);
				i--;
			}
		}
	}

	public List<SurveyQuestion> createQuestionList(ProductProcess productProcess) {
		List<SurveyQuestion> result = null;
		if (productProcess == null) {
			result = new ArrayList<SurveyQuestion>();
		} else {
			result = surveyQuestionService.findSurveyQuestionBypp(productProcess.getId());
		}

		return result;
	}

	public StudentAgeType[] getStudentAgeTypes() {
		return StudentAgeType.values();
	}

	public boolean isStudentLife() {
		return isStudentLife;
	}

	public boolean isPublicTermLife() {
		return isPublicTermLife;
	}

}
