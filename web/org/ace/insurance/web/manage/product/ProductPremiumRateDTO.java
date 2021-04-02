package org.ace.insurance.web.manage.product;

import java.util.List;

import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;

public class ProductPremiumRateDTO {
	private String tempId;
	private double premiumRate;

	private List<ProductPremiumRateKeyFactorDTO> premiumRateKeyFactorDTO;
	private Product product;

	public ProductPremiumRateDTO() {
		tempId = System.currentTimeMillis() + "";
	}

	public ProductPremiumRateDTO(ProductPremiumRate productPremiumRate) {
		tempId = System.currentTimeMillis() + "";
		this.premiumRate = productPremiumRate.getPremiumRate();
		this.product = productPremiumRate.getProduct();
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public List<ProductPremiumRateKeyFactorDTO> getPremiumRateKeyFactorDTO() {
		return premiumRateKeyFactorDTO;
	}

	public void setPremiumRateKeyFactorDTO(List<ProductPremiumRateKeyFactorDTO> premiumRateKeyFactorDTO) {
		this.premiumRateKeyFactorDTO = premiumRateKeyFactorDTO;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
