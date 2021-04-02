package org.ace.insurance.medical.productprocess;

public class ProductProcessCriteriaDTO {
	private StudentAgeType studentAgeType;
	private int age;
	private double sumInsured;

	public StudentAgeType getStudentAgeType() {
		return studentAgeType;
	}

	public void setStudentAgeType(StudentAgeType studentAgeType) {
		this.studentAgeType = studentAgeType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

}
