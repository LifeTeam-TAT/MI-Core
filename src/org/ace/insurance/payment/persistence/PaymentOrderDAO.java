package org.ace.insurance.payment.persistence;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import org.ace.insurance.payment.PaymentOrder;
import org.ace.insurance.payment.persistence.interfacs.IPaymentOrderDAO;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PaymentOrderDAO")
public class PaymentOrderDAO extends BasicDAO implements IPaymentOrderDAO {

	@Resource(name = "IDConfigLoader")
	private IDConfigLoader idConfigLoader;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(PaymentOrder paymentOrder) throws DAOException {
		try {
			em.persist(paymentOrder);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Payment Order", pe);
		}
	}

}
