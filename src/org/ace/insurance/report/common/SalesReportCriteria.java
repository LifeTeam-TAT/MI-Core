package org.ace.insurance.report.common;

import java.util.Date;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;

public class SalesReportCriteria {
	public Agent agent;
	public PolicyReferenceType referenceType;
	public Date startDate;
	public Date endDate;
	private Product product;

	public SalesReportCriteria() {

	}

	public SalesReportCriteria(Agent agent, PolicyReferenceType referenceType, Date startDate, Date endDate, Product product) {
		this.agent = agent;
		this.referenceType = referenceType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

}