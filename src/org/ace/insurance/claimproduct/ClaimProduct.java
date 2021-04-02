/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIMPRODUCT)
@TableGenerator(name = "CLAIMPRODUCT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIMPRODUCT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimProduct.findAll", query = "SELECT p FROM ClaimProduct p ORDER by p.name ASC"),
		@NamedQuery(name = "ClaimProduct.findById", query = "SELECT p FROM ClaimProduct p WHERE p.id = :id"),
		@NamedQuery(name = "ClaimProduct.findByInsuranceType", query = "SELECT p FROM ClaimProduct p WHERE p.insuranceType = :insuranceType") })
@EntityListeners(IDInterceptor.class)
public class ClaimProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIMPRODUCT_GEN")
	private String id;
	private String name;
	private boolean autoCalculate;
	private double fixedValue;
	private int shortTermActivatedYear;
	private int longTermActivatedYear;

	/*
	 * If PremiumRateType is "BASED_ON_BASE_SUMINSURED", premium calculated by
	 * baseSumInsured
	 */
	private double baseSumInsured;

	@Enumerated(EnumType.STRING)
	private ClaimRateType rateType;

	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CLAIMPRODUCT_KEYFACTOR_LINK", joinColumns = { @JoinColumn(name = "CLAIMPRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID") })
	private List<KeyFactor> keyFactors;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public ClaimProduct() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAutoCalculate() {
		return autoCalculate;
	}

	public void setAutoCalculate(boolean autoCalculate) {
		this.autoCalculate = autoCalculate;
	}

	public double getFixedValue() {
		return fixedValue;
	}

	public void setFixedValue(double fixedValue) {
		this.fixedValue = fixedValue;
	}

	public int getShortTermActivatedYear() {
		return shortTermActivatedYear;
	}

	public void setShortTermActivatedYear(int shortTermActivatedYear) {
		this.shortTermActivatedYear = shortTermActivatedYear;
	}

	public int getLongTermActivatedYear() {
		return longTermActivatedYear;
	}

	public void setLongTermActivatedYear(int longTermActivatedYear) {
		this.longTermActivatedYear = longTermActivatedYear;
	}

	public double getBaseSumInsured() {
		return baseSumInsured;
	}

	public void setBaseSumInsured(double baseSumInsured) {
		this.baseSumInsured = baseSumInsured;
	}

	public ClaimRateType getRateType() {
		return rateType;
	}

	public void setRateType(ClaimRateType rateType) {
		this.rateType = rateType;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public List<KeyFactor> getKeyFactors() {
		return keyFactors;
	}

	public void setKeyFactors(List<KeyFactor> keyFactors) {
		this.keyFactors = keyFactors;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autoCalculate ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(baseSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(fixedValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuranceType == null) ? 0 : insuranceType.hashCode());
		result = prime * result + longTermActivatedYear;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rateType == null) ? 0 : rateType.hashCode());
		result = prime * result + shortTermActivatedYear;
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
		ClaimProduct other = (ClaimProduct) obj;
		if (autoCalculate != other.autoCalculate)
			return false;
		if (Double.doubleToLongBits(baseSumInsured) != Double.doubleToLongBits(other.baseSumInsured))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(fixedValue) != Double.doubleToLongBits(other.fixedValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuranceType != other.insuranceType)
			return false;
		if (longTermActivatedYear != other.longTermActivatedYear)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rateType != other.rateType)
			return false;
		if (shortTermActivatedYear != other.shortTermActivatedYear)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public boolean isBasicSumInsured() {
		if (ClaimRateType.BASED_ON_SUMINSURED.equals(rateType) && autoCalculate == true) {
			return true;
		}
		return false;
	}
}