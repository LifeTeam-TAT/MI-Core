package org.ace.insurance.life.policy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.product.Product;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.SPORTMANTRAVELABROAD)
@TableGenerator(name = "SPORTMANTRAVELABROAD_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SPORTMANTRAVELABROAD_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class SportManTravelAbroad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SPORTMANTRAVELABROAD_GEN")
	private String id;
	private String fromCity;
	private String toCity;

	private double premium;

	@Temporal(TemporalType.TIMESTAMP)
	private Date travelStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date travelEndDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SPORTMANABROADID", referencedColumnName = "ID")
	private List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
	private List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList;

	private String invoiceNo;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public SportManTravelAbroad() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public Date getTravelStartDate() {
		return travelStartDate;
	}

	public void setTravelStartDate(Date travelStartDate) {
		this.travelStartDate = travelStartDate;
	}

	public Date getTravelEndDate() {
		return travelEndDate;
	}

	public void setTravelEndDate(Date travelEndDate) {
		this.travelEndDate = travelEndDate;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public List<PolicyInsuredPersonKeyFactorValue> getPolicyInsuredPersonkeyFactorValueList() {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValue>();
		}
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<PolicyInsuredPersonAddon> getPolicyInsuredPersonAddOnList() {
		if (policyInsuredPersonAddOnList == null) {
			policyInsuredPersonAddOnList = new ArrayList<PolicyInsuredPersonAddon>();
		}
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getTotalAdditionalPremium() {
		double totAddOnPremium = 0.0;
		for (PolicyInsuredPersonAddon policyInsuredPersonAddon : policyInsuredPersonAddOnList) {
			totAddOnPremium = totAddOnPremium + policyInsuredPersonAddon.getPremium();
		}
		return totAddOnPremium;
	}

	public double getTotalPremium() {
		double totalPremium = 0.0;
		totalPremium = premium + getTotalAdditionalPremium();
		return totalPremium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromCity == null) ? 0 : fromCity.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((toCity == null) ? 0 : toCity.hashCode());
		result = prime * result + ((travelEndDate == null) ? 0 : travelEndDate.hashCode());
		result = prime * result + ((travelStartDate == null) ? 0 : travelStartDate.hashCode());
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
		SportManTravelAbroad other = (SportManTravelAbroad) obj;
		if (fromCity == null) {
			if (other.fromCity != null)
				return false;
		} else if (!fromCity.equals(other.fromCity))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (toCity == null) {
			if (other.toCity != null)
				return false;
		} else if (!toCity.equals(other.toCity))
			return false;
		if (travelEndDate == null) {
			if (other.travelEndDate != null)
				return false;
		} else if (!travelEndDate.equals(other.travelEndDate))
			return false;
		if (travelStartDate == null) {
			if (other.travelStartDate != null)
				return false;
		} else if (!travelStartDate.equals(other.travelStartDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
