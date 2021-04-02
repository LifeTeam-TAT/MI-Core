/***************************************************************************************
 * @author HS
 * @Date 2019-01-22
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

import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.township.TSP002;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageTownshipActionBean")
public class ManageTownshipActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	private Township township;
	private boolean createNew;
	private List<TSP002> townshipList;

	@PostConstruct
	public void init() {
		createNewTownship();
		loadTownship();
	}

	public void loadTownship() {
		townshipList = townshipService.findAll_TSP002();
	}

	public void createNewTownship() {
		createNew = true;
		township = new Township();
	}

	public void prepareUpdateTownship(TSP002 tsp002) {
		createNew = false;
		this.township = townshipService.findTownshipById(tsp002.getId());
	}

	public void addNewTownship() {
		try {
			townshipService.addNewTownship(township);
			townshipList.add(new TSP002(township));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, township.getName());
			createNewTownship();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateTownship() {
		try {
			townshipService.updateTownship(township);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, township.getName());
			createNewTownship();
			loadTownship();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteTownship(TSP002 tsp002) {
		try {
			township = townshipService.findTownshipById(tsp002.getId());
			townshipService.deleteTownship(township);
			townshipList.remove(tsp002);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, township.getName());
			createNewTownship();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<TSP002> getTownshipList() {
		return townshipList;
	}

	public Township getTownship() {
		return township;
	}

	public void returnDistrict(SelectEvent event) {
		District district = (District) event.getObject();
		township.setDistrict(district);
	}

}
