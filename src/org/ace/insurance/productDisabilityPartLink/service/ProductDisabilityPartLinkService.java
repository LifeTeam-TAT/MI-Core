package org.ace.insurance.productDisabilityPartLink.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.productDisabilityPartLink.ProductDisabilityPart;
import org.ace.insurance.productDisabilityPartLink.ProductDisabilityRate;
import org.ace.insurance.productDisabilityPartLink.persistence.interfaces.IProductDisabilityPartLinkDAO;
import org.ace.insurance.productDisabilityPartLink.service.interfaces.IProductDisabilityPartLinkService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProductDisabilityPartLinkService")
public class ProductDisabilityPartLinkService extends BaseService implements IProductDisabilityPartLinkService {

	@Resource(name = "ProductDisabilityPartLinkDAO")
	private IProductDisabilityPartLinkDAO productDisabilityPartLinkService;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductDisabilityPart> findAll() {
		List<ProductDisabilityPart> result;
		try {
			result = productDisabilityPartLinkService.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find All Product Disability Part Link", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			productDisabilityPartLinkService.addNewProductDisabilityPart(productDisabilityPartLink);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Product_DisabilityPart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductDisabilityPart updateProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			productDisabilityPartLinkService.updateProductDisabilityPart(productDisabilityPartLink);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a update Product_DisabilityPart", e);
		}
		return productDisabilityPartLink;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductDisabilityPart(ProductDisabilityPart productDisabilityPartLink) {
		try {
			productDisabilityPartLinkService.deleteProductDisabilityPart(productDisabilityPartLink);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a delete Product_DisabilityPart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductDisabilityPart findProductDisbilityById(String id) {
		try {
			return productDisabilityPartLinkService.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find by id)", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductDisabilityRate> findAllDisabilityRateByProduct(String produtId) {
		List<ProductDisabilityRate> result = null;
		try {
			result = productDisabilityPartLinkService.findAllDisabilityRateByProduct(produtId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find All Disability Rate By Product)", e);
		}
		return result;
	}

}
