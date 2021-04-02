package org.ace.insurance.system.common.coinsuranceRatio.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.coinsuranceRatio.CoinsuranceRatio;

public interface ICoinsuranceRatioDAO {

	public List<CoinsuranceRatio> findCoinsuranceRatioListByProductGroupId(String productGroupId);

	public void addNewCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio);

	public void updateCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio);

	public void updateEndateByCoInRatioId(CoinsuranceRatio coInRatio);

	public CoinsuranceRatio findCoinsuranceRatio(String productGroupId, Date date);

}
