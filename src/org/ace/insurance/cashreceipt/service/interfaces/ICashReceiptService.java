package org.ace.insurance.cashreceipt.service.interfaces;

import java.util.List;

import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.cashreceipt.LifeCashReceiptListReportDTO;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;

public interface ICashReceiptService {

	public List<LifeCashReceiptListReportDTO> confirmLifeProposalsForCashReceipt(List<CashReceiptDTO> cashReceiptDTOList, ReferenceType type, PaymentDTO payment,
			User responsiblePerson, User currentUser, String remark) throws SystemException;

	public List<CashReceiptDTO> findConfirmationList(CashReceiptCriteria criteria, User user) throws SystemException;
}
