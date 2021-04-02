package org.ace.insurance.filter.bankCustomer.interfaces;

import java.util.List;

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.filter.cirteria.BANKCUST001;

public interface IBANKCUSTOMER_Filter {
	public List<BNK001> find(BANKCUST001 item, String value);
}
