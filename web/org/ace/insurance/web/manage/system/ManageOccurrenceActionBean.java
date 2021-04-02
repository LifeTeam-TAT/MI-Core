/***************************************************************************************
 * @author <<YE YINT PHYO>>
 * @Date 2016-06-18
 * @Version 1.0
 * @Purpose <<For Travel Insurance>>
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

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.city.service.interfaces.ICityService;
import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.occurrence.service.interfaces.IOccurrenceService;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean(name = "ManageOccurrenceActionBean")
public class ManageOccurrenceActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OccurrenceService}")
	private IOccurrenceService occurrenceService;

	public void setOccurrenceService(IOccurrenceService occurrenceService) {
		this.occurrenceService = occurrenceService;
	}

	@ManagedProperty(value = "#{CityService}")
	private ICityService cityService;

	public void setCityService(ICityService cityService) {
		this.cityService = cityService;
	}

	private boolean createNew;
	private Occurrence occurrence;
	private LazyDataModelUtil<Occurrence> lazyModel;
	private List<Occurrence> occurrenceList;

	private String criteria;

	@PostConstruct
	public void init() {
		createNewOccurrence();
		loadOccurrence();
	}

	public void loadOccurrence() {
		occurrenceList = occurrenceService.findAllOccurrence();
		lazyModel = new LazyDataModelUtil<Occurrence>(occurrenceList);
	}

	public void createNewOccurrence() {
		createNew = true;
		occurrence = new Occurrence();
	}

	public void prepareUpdateOccurrence(Occurrence occurrence) {
		createNew = false;
		this.occurrence = occurrence;
	}

	public void reset() {
		createNewOccurrence();
		loadOccurrence();
		criteria = "";
	}

	public void addNewOccurrence() {
		Occurrence occ = occurrenceService.findByFromCityToCity(occurrence);
		if (occ == null) {
			occurrenceService.addNewOccurrence(occurrence);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, occurrence.getDescription());
		} else {
			addWranningMessage(null, MessageId.ALREADY_INSERT, occ.getDescription());
		}
		loadOccurrence();
		createNewOccurrence();
	}

	public void updateOccurrence() {
		Occurrence occ = occurrenceService.findByFromCityToCity(occurrence);
		if (occ == null) {
			occurrenceService.updateOccurrence(occurrence);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, occurrence.getDescription());
		} else {
			addWranningMessage(null, MessageId.ALREADY_INSERT, occ.getDescription());
		}
		loadOccurrence();
		createNewOccurrence();
	}

	public String deleteOccurrence() {
		try {
			occurrenceService.deleteOccurrence(occurrence);
			loadOccurrence();
			addInfoMessage(null, MessageId.DELETE_SUCCESS, occurrence.getDescription());
			createNewOccurrence();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public LazyDataModel<Occurrence> getLazyModel() {
		return lazyModel;
	}

	public Occurrence getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
	}

	public void setOccurrenceList(List<Occurrence> occurrenceList) {
		this.occurrenceList = occurrenceList;
	}

	public void returnFromCity(SelectEvent event) {
		City city = (City) event.getObject();
		occurrence.setFromCity(city);
	}

	public void returnToCity(SelectEvent event) {
		City city = (City) event.getObject();
		occurrence.setToCity(city);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}
