package org.ace.insurance.product.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.PROGRP003;
import org.ace.insurance.product.ProductGroup;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProductGroupDAO {
	public void insert(ProductGroup productGroup) throws DAOException;

	public void update(ProductGroup productGroup) throws DAOException;

	public void delete(ProductGroup productGroup) throws DAOException;

	public List<ProductGroup> findAllProductGroup() throws DAOException;

	public List<PROGRP002> findAllByGroupType(ProductGroupType groupType) throws DAOException;

	public ProductGroup findProductGroupById(String id) throws DAOException;

	public List<PROGRP003> findAll_PROGRP003() throws DAOException;
}
