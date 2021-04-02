package org.ace.insurance.filter.life.interfaces;

import java.util.List;

import org.ace.insurance.filter.cirteria.CRIA002;
import org.ace.insurance.filter.life.LPLC001;

public interface ILIFE_Filter {
	public List<LPLC001> find(CRIA002 criteria);
}
