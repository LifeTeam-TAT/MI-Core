package org.ace.insurance.system.common.packing.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.packing.Packing;
import org.ace.insurance.system.common.packing.persistence.interfaces.IPackingDAO;
import org.ace.insurance.system.common.packing.service.interefaces.IPackingService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PackingService")
public class PackingService extends BaseService implements IPackingService {

	@Resource(name = "PackingDAO")
	private IPackingDAO packingDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPacking(Packing packing) {
		try {			
			packingDAO.insert(packing);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Packing", e);
		}
	}	
	

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePacking(Packing packing) {
		try {
			packingDAO.update(packing);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Packing", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePacking(Packing packing) {
		try {
			packingDAO.delete(packing);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Packing", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Packing> findAllPacking() {
		List<Packing> result = null;
		try {
			result = packingDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Packing)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Packing findPackingById(String id) {
		Packing result = null;
		try {
			result = packingDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Packing (ID : " + id + ")", e);
		}
		return result;
	}
}
