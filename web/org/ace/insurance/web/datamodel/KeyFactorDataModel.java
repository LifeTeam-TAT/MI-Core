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

import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.primefaces.model.SelectableDataModel;

public class KeyFactorDataModel extends ListDataModel<KeyFactor> implements SelectableDataModel<KeyFactor> {

	public KeyFactorDataModel() {
	}

	public KeyFactorDataModel(List<KeyFactor> data) {
		super(data);
	}

	@Override
	public KeyFactor getRowData(String rowKey) {

		List<KeyFactor> keyFactors = (List<KeyFactor>) getWrappedData();

		for (KeyFactor keyFactor : keyFactors) {
			if (keyFactor.getId().equals(rowKey))
				return keyFactor;
		}

		return null;
	}

	@Override
	public Object getRowKey(KeyFactor keyFactor) {
		return keyFactor.getId();
	}
}
