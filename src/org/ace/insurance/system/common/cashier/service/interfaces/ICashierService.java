/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.cashier.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.workshop.WorkShop;

public interface ICashierService {
	public void addNewCashier(Cashier cashier);

	public void updateCashier(Cashier cashier);

	public void deleteCashier(Cashier cashier);

	public Cashier findCashierById(String id);

	public List<Cashier> findAllCashier();

	public boolean checkExistingCashier(Cashier cashier);

	public List<Cashier> findCasherByWorkShop(WorkShop workShop);
}
