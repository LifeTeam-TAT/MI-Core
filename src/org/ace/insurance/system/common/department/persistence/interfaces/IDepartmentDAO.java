package org.ace.insurance.system.common.department.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.department.DEP001;
import org.ace.insurance.system.common.department.Department;
import org.ace.java.component.persistence.exception.DAOException;

public interface IDepartmentDAO {
	
	public void insert(Department Company) throws DAOException;

	public void update(Department Company) throws DAOException;

	public void delete(Department Company) throws DAOException;

	public Department findById(String id) throws DAOException;

	public List<Department> findAll() throws DAOException;

	public List<DEP001> findAll_DEP001() throws DAOException;

	public void updateActivePolicy(int policyCount, String departmentId) throws DAOException;

	public void updateActivedPolicyDate(Date activedDate, String departmentId) throws DAOException;

}
