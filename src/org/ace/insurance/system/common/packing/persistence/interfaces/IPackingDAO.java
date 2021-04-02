package org.ace.insurance.system.common.packing.persistence.interfaces;

import java.util.List;

import javax.persistence.NoResultException;

import org.ace.insurance.system.common.packing.Packing;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPackingDAO {
	public void insert(Packing Packing) throws DAOException;

	public void update(Packing Packing) throws DAOException;

	public void delete(Packing Packing) throws DAOException;

	public Packing findById(String id) throws DAOException;

	public Packing findByName(String name) throws DAOException, NoResultException;

	public List<Packing> findAll() throws DAOException;

}
