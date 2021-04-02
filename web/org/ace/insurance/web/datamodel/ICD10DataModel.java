package org.ace.insurance.web.datamodel;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.ace.insurance.system.common.icd10.ICD10;
import org.primefaces.model.SelectableDataModel;

public class ICD10DataModel extends ListDataModel<ICD10> implements SelectableDataModel<ICD10> {
	
	public ICD10DataModel() {
	}

	public ICD10DataModel(List<ICD10> data) {
		super(data);
	}

	@Override
	public ICD10 getRowData(String rowKey) {
		List<ICD10> icd10s = (List<ICD10>) getWrappedData();

		for (ICD10 icd : icd10s) {
			if (icd.getId().equals(rowKey))
				return icd;
		}

		return null;
	}

	@Override
	public Object getRowKey(ICD10 icd) {
		return icd.getId();
	}
}
