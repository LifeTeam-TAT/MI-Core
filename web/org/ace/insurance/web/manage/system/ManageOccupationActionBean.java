/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ViewScoped
@ManagedBean(name = "ManageOccupationActionBean")
public class ManageOccupationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OccupationService}")
	private IOccupationService occupationService;

	public void setOccupationService(IOccupationService occupationService) {
		this.occupationService = occupationService;
	}

	private Occupation occupation;
	private boolean createNew;
	private List<SelectItem> insuranceTypeSelectItemlist;
	private InsuranceType insuranceType;
	private String criteria;
	private List<Occupation> occupationList;
	
	public List<SelectItem> getInsuranceTypeSelectItemlist() {
		return insuranceTypeSelectItemlist;
	}

	public void setInsuranceTypeSelectItemlist(List<SelectItem> insuranceTypeSelectItemlist) {
		this.insuranceTypeSelectItemlist = insuranceTypeSelectItemlist;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	@PostConstruct
	public void init() {
		createNewOccupation();

		insuranceTypeSelectItemlist = new ArrayList<SelectItem>();
		InsuranceType inType[] = insuranceType.values();
		List<InsuranceType> insuranceTypes = Arrays.asList(inType);
		for (InsuranceType in : insuranceTypes) {
			insuranceTypeSelectItemlist.add(new SelectItem(in, in.getLabel()));
		}
		loadOccupation();
	}

	public void loadOccupation() {
		occupationList = occupationService.findAllOccupation();
	}

	public void createNewOccupation() {
		createNew = true;
		occupation = new Occupation();
	}

	public void prepareUpdateOccupation(Occupation occupation) {
		createNew = false;
		this.occupation = occupation;
	}

	public void addNewOccupation() {
		try {
//			occupationList.forEach(occupation -> 
//				occupationService.addNewOccupation(occupation)
//			);
			occupationService.addNewOccupation(occupation);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, occupation.getName());
			createNewOccupation();
			loadOccupation();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateOccupation() {
		try {
			occupationService.updateOccupation(occupation);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, occupation.getName());
			createNewOccupation();
			loadOccupation();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteOccupation() {
		try {
			occupationService.deleteOccupation(occupation);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, occupation.getName());
			createNewOccupation();
			loadOccupation();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Occupation> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<Occupation> occupationList) {
		this.occupationList = occupationList;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}
