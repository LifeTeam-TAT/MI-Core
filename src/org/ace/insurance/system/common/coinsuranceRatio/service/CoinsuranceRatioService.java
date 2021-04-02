package org.ace.insurance.system.common.coinsuranceRatio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.coinsuranceRatio.CoinsuranceRatio;
import org.ace.insurance.system.common.coinsuranceRatio.persistence.interfaces.ICoinsuranceRatioDAO;
import org.ace.insurance.system.common.coinsuranceRatio.service.interfaces.ICoinsuranceRatioService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("CoinsuranceRatioService")
public class CoinsuranceRatioService extends BaseService implements ICoinsuranceRatioService {

	@Resource(name = "CoinsuranceRatioDAO")
	private ICoinsuranceRatioDAO coinsuranceRatioDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceRatio> findCoinsuranceRatioListByProductGroupId(String productGroupId) {
		List<CoinsuranceRatio> coinsuranceRatioList = new ArrayList<>();
		try {
			coinsuranceRatioList = coinsuranceRatioDAO.findCoinsuranceRatioListByProductGroupId(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CoinsuranceRatio List By Product Id", e);
		}
		return coinsuranceRatioList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		try {
			coinsuranceRatioDAO.addNewCoinsuranceRatio(coinsuranceRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add New CoinsuranceRatio", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		try {
			coinsuranceRatioDAO.updateCoinsuranceRatio(coinsuranceRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update CoinsuranceRatio", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndateByCoInRatioId(CoinsuranceRatio coInRatio) {
		try {
			coinsuranceRatioDAO.updateEndateByCoInRatioId(coInRatio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update EnDate By CoInRatio Id", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CoinsuranceRatio findCoinsuranceRatio(String productGroupId, Date date) {
		CoinsuranceRatio result = null;
		try {
			result = coinsuranceRatioDAO.findCoinsuranceRatio(productGroupId, date);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find CoinsuranceRatio by productGroup and date", e);
		}
		return result;
	}
}
