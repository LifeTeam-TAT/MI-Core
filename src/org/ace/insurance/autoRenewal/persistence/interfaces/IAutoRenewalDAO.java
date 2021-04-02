package org.ace.insurance.autoRenewal.persistence.interfaces;

import java.util.List;

import org.ace.insurance.autoRenewal.AutoRenewal;
import org.ace.insurance.autoRenewal.AutoRenewalCriteria;
import org.ace.insurance.autoRenewal.AutoRenewalStatus;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAutoRenewalDAO {
	public AutoRenewal insert(AutoRenewal autoRenewal) throws DAOException;

	public void update(AutoRenewal autoRenewal) throws DAOException;

	public void updateStatus(AutoRenewalStatus status, String id) throws DAOException;

	public void updateStatusToDeactivate() throws DAOException;

	public void delete(AutoRenewal autoRenewal) throws DAOException;

	public AutoRenewal findById(String id) throws DAOException;

	public List<AutoRenewal> findAll() throws DAOException;

	public List<AutoRenewal> findAllActiveInstance() throws DAOException;

	public List<AutoRenewal> findByCriteria(AutoRenewalCriteria criteria) throws DAOException;
}
