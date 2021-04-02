package org.ace.insurance.system.common.operation.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.operation.Operation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IOperationDAO {
	public String insert(Operation operation) throws DAOException;

	public void update(Operation operation) throws DAOException;

	public void delete(Operation operation) throws DAOException;

	public Operation findById(String id) throws DAOException;

	public List<Operation> findAll() throws DAOException;

	public List<Operation> findByCriteria(String criteria) throws DAOException;
}
