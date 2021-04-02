package org.ace.insurance.web.manage.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.medical.productprocess.ActiveStatus;
import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.surveyquestion.factory.ProductProcessFactory;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ProductProcessConfigActionBean")
public class ProductProcessConfigActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProductProcessService}")
	private IProductProcessService productProcessService;

	public void setProductProcessService(IProductProcessService productProcessService) {
		this.productProcessService = productProcessService;
	}

	@ManagedProperty(value = "#{ProductProcessConfigFrontService}")
	private IProductProcessConfigFrontService processConfigFrontService;

	public void setProcessConfigFrontService(IProductProcessConfigFrontService processConfigFrontService) {
		this.processConfigFrontService = processConfigFrontService;
	}

	private boolean deactivate;
	private boolean activate;
	private ProductProcessDTO selectedProductProcess;
	private List<ProductProcessDTO> productProcessList;
	private List<ProductProcessDTO> selectedPPList;

	@PostConstruct
	public void init() {
		createNewProductProcess();
	}

	public void createNewProductProcess() {
		selectedPPList = new ArrayList<ProductProcessDTO>();
		productProcessList = new ArrayList<ProductProcessDTO>();
		for (ProductProcess productProcess : productProcessService.findProPActivateConfigure()) {
			productProcessList.add(ProductProcessFactory.getProductProcessDTO(productProcess));
		}
	}

	public void changeStatus(boolean status) {
		ProductProcessDTO ppDTO = null;
		if (productProcessList.contains(selectedProductProcess)) {
			ppDTO = productProcessList.get(productProcessList.indexOf(selectedProductProcess));
		}
		if (status) {
			if (!checkStatus()) {
				ppDTO.setActiveStatus(ActiveStatus.ACTIVATE);
				ppDTO.setStartDate(new Date());
				ppDTO.setEvent(true);
				selectedPPList.add(ppDTO);
			} else {
				addWranningMessage(null, "ONLYONE_ACTIVE", null);
			}
		} else {
			ppDTO.setActiveStatus(ActiveStatus.DEACTIVATE);
			ppDTO.setEndDate(new Date());
			selectedPPList.add(ppDTO);
		}
	}

	public boolean checkStatus() {
		String selectProcessId = selectedProductProcess.getProcess().getId();
		String selectProductId = selectedProductProcess.getProduct().getId();
		if (!KeyFactorChecker.isStudentLife(selectedProductProcess.getProduct().getId()) && !KeyFactorChecker.isPublicTermLife(selectedProductProcess.getProduct().getId())) {
			for (ProductProcessDTO ppDTO : productProcessList) {
				if (selectProcessId.equals(ppDTO.getProcess().getId()) && selectProductId.equals(ppDTO.getProduct().getId())
						&& ppDTO.getActiveStatus().equals(ActiveStatus.ACTIVATE)) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateProductProcess() {
		String ppNo = null;
		boolean flag = false;
		for (ProductProcessDTO pp : selectedPPList) {
			/*
			 * original => String prefix = pp.getProduct().getPrefix() + "_"+
			 * pp.getProcess().getPrefix();
			 */
			String prefix = pp.getProcess().getPrefix();
			if (pp.isEvent()) {
				flag = true;
				ppNo = processConfigFrontService.findGrateQuestionSetNo(pp.getProduct().getId(), pp.getProcess().getId());
			}
			processConfigFrontService.updateProductProcessConfigActivateDeactivate(pp, prefix, ppNo, flag);
		}
		createNewProductProcess();
		addInfoMessage(null, MessageId.UPDATE_SUCCESS, "This Process ");
	}

	public ProductProcessDTO getSelectedProductProcess() {
		return selectedProductProcess;
	}

	public void setSelectedProductProcess(ProductProcessDTO selectedProductProcess) {
		this.selectedProductProcess = selectedProductProcess;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public boolean isDeactivate() {
		return deactivate;
	}

	public void setDeactivate(boolean deactivate) {
		this.deactivate = deactivate;
	}

	public List<ProductProcessDTO> getProductProcessList() {
		return productProcessList;
	}

	public void setProductProcessList(List<ProductProcessDTO> productProcessList) {
		this.productProcessList = productProcessList;
	}
}
