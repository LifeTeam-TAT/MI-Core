package org.ace.insurance.system.common.riskyOccupation.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;

public interface IRiskyOccupationService {

	public void addNewRiskyOccupation(RiskyOccupation riskyOccupation);

	public void updateRiskyOccupation(RiskyOccupation riskyOccupation);

	public void deleteRiskyOccupation(RiskyOccupation riskyOccupation);

	public RiskyOccupation findRiskyOccupationById(String id);
	
	public List<RiskyOccupation> findAllRiskyOccupation();
}
