package org.ace.insurance.system.common.port.service.interfaces;

import java.util.List;

import org.ace.insurance.common.PortCriteria;
import org.ace.insurance.system.common.port.Port;

public interface IPortService {
	public void addNewPort(Port port);

	public void updatePort(Port port);

	public void deletePort(Port port);

	public Port findPortById(String id);

	public List<Port> findAllPort();

	public List<Port> findByCriteria(String criteria);

	public List<Port> findPortByCriteria(PortCriteria criteria, int max);

}
