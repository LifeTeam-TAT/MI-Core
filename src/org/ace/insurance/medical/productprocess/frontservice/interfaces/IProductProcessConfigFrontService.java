package org.ace.insurance.medical.productprocess.frontservice.interfaces;

import java.util.List;

import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.web.manage.product.ProductProcessDTO;

public interface IProductProcessConfigFrontService {
	/**
	 * 
	 * @param {@link
	 *            ProductProcess} instance
	 * @Purpose Add new ProductProcess.
	 */
	public void addNewProductProcessConfig(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 *            ProductProcess} instance
	 * @Purpose update ProductProcess data.<br/>
	 * 
	 */
	public void updateProductProcessConfig(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 *            ProductProcess} instance
	 * @Purpose update ProductProcess for delete status.<br/>
	 *          insert ProductProcess History for edit process.<br/>
	 */
	public void deleteProductProcessConfig(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 *            ProductProcess} instance
	 * @Purpose find old configure ProductProcess .<br/>
	 *          delete ProductProcess.<br/>
	 */
	// public void findAndDeleteOldConfPP(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 *            String,String}
	 * @Purpose find configure and deactivate ProductProcess .<br/>
	 * @return {@link List} of {@link ProductProcess} instances
	 */
	public List<ProductProcess> findConfigAndDeactivatePP(String productId, String processId);

	// public void
	// updateProductProcessConfigActivateDeactivate(ProductProcessDTO
	// productProcess);

	public void updateProductProcessConfigActivateDeactivate(ProductProcessDTO productProcess, String prefix, String ppNo, boolean flag);

	public String findGrateQuestionSetNo(String productId, String processId);

}
