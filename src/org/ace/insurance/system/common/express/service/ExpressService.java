/***************************************************************************************
 * @author <<Myo Thiha Kyaw>>
 * @Date 2014-06-18
 * @Version 1.0
 * @Purpose <<For Travel Insurance>>
 * 
 *    
 ***************************************************************************************/

package org.ace.insurance.system.common.express.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.ExpressCriteria;
import org.ace.insurance.system.common.express.Express;
import org.ace.insurance.system.common.express.persistence.interfaces.IExpressDAO;
import org.ace.insurance.system.common.express.service.interfaces.IExpressService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpressService")
public class ExpressService extends BaseService implements IExpressService {

	@Resource(name = "ExpressDAO")
	private IExpressDAO expressDAO;

	public void addNewExpress(Express express) {
		try {
			expressDAO.insert(express);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Express", e);
		}
	}

	public void updateExpress(Express express) {
		try {
			expressDAO.update(express);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Express", e);
		}
	}

	public void deleteExpress(Express express) {
		try {
			expressDAO.delete(express);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Express", e);
		}
	}

	public List<Express> findAllExpress() {
		List<Express> result = null;
		try {
			result = expressDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Express)", e);
		}
		return result;
	}

	public Express findExpressById(String id) {
		Express result = null;
		try {
			result = expressDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Express (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Express> findExpressByCriteria(ExpressCriteria criteria, int max) {
		List<Express> result = null;
		try {
			result = expressDAO.findByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find by criteria.", e);
		}
		return result;
	}
}
