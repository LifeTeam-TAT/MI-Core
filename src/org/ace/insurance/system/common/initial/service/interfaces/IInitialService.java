package org.ace.insurance.system.common.initial.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.initial.Initial;

public interface IInitialService {
	public void addNewInitial(Initial initial) ;

	public void updateInitial(Initial initial) ;

	public void deleteInitial(Initial initial) ;

	public Initial findInitialById(String id);

	public List<Initial> findAllInitial() ;
}
