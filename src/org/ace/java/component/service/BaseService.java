package org.ace.java.component.service;

import java.util.Properties;

import javax.annotation.Resource;

import org.ace.insurance.common.PeriodType;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;

public class BaseService {
	private final String SUM_INSURED = "SUM_INSURED";
	private final String PUBLIC_LIFE = "PUBLIC_LIFE";
	private final String GROUP_LIFE = "GROUP_LIFE";
	private final String AGE = "AGE";
	private final String TERM = "TERM";
	private final String SHORT_TERM_ENDOWMNENT = "SHORT_TERM_ENDOWMNENT";
	private final String SNAKE_BITE = "SNAKE_BITE";
	private final String SEAMEN = "SEAMEN_LIFE";
	protected final String PROPOSAL = "PROPOSAL";
	protected final String INFORM = "INFORM";
	protected final String CONFIRMATION = "CONFIRMATION";
	protected final String PAYMENT = "PAYMENT";
	protected final String FARMER = "FARMER";

	@Resource(name = "CustomIDGenerator")
	protected ICustomIDGenerator customIDGenerator;

	@Resource(name = "KEYFACTOR_ID_CONFIG")
	private Properties keyFactorConfig;

	@Resource(name = "UserProcessService")
	protected IUserProcessService userProcessService;

	public User getLoginUser() {
		return userProcessService.getLoginUser();
	}

	protected boolean isSumInsured(KeyFactor kf) {
		if (kf.getId().trim().equals(keyFactorConfig.getProperty(SUM_INSURED))) {
			return true;
		}
		return false;
	}

	protected boolean isPublicLife(Product product) {
		if (product.getId().trim().equals(keyFactorConfig.getProperty(PUBLIC_LIFE))) {
			return true;
		}
		return false;
	}

	protected boolean isGroupLife(Product product) {
		if (product.getId().trim().equals(keyFactorConfig.getProperty(GROUP_LIFE))) {
			return true;
		}
		return false;
	}

	protected boolean isSnakeBite(Product product) {
		if (product.getId().trim().equals(keyFactorConfig.getProperty(SNAKE_BITE).trim())) {
			return true;
		}
		return false;
	}
	
	protected boolean isSeaMen(Product product) {
		if (product.getId().trim().equals(keyFactorConfig.getProperty(SEAMEN).trim())) {
			return true;
		}
		return false;
	}

	protected boolean isShortEndowmentLife(Product product) {
		if (product.getId().trim().equals(keyFactorConfig.getProperty(SHORT_TERM_ENDOWMNENT))) {
			return true;
		}
		return false;
	}

	protected boolean isSportMan(Product product) {
		if (KeyFactorChecker.isSportMan(product)) {
			return true;
		}
		return false;
	}

	protected boolean isPersonalAccident(Product product) {
		if (KeyFactorChecker.isPersonalAccident(product)) {
			return true;
		}
		return false;
	}

	protected boolean isTerm(KeyFactor kf) {
		if (kf.getId().trim().equals(keyFactorConfig.getProperty(TERM))) {
			return true;
		}
		return false;
	}

	protected boolean isAge(KeyFactor kf) {
		if (kf.getId().trim().equals(keyFactorConfig.getProperty(AGE))) {
			return true;
		}
		return false;
	}

	public double getPremiumByPeriod(PeriodType periodType, int period, double prmAmt) {
		if (PeriodType.DAY.equals(periodType) && (period >= 1 && period <= 10)) {
			prmAmt = prmAmt / 8;
		} else if (PeriodType.DAY.equals(periodType) && (period >= 11 && period <= 15)) {
			prmAmt = prmAmt / 6;
		} else if ((PeriodType.MONTH.equals(periodType) && (period == 1)) || (PeriodType.DAY.equals(periodType) && (period >= 16 && period <= 31))) {
			prmAmt = prmAmt / 4;
		} else if (PeriodType.MONTH.equals(periodType) && (period == 2)) {
			prmAmt = (prmAmt / 8) * 3;
		} else if (PeriodType.MONTH.equals(periodType) && (period == 3)) {
			prmAmt = prmAmt / 2;
		} else if (PeriodType.MONTH.equals(periodType) && (period == 4)) {
			prmAmt = (prmAmt / 8) * 5;
		} else if (PeriodType.MONTH.equals(periodType) && (period == 5 || period == 6)) {
			prmAmt = (prmAmt / 4) * 3;
		} else {
			// do Nothing yet
		}
		return prmAmt;
	}
}
