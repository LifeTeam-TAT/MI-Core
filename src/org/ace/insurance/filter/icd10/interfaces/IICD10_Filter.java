package org.ace.insurance.filter.icd10.interfaces;

import java.util.List;

import org.ace.insurance.filter.cirteria.CRIAICD10;
import org.ace.insurance.system.common.icd10.ICD10;


public interface IICD10_Filter {


	public List<ICD10> find(CRIAICD10 item, String value);
	public List<ICD10> find(int maxResult);

}
