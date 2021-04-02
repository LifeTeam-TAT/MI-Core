package org.ace.insurance.productContent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.productContent.ProductContent;

public interface IProductContentDAO {

	public List<ProductContent> findAll();

	public void addNewProductContent(ProductContent productContent);

	public void updateProductContent(ProductContent productContent);

	public void deleteProductContent(ProductContent productContent);

}
