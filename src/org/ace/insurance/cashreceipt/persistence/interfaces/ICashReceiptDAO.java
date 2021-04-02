package org.ace.insurance.cashreceipt.persistence.interfaces;

import java.util.List;

import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.user.User;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICashReceiptDAO {
	public List<CashReceiptDTO> findFireConfirmationList(CashReceiptCriteria criteria, User user)throws DAOException;
	public List<CashReceiptDTO> findLifeConfirmationList(CashReceiptCriteria criteria, User user)throws DAOException;
	public List<CashReceiptDTO> findMotorConfirmationList(CashReceiptCriteria criteria, User user)throws DAOException;
}
