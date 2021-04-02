package org.ace.insurance.product.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.PROGRP003;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.persistence.interfaces.IProductGroupDAO;
import org.ace.insurance.product.service.interfaces.IProductGroupService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProductGroupService")
public class ProductGroupService extends BaseService implements IProductGroupService {
	@Resource(name = "ProductGroupDAO")
	private IProductGroupDAO productGroupDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductGroup(ProductGroup productGroup) {
		try {
			productGroupDAO.insert(productGroup);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Product", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductGroup(ProductGroup productGroup) {
		try {
			productGroupDAO.update(productGroup);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a updateProductGroup", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductGroup(ProductGroup productGroup) {
		try {
			productGroupDAO.delete(productGroup);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a ProductGroup", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProductGroup> findAllProductGroup() {
		List<ProductGroup> result = null;
		try {
			result = productGroupDAO.findAllProductGroup();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of product)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PROGRP002> findAllByGroupType(ProductGroupType groupType) throws SystemException {
		List<PROGRP002> result = null;
		try {
			result = productGroupDAO.findAllByGroupType(groupType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of product)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ProductGroup findProductGroupById(String id) throws SystemException {
		ProductGroup result = null;
		try {
			result = productGroupDAO.findProductGroupById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Product Group (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PROGRP003> findAll_PROGRP003() throws SystemException {
		List<PROGRP003> result = null;
		try {
			result = productGroupDAO.findAll_PROGRP003();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of product)", e);
		}
		return result != null ? result : Collections.emptyList();
	}

}
