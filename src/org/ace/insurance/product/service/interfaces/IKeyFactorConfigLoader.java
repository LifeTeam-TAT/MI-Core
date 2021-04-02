package org.ace.insurance.product.service.interfaces;

import java.util.List;

import org.ace.insurance.product.KeyFactorConfig;
import org.ace.insurance.product.ReferenceItem;


public interface IKeyFactorConfigLoader {
	public KeyFactorConfig getKeyFactorConfig(String entityName);
	public List<ReferenceItem> getReferenceItemList();
}
