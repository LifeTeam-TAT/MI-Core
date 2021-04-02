package org.ace.insurance.filter.school;

import org.ace.insurance.common.SchoolType;
import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.township.Township;

public class SchoolFilterCriteria {

	public SchoolType schoolType;
	public Province province;
	public Township township;

	public SchoolType getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Township getTownship() {
		return township;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

}
