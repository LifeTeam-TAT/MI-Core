package org.ace.insurance.system.common.cashier;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CASHIER)
@TableGenerator(name = "CASHIER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CASHIER_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Cashier.findAll", query = "SELECT a FROM Cashier a ORDER BY a.name.firstName ASC"),
		@NamedQuery(name = "Cashier.findById", query = "SELECT a FROM Cashier a WHERE a.id = :id") })
@EntityListeners(IDInterceptor.class)
public class Cashier implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CASHIER_GEN")
	private String id;
	private String initialId;
	private String idNo;

	@Embedded
	private Name name;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "STATECODEID", referencedColumnName = "ID")
	// private StateCode stateCode;
	//
	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "TOWNSHIPCODEID", referencedColumnName = "ID")
	// private TownshipCode townshipCode;

	@Enumerated(value = EnumType.STRING)
	private IdConditionType idConditionType;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WORKSHOPID", referencedColumnName = "ID")
	private WorkShop workshop;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public Cashier() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId + " ";
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName();
			}
		}
		return result;
	}

	public String getFullIdNo() {
		String result = "";
		// if (stateCode != null) {
		// result = result + stateCode.getCodeNo() + "/";
		// }
		// if (townshipCode != null) {
		// result = result + townshipCode.getTownshipcodeno();
		// }
		if (idConditionType != null) {
			result = result + "(" + idConditionType.getLabel() + ")";
		}
		if (result.isEmpty() && idNo == null) {
			return result;
		}
		if (result.isEmpty() && idNo != null) {
			return idNo;
		} else {
			return result + idNo;
		}
	}

	public Name getName() {
		if (this.name == null) {
			this.name = new Name();
		}
		return this.name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	// public StateCode getStateCode() {
	// return stateCode;
	// }
	//
	// public void setStateCode(StateCode stateCode) {
	// this.stateCode = stateCode;
	// }
	//
	// public TownshipCode getTownshipCode() {
	// return townshipCode;
	// }
	//
	// public void setTownshipCode(TownshipCode townshipCode) {
	// this.townshipCode = townshipCode;
	// }

	public IdConditionType getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(IdConditionType idConditionType) {
		this.idConditionType = idConditionType;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public WorkShop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(WorkShop workshop) {
		this.workshop = workshop;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime * result + ((stateCode == null) ? 0 :
		// stateCode.hashCode());
		// result = prime * result + ((townshipCode == null) ? 0 :
		// townshipCode.hashCode());
		result = prime * result + version;
		result = prime * result + ((workshop == null) ? 0 : workshop.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		Cashier other = (Cashier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idConditionType != other.idConditionType)
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		// if (stateCode == null) {
		// if (other.stateCode != null)
		// return false;
		// } else if (!stateCode.equals(other.stateCode))
		// return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		// if (townshipCode == null) {
		// if (other.townshipCode != null)
		// return false;
		// } else if (!townshipCode.equals(other.townshipCode))
		// return false;
		if (version != other.version)
			return false;
		if (workshop == null) {
			if (other.workshop != null)
				return false;
		} else if (!workshop.equals(other.workshop))
			return false;
		return true;
	}

}
