package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;

public interface IPaymentDelegateService {
	public void payment(List<PaymentDTO> paymentDTOList, PolicyReferenceType referenceType, User responsiblePerson, String remark, Branch branch) throws SystemException;

	public void billCollectionPayment(Payment payment, Branch branch);
}
