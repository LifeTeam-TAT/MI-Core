package org.ace.insurance.system.common.reinsuranceRatio.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.reinsuranceRatio.ReinsuranceRatio;

public interface IReinsuranceRatioService {

	void addNewReinsuranceRatio(ReinsuranceRatio reinsuranceRatio);

	void updateReinsuranceRatio(ReinsuranceRatio reinsuranceRatio);

	List<ReinsuranceRatio> findReinsuranceRatioListByProductGroupId(String id);

	void updateEndateByReInRatioId(ReinsuranceRatio reinsuranceRatio);

}
