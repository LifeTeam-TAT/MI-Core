package org.ace.insurance.product.service;

import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.PremiumRateType;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.StampFeeRateType;
import org.ace.insurance.product.persistence.interfaces.IPremiumCalculatorDAO;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PremiumCalculatorService")
public class PremiumCalculatorService extends BaseService implements IPremiumCalculatorService {

	@Resource(name = "PremiumCalculatorDAO")
	private IPremiumCalculatorDAO premiumCalculatorDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <T> Double calculatePremium(Map<KeyFactor, String> keyfatorValueMap, T param, PremiumCalData data) throws SystemException {
		Double result = null;
		try {
			result = premiumCalculatorDAO.findPremiumRate(keyfatorValueMap, param);
			if (result != null && result > 0)
				result = calulatePremium(result, param, data);
			else {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public <T> Double calulatePremium(double premiumRate, T param, PremiumCalData data) throws SystemException {
		PremiumRateType type = null;
		Double basedAmount = null;
		boolean isGovernmentShortTerm;
		if (param instanceof Product) {
			Product product = (Product) param;
			type = product.getPremiumRateType();
			basedAmount = product.getBasedAmount();
		} else if (param instanceof AddOn) {
			AddOn addOn = (AddOn) param;
			type = addOn.getPremiumRateType();
			basedAmount = addOn.getBasedAmount();
		}
		switch (type) {
			case BASED_ON_OWN_SUMINSURED: {
				premiumRate = (premiumRate * data.getSuminsured()) / basedAmount;
			}
				break;

			case BASED_ON_MAINCOVER_SUMINSURED: {
				premiumRate = (premiumRate * data.getMainCoverSuminsured()) / basedAmount;
			}
				break;

			case PER_UNIT: {
				premiumRate = premiumRate * data.getUnit();
			}
				break;

			case BASED_ON_PREMIUM: {
				premiumRate = (premiumRate * data.getMainCoverPremium()) / basedAmount;
			}
				break;

			case USER_DEFINED_PREMIUM:
			case FIXED:
				break;
		}
		return premiumRate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param) throws SystemException {
		Double result = null;
		try {
			result = premiumCalculatorDAO.findPremiumRate(keyfatorValueMap, param);
			if (result == null || result < 0) {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double calculateStampFee(Product product, PremiumCalData data) throws SystemException {
		double stampFee = 0.0;
		double stampFeeRate = product.getStampFee();
		StampFeeRateType type = product.getStampFeeRateType();
		if (type != null)
			switch (type) {
				case BASEDONSI: {
					int stampFeeCount = (int) (data.getMainCoverSuminsured() / product.getStampFeeBasedAmount());
					stampFee = stampFeeRate * stampFeeCount;
				}
					break;
				case BASEONUNIT:{
					stampFee = stampFeeRate * data.getUnit();
				}
					break;
				case FIXED: {
					stampFee = stampFeeRate;
				}
					break;
				default:
					break;
			}
		return stampFee;
	}
}