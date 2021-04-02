package org.ace.insurance.system.common.initial.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.initial.Initial;
import org.ace.java.component.persistence.exception.DAOException;

public interface IInitialDAO {
	public void insert(Initial initial) throws DAOException;

	public void update(Initial initial) throws DAOException;

	public void delete(Initial initial) throws DAOException;

	public Initial findById(String id) throws DAOException;

	public List<Initial> findAll() throws DAOException;
}
