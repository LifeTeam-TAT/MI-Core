package org.ace.insurance.system.common.workshop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.WORKSHOP)
@TableGenerator(name = "WORKSHOP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "WORKSHOP_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "WorkShop.findAll", query = "SELECT w FROM WorkShop w "),
		@NamedQuery(name = "WorkShop.findById", query = "SELECT w FROM WorkShop w WHERE w.id = :id") })
@EntityListeners(IDInterceptor.class)
public class WorkShop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WORKSHOP_GEN")
	private String id;

	@Column(name = "NAME")
	private String name;
	@Column(name = "CODE_NO")
	private String codeNo;

	@Embedded
	private WorkShopAddress address;

	@Embedded
	private WorkShopContentInfo contentInfo;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public WorkShop() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkShopAddress getAddress() {
		if (this.address == null) {
			this.address = new WorkShopAddress();
		}
		return address;
	}

	public void setAddress(WorkShopAddress address) {
		this.address = address;
	}

	public WorkShopContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new WorkShopContentInfo();
		}
		return contentInfo;
	}

	public void setContentInfo(WorkShopContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFullAddress() {
		String fullAddress = "";
		if (address != null) {
			String townShip = address.getTownship() == null ? "" : address.getTownship().getFullTownShip();
			fullAddress = address.getAddress() + ", " + townShip;
		}
		return fullAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((codeNo == null) ? 0 : codeNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkShop other = (WorkShop) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (codeNo == null) {
			if (other.codeNo != null)
				return false;
		} else if (!codeNo.equals(other.codeNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
