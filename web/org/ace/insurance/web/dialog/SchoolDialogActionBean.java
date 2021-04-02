package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.school.SCH001;
import org.ace.insurance.filter.school.SchoolFilterCriteria;
import org.ace.insurance.filter.school.interfaces.ISchoolFilter;
import org.ace.insurance.system.common.province.Province;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.school.School;
import org.ace.insurance.system.common.school.service.interfaces.ISchoolService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "SchoolDialogActionBean")
@ViewScoped
public class SchoolDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{SchoolService}")
	private ISchoolService schoolService;

	public void setSchoolService(ISchoolService schoolService) {
		this.schoolService = schoolService;
	}

	@ManagedProperty(value = "#{SchoolFilter}")
	private ISchoolFilter filter;

	public void setFilter(ISchoolFilter filter) {
		this.filter = filter;
	}

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	private SchoolFilterCriteria criteria;
	private List<SCH001> schoolList;
	private List<Province> provinceList = new ArrayList<Province>();
	private List<Township> townshipList = new ArrayList<Township>();

	@PostConstruct
	public void init() {
		resetCriteria();
	}

	public void search() {
		schoolList = filter.find(criteria);
	}

	public void resetCriteria() {
		criteria = new SchoolFilterCriteria();
		schoolList = filter.find(criteria);
		provinceList = provinceService.findAllProvince();
		townshipList = new ArrayList<Township>();
	}

	public void selectSchool(SCH001 school) {
		School sch = schoolService.findBySchoolId(school.getId());
		PrimeFaces.current().dialog().closeDynamic(sch);
	}

	public void changeStateCodeList() {
		if (criteria.getProvince() != null) {
			townshipList = townshipService.findTownshipByProvince(criteria.getProvince());
		} else {
			townshipList = new ArrayList<Township>();
		}

	}

	public SchoolFilterCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SchoolFilterCriteria criteria) {
		this.criteria = criteria;
	}

	public List<SCH001> getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(List<SCH001> schoolList) {
		this.schoolList = schoolList;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public List<Township> getTownshipList() {
		return townshipList;
	}

}
