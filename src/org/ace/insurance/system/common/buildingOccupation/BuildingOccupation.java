package org.ace.insurance.system.common.buildingOccupation;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = TableName.BUILDINGOCCUPATION)
@TableGenerator(name = "BUILDINGOCCUPATION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "BUILDINGOCCUPATION_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "BuildingOccupation.findAll", query = "SELECT o FROM BuildingOccupation o ORDER BY o.name ASC"),
		@NamedQuery(name = "BuildingOccupation.findById", query = "SELECT o FROM BuildingOccupation o WHERE o.id = :id") })
@EntityListeners(IDInterceptor.class)
public class BuildingOccupation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OCCUPATION_GEN")
	private String id;

	@Enumerated(EnumType.STRING)
	private BuildingOccupationType buildingOccupationType;

	private String name;

	private String myanDescription;
	private String description;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public BuildingOccupation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BuildingOccupationType getBuildingOccupationType() {
		return buildingOccupationType;
	}

	public void setBuildingOccupationType(BuildingOccupationType buildingOccupationType) {
		this.buildingOccupationType = buildingOccupationType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String month) {
		this.name = month;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getMyanDescription() {
		return myanDescription;
	}

	public void setMyanDescription(String myanDescription) {
		this.myanDescription = myanDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingOccupationType == null) ? 0 : buildingOccupationType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		BuildingOccupation other = (BuildingOccupation) obj;
		if (buildingOccupationType != other.buildingOccupationType)
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