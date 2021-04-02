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

import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageRelationShipActionBean")
public class ManageRelationShipActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	private boolean createNew;
	private RelationShip relationShip;
	private List<RelationShip> relationShipList;
	private String criteria;

	@PostConstruct
	public void init() {
		createNewRelationShip();
		loadRelationShipList();
	}

	public void createNewRelationShip() {
		createNew = true;
		relationShip = new RelationShip();
	}

	public void prepareUpdateRelationShip(RelationShip relationShip) {
		createNew = false;
		this.relationShip = relationShip;
	}

	public void addNewRelationShip() {
		try {
			relationShipService.addNewRelationShip(relationShip);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, relationShip.getName());
			createNewRelationShip();
			loadRelationShipList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateRelationShip() {
		try {
			relationShipService.updateRelationShip(relationShip);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, relationShip.getName());
			createNewRelationShip();
			loadRelationShipList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteRelationShip() {
		try {
			relationShipService.deleteRelationShip(relationShip);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, relationShip.getName());
			createNewRelationShip();
			loadRelationShipList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void loadRelationShipList() {
		relationShipList = relationShipService.findAllRelationShip();
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public void setRelationShipList(List<RelationShip> relationShipList) {
		this.relationShipList = relationShipList;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}
