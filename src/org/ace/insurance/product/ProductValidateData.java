package org.ace.insurance.product;

import org.ace.insurance.common.PeriodType;

public class ProductValidateData {

	private Product product;
	private Integer period;
	private PeriodType periodType;
	private Double sumInsured;
	private Integer unit;
	private Integer age;

	public ProductValidateData(Product product, Integer period, PeriodType periodType, Double sumInsured, Integer unit, Integer age) {
		super();
		this.product = product;
		this.period = period;
		this.periodType = periodType;
		this.sumInsured = sumInsured;
		this.unit = unit;
		this.age = age;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public Double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(Double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
