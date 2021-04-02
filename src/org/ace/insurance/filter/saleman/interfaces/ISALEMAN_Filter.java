package org.ace.insurance.filter.saleman.interfaces;

import java.util.List;

import org.ace.insurance.filter.cirteria.CRIA001;
import org.ace.insurance.filter.saleman.SAMN001;

public interface ISALEMAN_Filter {
	public List<SAMN001> find(CRIA001 item, String value);

	public List<SAMN001> find(int maxResult);
}
