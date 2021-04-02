package org.ace.insurance.system.common.department.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.department.DEP001;
import org.ace.insurance.system.common.department.Department;


public interface IDepartmentService {
	public void addNewDepartment(Department department);

	public void updateDepartment(Department department);

	public void deleteDepartment(Department department);

	public Department findDepartmentById(String id);

	public List<Department> findAllDepartment();

	public List<DEP001> findAll_DEP001();

	public void updateActivePolicy(int policyCount, String departmentId);

}
