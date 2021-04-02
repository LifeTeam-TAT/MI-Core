package org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces;

import java.util.List;

import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuredProductGroup;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * This interface serves as the DAO to manipulate the Co-insurance Company
 * object.
 * 
 * @author ACN
 * @version 1.0.0
 * @Date 2013/05/07
 */
public interface ICoinsuranceCompanyDAO {
	/**
	 * This method is used to add a new Co-insurance Company object to the
	 * database.
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the newly created Co-insurance Company object
	 *            which is to be persisted.
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public void insert(CoinsuranceCompany coinsuranceCompany) throws DAOException;

	/**
	 * This method is used to update the existing Co-insurance Company object in
	 * the database.
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the exiting Co-insurance Company object with
	 *            the updated data amended.
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public void update(CoinsuranceCompany coinsuranceCompany) throws DAOException;

	/**
	 * This method is used to remove the existing Co-insurance Company object in
	 * the database.
	 * 
	 * @param coinsuranceCompany
	 *            An instance of the exiting Co-insurance Company object which
	 *            is to be removed from the database.
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public void delete(CoinsuranceCompany coinsuranceCompany) throws DAOException;

	/**
	 * This method is used to retrieve by the given <code>ID</code> the existing
	 * Co-insurance Company object from the database.
	 * 
	 * @param id
	 *            The unique ID of the Co-insurance Company object
	 * @return An instance of the {@link CoinsuranceCompany}
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public CoinsuranceCompany findById(String id) throws DAOException;

	/**
	 * This method is used to retrieve all existing Co-insurance Company objects
	 * from the database.
	 * 
	 * @return A {@link List} of {@link CoinsuranceCompany} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public List<CoinsuranceCompany> findAll() throws DAOException;

	/**
	 * This method is used to retrieve from the DB a list of
	 * {@link CoinsuranceCompany} instances whose Product Groups' IDs matches
	 * the given ID.
	 * 
	 * @param productGroupId
	 *            The unique identification of the product group
	 * @return A {@link List} of {@link CoinsuranceCompany} instances whose
	 *         Product Groups' IDs matches the given ID
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public List<CoinsuranceCompany> findByProductGroupId(String productGroupId) throws DAOException;

	/**
	 * This method is used to retrieve from the DB a list of
	 * {@link CoinsuredProductGroup} instances whose Product Groups' IDs matches
	 * the given ID.
	 * 
	 * @param productGroupId
	 *            The unique identification of the product group
	 * @return A {@link List} of {@link CoinsuranceCompany} instances whose
	 *         Product Groups' IDs matches the given ID
	 * @throws DAOException
	 *             DAOException An exception occurs during the DB operation
	 */
	public List<CoinsuredProductGroup> findCoinsuredProductGroupsByProductGroupId(String productGroupId) throws DAOException;

	/**
	 * This method is used to check whether the percentage is still available
	 * for the selected product group among Co-insurance Company objects in the
	 * database.
	 * 
	 * @param productGroup
	 *            - the product group to be checked for entered percentage value
	 * @param percentage
	 *            - the Co-insurance(OUT) percentage for the product group
	 * @param company
	 *            - the coinsurance compnay for the product group
	 * @return - TRUE or FLASE of checking availability
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public boolean isPercentageAvailable(ProductGroup productGroup, double percentage, CoinsuranceCompany company) throws DAOException;

	public List<CoinsuranceCompany> findByCriteria(String criteria) throws DAOException;
}
