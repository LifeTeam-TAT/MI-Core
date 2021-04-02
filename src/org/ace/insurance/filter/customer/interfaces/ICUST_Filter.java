package org.ace.insurance.filter.customer.interfaces;

import java.util.List;

import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.filter.customer.CUST001;

public interface ICUST_Filter {
	public List<CUST001> find(CRIA001 item, String value);
	// used for initial popup
	public List<CUST001> find(int maxResult);
}
