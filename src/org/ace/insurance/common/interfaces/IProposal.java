package org.ace.insurance.common.interfaces;

import java.util.Date;

import org.ace.insurance.system.common.currency.Currency;

public interface IProposal {

	public String getId();

	public Currency getCurrency();

	public String getUserType();

	public Date getStartDate();

	public Date getEndDate();

}
