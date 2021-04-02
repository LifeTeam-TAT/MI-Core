package org.ace.insurance.afpBankDiscountRate.service.interfaces;

import java.util.List;

import org.ace.insurance.afpBankDiscountRate.AFPBankDiscountRate;
import org.ace.insurance.afpBankDiscountRate.AFPR001;

public interface IAFPBankDiscountRateService {

	public List<AFPR001> findAFPBankDiscountRateDTOByProductGroupId(String id);

	public void deleteAllAFPBankDiscountRateByProductGroup(String id);

	public void addNewAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate);

	public void updateAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate);

	public void deleteAFPBankDiscountRateById(String id);

	public AFPBankDiscountRate findAFPBankDiscountRateById(String id);

	public double findAFPDiscountRate(String bankId, String productGroupId);
}
