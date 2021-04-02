/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.paymenttype.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.paymenttype.PaymentType;

public interface IPaymentTypeService {
	public void addNewPaymentType(PaymentType PaymentType);

	public void updatePaymentType(PaymentType PaymentType);

	public void deletePaymentType(PaymentType PaymentType);

	public PaymentType findPaymentTypeById(String id);

	public List<PaymentType> findAllPaymentType();
}
