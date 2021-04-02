package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "RelationshipDialogActionBean")
@ViewScoped
public class RelationshipDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	private List<RelationShip> relationshipList;

	@PostConstruct
	public void init() {
		relationshipList = relationShipService.findAllRelationShip();
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public void selectRelationship(RelationShip relationship) {
		RequestContext.getCurrentInstance().closeDialog(relationship);
	}
}
