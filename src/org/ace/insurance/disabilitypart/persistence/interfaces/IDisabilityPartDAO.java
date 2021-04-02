package org.ace.insurance.disabilitypart.persistence.interfaces;

import java.util.List;

import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.java.component.persistence.exception.DAOException;

public interface IDisabilityPartDAO {

	public void insert(DisabilityPart disabilitypart) throws DAOException;

	public void delete(DisabilityPart disabilitypart) throws DAOException;

	public void update(DisabilityPart disabilitypart) throws DAOException;

	public DisabilityPart findById(String disabilitypartId) throws DAOException;

	public List<DisabilityPart> findAllDisabilitypart() throws DAOException;


}
