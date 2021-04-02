package org.ace.insurance.productDisabilityPartLink.persistence.interfaces;

import java.util.List;

import org.ace.insurance.productDisabilityPartLink.ProductDisabilityPart;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;

public interface IProductDisabilityPartLinkDAO {

	List<ProductDisabilityPart> findAll();

	void addNewProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	void updateProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	void deleteProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	ProductDisabilityPart findById(String id);

	List<ProductDisabilityRate> findAllDisabilityRateByProduct(String produtId);

}
