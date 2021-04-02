package org.ace.insurance.system.common.salesPoints.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.SystemException;

public interface ISalesPointsService {
	public void addNewSalesPoints(SalesPoints salesPoints) throws SystemException;

	public void updateSalesPoints(SalesPoints salesPoints) throws SystemException;

	public void deleteSalesPoints(SalesPoints salesPoints) throws SystemException;
	
	public List<SalesPoints> findAllSalesPoints()throws SystemException;
	
	public SalesPoints findSalesPointsByName(String name) throws SystemException;
	
	public List<SalesPoints> findSalesPointsByBranch(String id) throws SystemException;

}
