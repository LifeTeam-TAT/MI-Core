package org.ace.insurance.product.service.interfaces;

import java.util.List;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.PROGRP003;
import org.ace.insurance.product.ProductGroup;
import org.ace.java.component.SystemException;

public interface IProductGroupService {

	public void addNewProductGroup(ProductGroup productGroup) throws SystemException;

	public void updateProductGroup(ProductGroup productGroup) throws SystemException;

	public void deleteProductGroup(ProductGroup productGroup) throws SystemException;

	public List<ProductGroup> findAllProductGroup() throws SystemException;

	public List<PROGRP002> findAllByGroupType(ProductGroupType groupType) throws SystemException;

	public ProductGroup findProductGroupById(String param) throws SystemException;

	public List<PROGRP003> findAll_PROGRP003() throws SystemException;

}
