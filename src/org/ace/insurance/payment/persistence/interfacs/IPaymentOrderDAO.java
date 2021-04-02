package org.ace.insurance.payment.persistence.interfacs;

import org.ace.insurance.payment.PaymentOrder;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPaymentOrderDAO {
	public void insert(PaymentOrder paymentOrder) throws DAOException;
}
