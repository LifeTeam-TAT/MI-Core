package org.ace.insurance.payment.trantype.persistence.interfaces;

import java.util.List;

import org.ace.insurance.payment.trantype.TranCode;
import org.ace.insurance.payment.trantype.TranType;
import org.ace.java.component.persistence.exception.DAOException;

public interface ITranTypeDAO {

	public List<TranType> findAll() throws DAOException;

	public TranType findByTransCode(TranCode tranCode) throws DAOException;
}
