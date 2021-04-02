package org.ace.insurance.web.manage.enquires;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.ace.insurance.common.interfaces.IPolicy;
import org.primefaces.model.SelectableDataModel;

public class PaymentOrderDataModel extends ListDataModel<IPolicy> implements SelectableDataModel<IPolicy> {
	public PaymentOrderDataModel() {
	}

	public PaymentOrderDataModel(List<IPolicy> data) {
		super(data);
	}

	@Override
	public IPolicy getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data
		List<IPolicy> list = (List<IPolicy>) getWrappedData();
		for (IPolicy dto : list) {
			if (dto.getId().equals(rowKey)) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(IPolicy dto) {
		return dto.getId();
	}
}
