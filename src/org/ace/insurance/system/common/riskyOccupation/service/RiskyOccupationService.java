package org.ace.insurance.system.common.riskyOccupation.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;
import org.ace.insurance.system.common.riskyOccupation.persistence.interfaces.IRiskyOccupationDAO;
import org.ace.insurance.system.common.riskyOccupation.service.interfaces.IRiskyOccupationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "RiskyOccupationService")
public class RiskyOccupationService extends BaseService implements IRiskyOccupationService {
	
	@Resource(name = "RiskyOccupationDAO")
	private IRiskyOccupationDAO riskyOccupationDAO;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addNewRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.insert(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new RiskyOccupation", e);
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.update(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update RiskyOccupation", e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.delete(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete RiskyOccupation", e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public RiskyOccupation findRiskyOccupationById(String id) {
		RiskyOccupation result=null;
		try {
			result=riskyOccupationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find RiskyOccupation by Id", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<RiskyOccupation> findAllRiskyOccupation() {
		List<RiskyOccupation> result=null;
		try {
			result=riskyOccupationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All RiskyOccupation", e);
		}
		return result;
	}

}
