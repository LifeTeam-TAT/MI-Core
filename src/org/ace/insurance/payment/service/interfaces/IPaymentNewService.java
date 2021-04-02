package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.common.interfaces.IProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;

public interface IPaymentNewService {

	public void activatePayment(IProposal proposal, List<Payment> paymentList, Branch branch, boolean isRenewal);

}
