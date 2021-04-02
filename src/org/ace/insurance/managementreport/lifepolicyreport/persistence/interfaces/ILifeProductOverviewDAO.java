package org.ace.insurance.managementreport.lifepolicyreport.persistence.interfaces;

import org.ace.insurance.managementreport.lifepolicyreport.LifeProductOverview;

public interface ILifeProductOverviewDAO {
	public LifeProductOverview findLifePolicyByProductType();
	public LifeProductOverview findLifePolicyByTownship();
	public LifeProductOverview findLifePolicyByGender();
	public LifeProductOverview findLifePolicyByMonth(int year);
	public LifeProductOverview findLifePolicyByPaymentType();
	public LifeProductOverview findLifePolicyByChannel();
	public LifeProductOverview findLifePolicyByAge();
	public LifeProductOverview findLifePolicyBySIAge();
}
