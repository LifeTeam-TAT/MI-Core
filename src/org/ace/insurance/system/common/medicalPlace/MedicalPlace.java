package org.ace.insurance.system.common.medicalPlace;

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

import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.ContentInfo;
import org.ace.insurance.common.PermanentAddress;
import org.ace.insurance.common.TableName;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.MEDICALPLACE)
@TableGenerator(name = "MEDICALPLACE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPLACE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MedicalPlace.findAll", query = "SELECT c FROM MedicalPlace c ORDER BY c.name ASC"),
		@NamedQuery(name = "MedicalPlace.findById", query = "SELECT c FROM MedicalPlace c WHERE c.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MedicalPlace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPLACE_GEN")
	private String id;
	private String name;
	private String description;

	@Embedded
	private PermanentAddress address;

	@Embedded
	private ContentInfo contentInfo;
	@Version
	private int version;
	
	@Embedded
	private UserRecorder recorder;

	public MedicalPlace() {

	}

	public MedicalPlace(String name, String description, PermanentAddress address, ContentInfo contentInfo) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.contentInfo = contentInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PermanentAddress getAddress() {
		if (this.address == null) {
			this.address = new PermanentAddress();
		}
		return this.address;
	}

	public void setAddress(PermanentAddress address) {
		this.address = address;
	}

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(
			UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MedicalPlace other = (MedicalPlace) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (version != other.version)
			return false;
		return true;
	}

}
