package org.ace.insurance.system.common.packing.service.interefaces;

import java.util.List;

import org.ace.insurance.system.common.packing.Packing;

public interface IPackingService {
	public void addNewPacking(Packing Packing);

	public void updatePacking(Packing Packing);

	public void deletePacking(Packing Packing);

	public Packing findPackingById(String id);

	public List<Packing> findAllPacking();
}
