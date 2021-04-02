/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.hospital.service.interfaces.IHospitalService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageHospitalActionBean")
public class ManageHospitalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{HospitalService}")
	private IHospitalService hospitalService;

	public void setHospitalService(IHospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	private Hospital hospital;
	private boolean createNew;
	private String criteria;
	private List<Hospital> coutryList;

	@PostConstruct
	public void init() {
		reset();
	}

	public void reset() {
		createNewHospital();
		loadHospital();
	}

	public void loadHospital() {
		coutryList = hospitalService.findAllHospital();
	}

	public void createNewHospital() {
		createNew = true;
		hospital = new Hospital();
		criteria = "";
	}

	public void prepareUpdateHospital(Hospital hospital) {
		createNew = false;
		this.hospital = hospital;
	}

	public String addNewHospital() {
		try {
			hospitalService.addNewHospital(hospital);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, hospital.getName());
			createNewHospital();
			loadHospital();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return "";
	}

	public void updateHospital() {
		try {
			hospitalService.updateHospital(hospital);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, hospital.getName());
			createNewHospital();
			loadHospital();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteHospital() {
		try {
			hospitalService.deleteHospital(hospital);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, hospital.getName());
			createNewHospital();
			loadHospital();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Hospital> getHospitalList() {
		return coutryList;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		hospital.getAddress().setTownship(township);
	}
}
