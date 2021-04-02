package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.salesPoints.service.interfaces.ISalesPointsService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageSalesPointsActionBean")
public class ManageSalesPointsActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{SalesPointsService}")
	private ISalesPointsService salesPointsService;

	public void setSalesPointsService(ISalesPointsService salesPointsService) {
		this.salesPointsService = salesPointsService;
	}

	private User user;
	private SalesPoints salesPoints;
	private String oldsalesPointsName;
	private List<SalesPoints> salesPointsList;
	private boolean createNew;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		createNewSalesPoints();
		loadSalesPoints();
	}

	public void createNewSalesPoints() {
		createNew = true;
		salesPoints = new SalesPoints();
	}

	public void loadSalesPoints() {
		salesPointsList = salesPointsService.findAllSalesPoints();
	}

	public void prepareUpdateSalesPoints(SalesPoints salesPoints) {
		createNew = false;
		oldsalesPointsName = salesPoints.getName();
		this.salesPoints = salesPoints;

	}

	public void addNewSalesPoints() {
		try {
			SalesPoints result = salesPointsService.findSalesPointsByName(salesPoints.getName());
			if (result == null) {
				salesPointsService.addNewSalesPoints(salesPoints);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, salesPoints.getName());
			} else {
				addWranningMessage(null, MessageId.ALREADY_INSERT, salesPoints.getName());
			}
			createNewSalesPoints();
			loadSalesPoints();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateSalesPoints() {
		try {
			salesPointsService.updateSalesPoints(salesPoints);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, salesPoints.getName());
			createNewSalesPoints();
			loadSalesPoints();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteSalesPoints(SalesPoints salesPoints) {
		try {
			salesPointsService.deleteSalesPoints(salesPoints);
			salesPointsList.remove(salesPoints);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, salesPoints.getName());
			createNewSalesPoints();

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}
	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		salesPoints.setTownship(township);
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	public List<SalesPoints> getSalesPointsList() {
		return salesPointsList;
	}

	public void setSalesPointsList(List<SalesPoints> salesPointsList) {
		this.salesPointsList = salesPointsList;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public String getOldsalesPointsName() {
		return oldsalesPointsName;
	}

	public void setOldsalesPointsName(String oldsalesPointsName) {
		this.oldsalesPointsName = oldsalesPointsName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
