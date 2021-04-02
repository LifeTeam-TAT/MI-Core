package org.ace.insurance.payment.trantype.service.interfaces;

import java.util.List;

import org.ace.insurance.payment.trantype.TranCode;
import org.ace.insurance.payment.trantype.TranType;

public interface ITranTypeService {
	public List<TranType> findAllTranType();

	public TranType findByTransCode(TranCode tranCode);
}
