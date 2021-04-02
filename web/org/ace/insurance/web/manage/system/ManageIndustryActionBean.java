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

import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.industry.service.interfaces.IIndustryService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageIndustryActionBean")
public class ManageIndustryActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{IndustryService}")
	private IIndustryService industryService;

	public void setIndustryService(IIndustryService industryService) {
		this.industryService = industryService;
	}

	private boolean createNew;
	private Industry industry;
	private String criteria;
	private List<Industry> industrieList;

	@PostConstruct
	public void init() {
		createNewIndustry();
		loadIndustry();
	}


	public void createNewIndustry() {
		createNew = true;
		industry = new Industry();
	}

	public void prepareUpdateIndustry(Industry industry) {
		createNew = false;
		this.industry = industry;
	}

	public void addNewIndustry() {
		try {
			industryService.addNewIndustry(industry);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, industry.getName());
			createNewIndustry();
			loadIndustry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateIndustry() {
		try {
			industryService.updateIndustry(industry);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, industry.getName());
			createNewIndustry();
			loadIndustry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteIndustry() {
		try {
			industryService.deleteIndustry(industry);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, industry.getName());
			createNewIndustry();
			loadIndustry();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void loadIndustry() {
		industrieList = industryService.findAllIndustry();
	}

	public List<Industry> getIndustrieList() {
		return industrieList;
	}

	public void setIndustrieList(List<Industry> industrieList) {
		this.industrieList = industrieList;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}
