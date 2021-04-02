package org.ace.insurance.productContent.service.interfaces;

import java.util.List;

import org.ace.insurance.productContent.ProductContent;

public interface IProductContentService {

	public List<ProductContent> findAllProductContent();

	public void addNewProductContent(ProductContent productContent);

	public void updateProductContent(ProductContent productContent);

	public void deleteProductContent(ProductContent productContent);

}
