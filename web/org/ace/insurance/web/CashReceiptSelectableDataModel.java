package org.ace.insurance.web;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.primefaces.model.SelectableDataModel;

public class CashReceiptSelectableDataModel extends ListDataModel<CashReceiptDTO> implements SelectableDataModel<CashReceiptDTO> {

	public CashReceiptSelectableDataModel() {

	}

	public CashReceiptSelectableDataModel(List<CashReceiptDTO> datasource) {
		super(datasource);
	}

	@Override
	public CashReceiptDTO getRowData(String rowKey) {

		List<CashReceiptDTO> datasource = (List<CashReceiptDTO>) getWrappedData();

		for (CashReceiptDTO temp : datasource) {
			if (temp.getId().equals(rowKey))
				return temp;
		}
		return null;
	}

	@Override
	public Object getRowKey(CashReceiptDTO temp) {
		return temp.getId();
	}
}
