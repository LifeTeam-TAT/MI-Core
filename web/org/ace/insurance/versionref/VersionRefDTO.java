package org.ace.insurance.versionref;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VersionRefDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String entityId;
	private String entityName;	
	private int versionNo;
	private String operation;	
	private String group;
	
	public VersionRefDTO() {}
	
	public VersionRefDTO(String entityId, String entityName, int versionNo,
			String operation, String group) {
		super();
		this.entityId = entityId;
		this.entityName = entityName;
		this.versionNo = versionNo;
		this.operation = operation;
		this.group = group;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}	
}
