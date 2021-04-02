package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.operation.Operation;
import org.ace.insurance.system.common.operation.service.interfaces.IOperationService;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean(name = "ManageOperationActionBean")
public class ManageOperationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OperationService}")
	private IOperationService operationService;

	public void setOperationService(IOperationService operationService) {
		this.operationService = operationService;
	}

	private Operation operation;
	private boolean createNew;
	private List<Operation> operationList;
	private String criteria;
	private LazyDataModelUtil<Operation> lazyModel;

	@PostConstruct
	public void init() {
		createNewMedical();
		loadOperationList();

	}

	public void createNewMedical() {
		createNew = true;
		criteria = "";
		operation = new Operation();
	}

	public void loadOperationList() {
		operationList = operationService.findAllOperation();
		lazyModel = new LazyDataModelUtil<Operation>(operationList);
	}

	public void addNewOperation() {
		try {
			operationService.addNewOperation(operation);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, operation.getCode());
			createNewMedical();
			loadOperationList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateOperation() {
		try {
			operationService.updateOperation(operation);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, operation.getCode());
			createNewMedical();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadOperationList();
	}

	public String deleteOperation() {
		try {
			operationService.deleteOperation(operation);
			loadOperationList();
			addInfoMessage(null, MessageId.DELETE_SUCCESS, operation.getCode());
			createNewMedical();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public void searchOperation() {
		operationList = operationService.findByCriteria(criteria);
		lazyModel = new LazyDataModelUtil<Operation>(operationList);
	}

	public void prepareUpdateOperation(Operation operation) {
		createNew = false;
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void selectOperation(Operation operation) {
		RequestContext.getCurrentInstance().closeDialog(operation);
	}

	public LazyDataModelUtil<Operation> getLazyModel() {
		return lazyModel;
	}

}
