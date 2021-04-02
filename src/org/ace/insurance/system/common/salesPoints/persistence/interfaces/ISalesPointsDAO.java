package org.ace.insurance.system.common.salesPoints.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.persistence.exception.DAOException;

public interface ISalesPointsDAO {
	
	public void insert(SalesPoints salesPoints) throws DAOException;

	public void update(SalesPoints salesPoints) throws DAOException;

	public void delete(SalesPoints salesPoints) throws DAOException;
	
	public SalesPoints findById(String id) throws DAOException;
	
	public List<SalesPoints> findAll() throws DAOException;
	
	public SalesPoints findByName(String name) throws DAOException;
	
	public List<SalesPoints> findSalesPointsByBranch(String id) throws DAOException;
}
