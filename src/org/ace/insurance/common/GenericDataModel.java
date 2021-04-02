package org.ace.insurance.common;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.ace.insurance.common.interfaces.IDataModel;
import org.primefaces.model.SelectableDataModel;

public class GenericDataModel<T extends IDataModel> extends ListDataModel<T> implements SelectableDataModel<T> {

	public GenericDataModel() {
	}

	public GenericDataModel(List<T> data) {
		super(data);
	}

	@Override
	public T getRowData(String rowKey) {
		List<T> modelList = (List<T>) getWrappedData();
		for (T dataModel : modelList) {
			if (dataModel.getId().equals(rowKey))
				return dataModel;
		}

		return null;
	}

	@Override
	public Object getRowKey(T dataModel) {
		return dataModel.getId();
	}
}
