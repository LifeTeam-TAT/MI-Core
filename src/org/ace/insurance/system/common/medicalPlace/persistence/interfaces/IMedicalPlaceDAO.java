package org.ace.insurance.system.common.medicalPlace.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.medicalPlace.MedicalPlace;
import org.ace.java.component.persistence.exception.DAOException;

/***************************************************************************************
 * @author Kyaw Myat Htut
 * @Date 2014-7-25
 * @Version 1.0
 * @Purpose This interface serves as the Database Access Layer to manipulate the
 *          <code>MedicalPlace</code> object.
 * 
 * 
 ***************************************************************************************/
public interface IMedicalPlaceDAO {
	/**
	 * @param {@link MedicalPlace} instance
	 * @return {@link MedicalPlace} instance
	 * @Purpose insert MedicalPlace
	 */
	public void insert(MedicalPlace medicalPlace) throws DAOException;

	/**
	 * @param {@link MedicalPlace} instance
	 * @throws DAOException
	 * @Purpose update MedicalPlace
	 */
	public void update(MedicalPlace medicalPlace) throws DAOException;

	/**
	 * @param {@link MedicalPlace} instance
	 * @throws DAOException
	 * @Purpose delete MedicalPlace
	 */
	public void delete(MedicalPlace medicalPlace) throws DAOException;

	/**
	 * @param String
	 * @throws DAOException
	 * @Purpose findById MedicalPlace
	 */
	public MedicalPlace findById(String id) throws DAOException;

	/**
	 * @throws DAOException
	 * @Purpose findAll MedicalPlace
	 */
	public List<MedicalPlace> findAll() throws DAOException;

	public List<MedicalPlace> findByCriteria(String criteria) throws DAOException;
}
