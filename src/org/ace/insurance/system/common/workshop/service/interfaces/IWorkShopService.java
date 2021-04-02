package org.ace.insurance.system.common.workshop.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.workshop.WorkShop;

public interface IWorkShopService {

	public void insert(WorkShop workshop);

	public void update(WorkShop workshop);

	public void delete(WorkShop workshop);

	public WorkShop findById(String id);

	public List<WorkShop> findAll();

	public List<WorkShop> findByCriteria(String criteria);

	public List<WorkShop> findByCriteria(String criteria, String criteriaValue);
}
