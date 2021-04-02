package org.ace.insurance.system.common.coinsurancecompany.service.interfaces;

import java.util.List;

import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuredProductGroup;

/**
 * This interface serves as the service layer to manipulate the Co-insurance
 * Company object.
 * 
 * @author ACN
 * @version 1.0.0
 * @Date 2013/05/07
 */
public interface ICoinsuranceCompanyService {
	/**
	 * This method adds the given {@link CoinsuranceCompany} to the repository;
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the Co-insurance Company object
	 * @return A newly created {@link CoinsuranceCompany} instance with the
	 *         generated unique ID populated
	 */
	public CoinsuranceCompany add(CoinsuranceCompany coinsuranceCompany);

	/**
	 * This method updates the given {@link CoinsuranceCompany} in the
	 * repository;
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the Co-insurance Company object
	 */
	public void update(CoinsuranceCompany coinsuranceCompany);

	/**
	 * This method removes the given {@link CoinsuranceCompany} from the
	 * repository;
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the Co-insurance Company object
	 */
	public void delete(CoinsuranceCompany coinsuranceCompany);

	/**
	 * This method is used to retrieve by the given <code>ID</code> the existing
	 * Co-insurance Company object from the repository .
	 * 
	 * @param id
	 *            The unique ID of the Co-insurance Company object
	 * @return An instance of the {@link CoinsuranceCompany}
	 */
	public CoinsuranceCompany findById(String id);

	/**
	 * This method is used to retrieve all existing Co-insurance Company objects
	 * from the repository.
	 * 
	 * @return A {@link List} of {@link CoinsuranceCompany} instances
	 */
	public List<CoinsuranceCompany> findAll();

	/**
	 * This method is used to retrieve from the DB a list of
	 * {@link CoinsuranceCompany} instances whose Product Groups' IDs matches
	 * the given ID.
	 * 
	 * @param productGroupId
	 *            The unique identification of the product group
	 * @return A {@link List} of {@link CoinsuranceCompany} instances whose
	 *         Product Groups' IDs matches the given ID
	 */
	public List<CoinsuranceCompany> findByProductGroupId(String productGroupId);

	/**
	 * This method is used to retrieve from the DB a list of
	 * {@link CoinsuredProductGroup} instances whose Product Groups' IDs matches
	 * the given ID.
	 * 
	 * @param productGroupId
	 *            The unique identification of the product group
	 * @return A {@link List} of {@link CoinsuranceCompany} instances whose
	 *         Product Groups' IDs matches the given ID
	 */
	public List<CoinsuredProductGroup> findCoinsuredProductGroupsByProductGroupId(String productGroupId);

	/**
	 * This method is used to check whether the percentage is still available
	 * for the selected product group.
	 * 
	 * @param productGroup
	 *            - the product group to be checked for entered percentage value
	 * @param percentage
	 *            - the coinsurance(OUT) percentage for the product group
	 * @param company
	 *            - the coinsurance compnay for the product group
	 * @return - TRUE or FLASE of checking availability
	 */
	public boolean isPercentageAvailable(ProductGroup productGroup, double percentage, CoinsuranceCompany company);

	public List<CoinsuranceCompany> findByCriteria(String criteria);
}
