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

import org.ace.insurance.system.common.addon.AddOn;
import org.primefaces.model.SelectableDataModel;

public class AddOnDataModel extends ListDataModel<AddOn> implements SelectableDataModel<AddOn> {

	public AddOnDataModel() {
	}

	public AddOnDataModel(List<AddOn> data) {
		super(data);
	}

	@Override
	public AddOn getRowData(String rowKey) {

		List<AddOn> cars = (List<AddOn>) getWrappedData();

		for (AddOn car : cars) {
			if (car.getId().equals(rowKey))
				return car;
		}

		return null;
	}

	@Override
	public Object getRowKey(AddOn car) {
		return car.getId();
	}
}
