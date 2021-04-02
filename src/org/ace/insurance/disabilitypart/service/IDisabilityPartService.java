package org.ace.insurance.disabilitypart.service;

import java.util.List;

import org.ace.insurance.disabilitypart.DisabilityPart;

public interface IDisabilityPartService {	
	
public void addNewDisabilityPart(DisabilityPart disabilitypart);

public void deleteDisabilityPart(DisabilityPart disabilitypart);

public void updateDisabilityPart(DisabilityPart disabilitypart);

public DisabilityPart findById(String disabilitypartId);;

public List<DisabilityPart> findAllDisabilityPart();

}
