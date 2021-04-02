/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.primefaces.model.SelectableDataModel;

public class PaymentTypeDataModel extends ListDataModel<PaymentType> implements SelectableDataModel<PaymentType> {

	public PaymentTypeDataModel() {
	}

	public PaymentTypeDataModel(List<PaymentType> data) {
		super(data);
	}

	@Override
	public PaymentType getRowData(String rowKey) {

		List<PaymentType> paymentTypes = (List<PaymentType>) getWrappedData();

		for (PaymentType car : paymentTypes) {
			if (car.getId().equals(rowKey))
				return car;
		}

		return null;
	}

	@Override
	public Object getRowKey(PaymentType car) {
		return car.getId();
	}
}
