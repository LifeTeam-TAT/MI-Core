package org.ace.insurance.productContent.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.productContent.ProductContent;
import org.ace.insurance.productContent.persistence.interfaces.IProductContentDAO;
import org.ace.insurance.productContent.service.interfaces.IProductContentService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProductContentService")
public class ProductContentService extends BaseService implements IProductContentService {

	@Resource(name = "ProductContentDAO")
	private IProductContentDAO productContentDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<ProductContent> findAllProductContent() {
		List<ProductContent> result = null;
		try {
			result = productContentDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ProductContent)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewProductContent(ProductContent productContent) {
		try {
			productContentDAO.addNewProductContent(productContent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add ProductContent)", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateProductContent(ProductContent productContent) {
		try {
			productContentDAO.updateProductContent(productContent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update ProductContent)", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteProductContent(ProductContent productContent) {
		try {
			productContentDAO.deleteProductContent(productContent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete ProductContent)", e);
		}
	}

}
