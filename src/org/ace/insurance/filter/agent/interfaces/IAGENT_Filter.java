package org.ace.insurance.filter.agent.interfaces;

import java.util.List;

import org.ace.insurance.filter.agent.AGNT001;
import org.ace.insurance.filter.cirteria.CRIA001;

public interface IAGENT_Filter {
	public List<AGNT001> find(CRIA001 item, String value);
	public List<AGNT001> find(int maxResult);
}
