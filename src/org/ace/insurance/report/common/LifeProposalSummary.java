package org.ace.insurance.report.common;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.LIFEPROPOSALSUMMARY)
@ReadOnly
@SuppressWarnings("unused")
public class LifeProposalSummary implements ISorter {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private Double proposedPremium;
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;
	private String agentId;
	private String saleManId;

	@Override
	public String getRegistrationNo() {
		return id;
	}
}
