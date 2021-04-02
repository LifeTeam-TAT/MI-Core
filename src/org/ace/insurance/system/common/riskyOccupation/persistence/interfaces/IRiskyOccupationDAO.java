package org.ace.insurance.system.common.riskyOccupation.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IRiskyOccupationDAO {
	
	public void insert(RiskyOccupation riskyOccupation) throws DAOException;
	
	public void update(RiskyOccupation riskyOccupation) throws DAOException;
	
	public void delete(RiskyOccupation riskyOccupation) throws DAOException;
	
	public RiskyOccupation findById(String id) throws DAOException;
	
	public List<RiskyOccupation> findAll() throws DAOException;
	

}
