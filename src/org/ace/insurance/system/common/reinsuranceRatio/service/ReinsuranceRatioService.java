package org.ace.insurance.system.common.reinsuranceRatio.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRatio;
import org.ace.insurance.system.common.reinsuranceRatio.persistence.interfaces.IReinsuranceRatioDAO;
import org.ace.insurance.system.common.reinsuranceRatio.service.interfaces.IReinsuranceRatioService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ReinsuranceRatioService")
public class ReinsuranceRatioService extends BaseService implements IReinsuranceRatioService {

	@Resource(name = "ReinsuranceRatioDAO")
	private IReinsuranceRatioDAO reinsuranceRatioDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		try {
			reinsuranceRatioDAO.addNewReinsuranceRatio(reinsuranceRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add New ReinsuranceRatio", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateReinsuranceRatio(ReinsuranceRatio reinsuranceRatio) {
		try {
			reinsuranceRatioDAO.updateReinsuranceRatio(reinsuranceRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update ReinsuranceRatio", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ReinsuranceRatio> findReinsuranceRatioListByProductGroupId(String productGroupId) {
		List<ReinsuranceRatio> coinsuranceRatioList = new ArrayList<>();
		try {
			coinsuranceRatioList = reinsuranceRatioDAO.findReinsuranceRatioListByProductGroupId(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find ReinsuranceRatio List By Product Id", e);
		}
		return coinsuranceRatioList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndateByReInRatioId(ReinsuranceRatio reinsuranceRatio) {
		try {
			reinsuranceRatioDAO.updateEndateByReInRatioId(reinsuranceRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update End Date By ReInRatio Id", e);
		}
	}
}
