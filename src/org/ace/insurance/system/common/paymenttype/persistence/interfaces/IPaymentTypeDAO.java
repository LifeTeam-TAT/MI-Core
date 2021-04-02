/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.paymenttype.persistence.interfaces;

import java.util.List;

import javax.persistence.NoResultException;

import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPaymentTypeDAO {
	public void insert(PaymentType PaymentType) throws DAOException;

	public void update(PaymentType PaymentType) throws DAOException;

	public void delete(PaymentType PaymentType) throws DAOException;

	public PaymentType findById(String id) throws DAOException;

	public PaymentType findByName(String name) throws DAOException, NoResultException;

	public List<PaymentType> findAll() throws DAOException;
}
