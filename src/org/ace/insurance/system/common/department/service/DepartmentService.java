package org.ace.insurance.system.common.department.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.department.DEP001;
import org.ace.insurance.system.common.department.Department;
import org.ace.insurance.system.common.department.persistence.interfaces.IDepartmentDAO;
import org.ace.insurance.system.common.department.service.interfaces.IDepartmentService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;

@Service(value = "DepartmentService")
public class DepartmentService implements IDepartmentService {
	
	@Resource(name = "DepartmentDAO")
	private IDepartmentDAO departmentDAO;

	@Override
	public void addNewDepartment(Department department) {
		try {
			departmentDAO.insert(department);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Department", e);
		}
	}

	@Override
	public void updateDepartment(Department department) {
		try {
			departmentDAO.update(department);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Department", e);
		}
	}

	@Override
	public void deleteDepartment(Department department) {
		try {
			departmentDAO.delete(department);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Department", e);
		}
	}

	@Override
	public Department findDepartmentById(String id) {
		Department result = null;
		try {
			result = departmentDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Department (ID : " + id + ")", e);
		}
		return result;
	}

	@Override
	public List<Department> findAllDepartment() {
		List<Department> result = null;
		try {
			result = departmentDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Department)", e);
		}
		return result;
	}

	@Override
	public List<DEP001> findAll_DEP001() {
		List<DEP001> result = null;
		try {
			result = departmentDAO.findAll_DEP001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all DEP001 ", e);
		}
		return result;
	}

	@Override
	public void updateActivePolicy(int policyCount, String departmentId) {
		try {
			departmentDAO.updateActivePolicy(policyCount, departmentId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update activePolicy", e);
		}
	}

}
