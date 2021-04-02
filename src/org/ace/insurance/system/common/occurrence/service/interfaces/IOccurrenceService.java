package org.ace.insurance.system.common.occurrence.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.occurrence.Occurrence;

public interface IOccurrenceService {
	public void addNewOccurrence(Occurrence occurrence);

	public void updateOccurrence(Occurrence occurrence);

	public void deleteOccurrence(Occurrence occurrence);

	public Occurrence findOccurrenceById(String id);

	public List<Occurrence> findAllOccurrence();

	public List<Occurrence> findByCriteria(String criteria);

	public Occurrence findByFromCityToCity(Occurrence occurrence);
}
