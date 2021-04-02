package org.ace.insurance.afpBankDiscountRate.persistence.interfaces;

import java.util.List;

import org.ace.insurance.afpBankDiscountRate.AFPBankDiscountRate;
import org.ace.insurance.afpBankDiscountRate.AFPR001;

public interface IAFPBankDiscountDAO {

	List<AFPR001> findAFPBankDiscountRateDTOByProductGroupId(String productGroupId);

	void deleteAllAFPBankDiscountRateByProductGroup(String id);

	void insertAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate);

	void updateAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate);

	void deleteAFPBankDiscountRateById(String id);

	AFPBankDiscountRate findAFPBankDiscountRateById(String id);

	double findAFPDiscountRate(String bankId, String productGroupId);

}
