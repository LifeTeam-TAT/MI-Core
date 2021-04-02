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

import org.ace.insurance.product.ProductPremiumRate;
import org.primefaces.model.SelectableDataModel;

public class ProductPremiumRateDataModel extends ListDataModel<ProductPremiumRate> implements SelectableDataModel<ProductPremiumRate> {
	public ProductPremiumRateDataModel() {
	}

	public ProductPremiumRateDataModel(List<ProductPremiumRate> data) {
		super(data);
	}

	@Override
	public ProductPremiumRate getRowData(String rowKey) {
		List<ProductPremiumRate> motorPremiumRateList = (List<ProductPremiumRate>) getWrappedData();
		for (ProductPremiumRate motorPremiumRate : motorPremiumRateList) {
			if (motorPremiumRate.getId().equals(rowKey))
				return motorPremiumRate;
		}
		return null;
	}

	@Override
	public Object getRowKey(ProductPremiumRate motorPremiumRate) {
		return motorPremiumRate.getId();
	}
}
