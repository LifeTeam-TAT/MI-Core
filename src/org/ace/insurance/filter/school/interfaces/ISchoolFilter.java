package org.ace.insurance.filter.school.interfaces;

import java.util.List;

import org.ace.insurance.filter.school.SCH001;
import org.ace.insurance.filter.school.SchoolFilterCriteria;

public interface ISchoolFilter {
	public List<SCH001> find(SchoolFilterCriteria criteria);
}
