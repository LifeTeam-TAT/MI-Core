package org.ace.insurance.life.premium;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.payment.Payment;
import org.ace.java.component.idgen.service.IDInterceptor;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/11
 */
// TODO UNUSED
@Entity
@Table(name = TableName.LIFEPOLICYBILLING)
@TableGenerator(name = "LIFEPOLICYBILLING_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYBILLING_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifePolicyBilling.findAll", query = "SELECT m FROM LifePolicyBilling m "),
		@NamedQuery(name = "LifePolicyBilling.findById", query = "SELECT m FROM LifePolicyBilling m WHERE m.id = :id"),
		@NamedQuery(name = "LifePolicyBilling.findByBillingNo", query = "SELECT m FROM LifePolicyBilling m WHERE m.billingNo = :billingNo") })
@EntityListeners(IDInterceptor.class)
public class LifePolicyBilling implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYBILLING_GEN")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date billingDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date billingDeadLine;

	private double billingAmount;

	private String billingNo;

	@Version
	private int version;

	@Temporal(TemporalType.TIMESTAMP)
	private Date collectDate;
	private boolean isCollected;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTID", referencedColumnName = "ID")
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYPREMIUMID", referencedColumnName = "ID")
	private LifePolicyPremium lifePolicyPremium;

	@Embedded
	private UserRecorder recorder;

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

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public Date getBillingDeadLine() {
		return billingDeadLine;
	}

	public void setBillingDeadLine(Date billingDeadLine) {
		this.billingDeadLine = billingDeadLine;
	}

	public double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public LifePolicyPremium getLifePolicyPremium() {
		return lifePolicyPremium;
	}

	public void setLifePolicyPremium(LifePolicyPremium lifePolicyPremium) {
		this.lifePolicyPremium = lifePolicyPremium;
	}

	public String getBillingNo() {
		return billingNo;
	}

	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
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
		long temp;
		temp = Double.doubleToLongBits(billingAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((billingDate == null) ? 0 : billingDate.hashCode());
		result = prime * result + ((billingDeadLine == null) ? 0 : billingDeadLine.hashCode());
		result = prime * result + ((billingNo == null) ? 0 : billingNo.hashCode());
		result = prime * result + ((collectDate == null) ? 0 : collectDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCollected ? 1231 : 1237);
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
		LifePolicyBilling other = (LifePolicyBilling) obj;
		if (Double.doubleToLongBits(billingAmount) != Double.doubleToLongBits(other.billingAmount))
			return false;
		if (billingDate == null) {
			if (other.billingDate != null)
				return false;
		} else if (!billingDate.equals(other.billingDate))
			return false;
		if (billingDeadLine == null) {
			if (other.billingDeadLine != null)
				return false;
		} else if (!billingDeadLine.equals(other.billingDeadLine))
			return false;
		if (billingNo == null) {
			if (other.billingNo != null)
				return false;
		} else if (!billingNo.equals(other.billingNo))
			return false;
		if (collectDate == null) {
			if (other.collectDate != null)
				return false;
		} else if (!collectDate.equals(other.collectDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCollected != other.isCollected)
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
