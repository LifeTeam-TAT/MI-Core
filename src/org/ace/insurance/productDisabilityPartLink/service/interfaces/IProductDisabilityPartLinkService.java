package org.ace.insurance.productDisabilityPartLink.service.interfaces;

import java.util.List;

import org.ace.insurance.productDisabilityPartLink.ProductDisabilityPart;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;

public interface IProductDisabilityPartLinkService {

	List<ProductDisabilityPart> findAll();

	void addNewProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	ProductDisabilityPart updateProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	void deleteProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink);

	ProductDisabilityPart findProductDisbilityById(String id);

	List<ProductDisabilityRate> findAllDisabilityRateByProduct(String produtId);

}
