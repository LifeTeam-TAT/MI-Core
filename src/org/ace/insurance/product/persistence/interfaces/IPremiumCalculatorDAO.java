package org.ace.insurance.product.persistence.interfaces;

import java.util.Map;

import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPremiumCalculatorDAO {

	public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param) throws DAOException;

}
