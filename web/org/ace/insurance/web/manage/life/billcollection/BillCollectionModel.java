package org.ace.insurance.web.manage.life.billcollection;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class BillCollectionModel extends ListDataModel<BillCollectionDTO> implements SelectableDataModel<BillCollectionDTO> {
	public BillCollectionModel() {
	}

	public BillCollectionModel(List<BillCollectionDTO> data) {
		super(data);
	}

	@Override
	public BillCollectionDTO getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data
		List<BillCollectionDTO> list = (List<BillCollectionDTO>) getWrappedData();
		for (BillCollectionDTO dto : list) {
			if (dto.getPolicyId().equals(rowKey)) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(BillCollectionDTO dto) {
		return dto.getPolicyId();
	}
}
