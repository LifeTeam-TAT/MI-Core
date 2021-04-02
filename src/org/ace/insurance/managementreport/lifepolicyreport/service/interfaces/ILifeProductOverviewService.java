package org.ace.insurance.managementreport.lifepolicyreport.service.interfaces;

import org.ace.insurance.managementreport.lifepolicyreport.LifeProductOverview;

public interface ILifeProductOverviewService {
	public LifeProductOverview findLifePolicyByTownship();
	public LifeProductOverview findLifePolicyByProductType();
	public LifeProductOverview findLifePolicyByGender();
	public LifeProductOverview findLifePolicyByMonth(int year);
	public LifeProductOverview findLifePolicyByPaymentType();
	public LifeProductOverview findLifePolicyByChannel();
	public LifeProductOverview findLifePolicyByAge();
	public LifeProductOverview findLifePolicyBySIAge();

}
