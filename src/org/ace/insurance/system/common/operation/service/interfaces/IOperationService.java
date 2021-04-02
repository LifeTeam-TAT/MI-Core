package org.ace.insurance.system.common.operation.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.operation.Operation;

public interface IOperationService {
	public String addNewOperation(Operation idoperation);

	public void updateOperation(Operation idoperation);
	
	public void deleteOperation(Operation idoperation);

	public List<Operation> findAllOperation();

	public Operation findbyId(String id);

	public List<Operation> findByCriteria(String criteria);

}
