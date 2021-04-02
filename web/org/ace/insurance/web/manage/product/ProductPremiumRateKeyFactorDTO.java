package org.ace.insurance.web.manage.product;

import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.common.KeyFactorType;
import org.ace.insurance.product.ProductPremiumRateKeyFactor;
import org.ace.insurance.system.common.keyfactor.KeyFactor;

public class ProductPremiumRateKeyFactorDTO {
	private String value;
	private String referenceName;
	private double from;
	private double to;
	private KeyFactor keyFactor;
	private ProductPremiumRateDTO productPremiumRateDTO;
	private Map<String, String> referenceValueMap = new HashMap<String, String>();

	public ProductPremiumRateKeyFactorDTO() {
	}

	public ProductPremiumRateKeyFactorDTO(ProductPremiumRateKeyFactor productPremiumRateKeyFactor) {
		this.value = productPremiumRateKeyFactor.getValue();
		this.from = productPremiumRateKeyFactor.getFrom();
		this.to = productPremiumRateKeyFactor.getTo();
		this.keyFactor = productPremiumRateKeyFactor.getKeyFactor();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (this.keyFactor.getKeyFactorType().equals(KeyFactorType.REFERENCE)) {
			this.referenceName = referenceValueMap.get(value);
		}
		this.value = value;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public double getFrom() {
		return from;
	}

	public void setFrom(double from) {
		this.from = from;
	}

	public double getTo() {
		return to;
	}

	public void setTo(double to) {
		this.to = to;
	}

	public KeyFactor getKeyFactor() {
		return keyFactor;
	}

	public void setKeyFactor(KeyFactor keyFactor) {
		this.keyFactor = keyFactor;
	}

	public ProductPremiumRateDTO getProductPremiumRateDTO() {
		return productPremiumRateDTO;
	}

	public void setProductPremiumRateDTO(ProductPremiumRateDTO productPremiumRateDTO) {
		this.productPremiumRateDTO = productPremiumRateDTO;
	}

	public void putRefValue(String key, String value) {
		referenceValueMap.put(key, value);
	}
}
