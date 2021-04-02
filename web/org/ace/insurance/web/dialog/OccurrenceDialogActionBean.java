package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.occurrence.Occurrence;
import org.ace.insurance.system.common.occurrence.service.interfaces.IOccurrenceService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "OccurrenceDialogActionBean")
@ViewScoped
public class OccurrenceDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Occurrence> occurrenceList;
	private String criteria;

	@ManagedProperty(value = "#{OccurrenceService}")
	private IOccurrenceService occurrenceService;

	public void setOccurrenceService(IOccurrenceService occurrenceService) {
		this.occurrenceService = occurrenceService;
	}

	@PostConstruct
	public void init() {
		occurrenceList = occurrenceService.findAllOccurrence();
	}

	public List<Occurrence> getOccurrenceList() {
		return occurrenceList;
	}

	public void selectOccurrence(Occurrence occurrence) {
		RequestContext.getCurrentInstance().closeDialog(occurrence);
	}

	public void search() {
		occurrenceList = occurrenceService.findByCriteria(criteria);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void setOccurrenceList(List<Occurrence> occurrenceList) {
		this.occurrenceList = occurrenceList;
	}

}
