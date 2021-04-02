package org.ace.insurance.payment.trantype.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.payment.trantype.TranCode;
import org.ace.insurance.payment.trantype.TranType;
import org.ace.insurance.payment.trantype.persistence.interfaces.ITranTypeDAO;
import org.ace.insurance.payment.trantype.service.interfaces.ITranTypeService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "TranTypeService")
public class TranTypeService extends BaseService implements ITranTypeService {

	@Resource(name = "TranTypeDAO")
	private ITranTypeDAO tranTypeDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TranType> findAllTranType() {
		List<TranType> result = null;
		try {
			result = tranTypeDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of TranType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TranType findByTransCode(TranCode tranCode) {
		TranType result = null;
		try {
			result = tranTypeDAO.findByTransCode(tranCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find TranType By tranCode)" + tranCode, e);
		}
		return result;
	}

}
