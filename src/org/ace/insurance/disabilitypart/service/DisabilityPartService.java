package org.ace.insurance.disabilitypart.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.disabilitypart.persistence.interfaces.IDisabilityPartDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "DisabilityPartService")
public class DisabilityPartService implements IDisabilityPartService {

	@Resource(name = "DisabilityPartDAO")
	private IDisabilityPartDAO disabilityPartDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewDisabilityPart(DisabilityPart disabilitypart) {
		try {
			disabilityPartDAO.insert(disabilitypart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new disabilitypart", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDisabilityPart(DisabilityPart disabilitypart) {
		try {
			disabilityPartDAO.delete(disabilitypart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new disabilitypart", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDisabilityPart(DisabilityPart disabilitypart) {
		try {
			disabilityPartDAO.update(disabilitypart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new disabilitypart", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DisabilityPart findById(String disabilitypartId) {
		DisabilityPart result = null;
		try {
			result = disabilityPartDAO.findById(disabilitypartId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new disabilitypart", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DisabilityPart> findAllDisabilityPart() {
		List<DisabilityPart> result = null;
		try {
			result = disabilityPartDAO.findAllDisabilitypart();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find All disabilitypart", e);
		}
		return result;
	}
}
