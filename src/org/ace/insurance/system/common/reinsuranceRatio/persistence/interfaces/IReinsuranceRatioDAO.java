package org.ace.insurance.system.common.reinsuranceRatio.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRatio;
import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRationDTO;

public interface IReinsuranceRatioDAO {

	void addNewReinsuranceRatio(ReinsuranceRatio reinsuranceRatio);

	void updateReinsuranceRatio(ReinsuranceRatio reinsuranceRatio);

	List<ReinsuranceRatio> findReinsuranceRatioListByProductGroupId(String productGroupId);

	void updateEndateByReInRatioId(ReinsuranceRatio reinsuranceRatio);

	List<ReinsuranceRationDTO> findReinsuranceDetailRatioList(String productGroup, double sumInsured);

}
