package org.ace.insurance.system.common.workshop.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.persistence.exception.DAOException;

public interface IWorkShopDAO {
	public void insert(WorkShop workshop) throws DAOException;

	public void update(WorkShop workshop) throws DAOException;

	public void delete(WorkShop workshop) throws DAOException;

	public WorkShop findById(String id) throws DAOException;

	public List<WorkShop> findAll() throws DAOException;

	public List<WorkShop> findByCriteria(String criteria) throws DAOException;

	public List<WorkShop> findByCriteria(String criteria, String criteriaValue) throws DAOException;

}
