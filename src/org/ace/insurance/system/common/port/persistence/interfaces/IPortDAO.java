package org.ace.insurance.system.common.port.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.PortCriteria;
import org.ace.insurance.system.common.port.Port;
import org.ace.java.component.persistence.exception.DAOException;

public interface IPortDAO {
	public void insert(Port port) throws DAOException;

	public void update(Port port) throws DAOException;

	public void delete(Port port) throws DAOException;

	public Port findById(String id) throws DAOException;

	public List<Port> findAll() throws DAOException;

	public List<Port> findByCriteria(String criteria) throws DAOException;

	public List<Port> findPortByCriteria(PortCriteria criteria, int max) throws DAOException;

}
