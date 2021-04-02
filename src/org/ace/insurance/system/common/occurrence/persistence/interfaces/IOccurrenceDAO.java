package org.ace.insurance.system.common.occurrence.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.java.component.persistence.exception.DAOException;

public interface IOccurrenceDAO {
	public void insert(Occurrence occurrence) throws DAOException;

	public void update(Occurrence occurrence) throws DAOException;

	public void delete(Occurrence occurrence) throws DAOException;

	public Occurrence findById(String id) throws DAOException;

	public List<Occurrence> findAll() throws DAOException;

	public List<Occurrence> findByCriteria(String criteria) throws DAOException;

	public Occurrence findByFromCityToCity(Occurrence occurrence) throws DAOException;
}
