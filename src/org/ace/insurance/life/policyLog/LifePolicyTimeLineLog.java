package org.ace.insurance.life.policyLog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFE_POLICY_TIMELINE_LOG)
@TableGenerator(name = "LIFEPOLICYTIMELINELOG_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYTIMELINELOG_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifePolicyTimeLineLog implements Serializable {

	private static final long serialVersionUID = 7890954693446572730L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYTIMELINELOG_GEN")
	private String id;

	@Column(name = "POLICYNO")
	private String policyNo;
	@Column(name = "FROMDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date from;
	@Column(name = "TODATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date to;
	@Column(name = "POLICYLIFECOUNT")
	private int policyLifeCount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicyTimeLineLog() {
	}

	public LifePolicyTimeLineLog(String policyNo, Date from, Date to, Date commenmanceDate) {
		this.policyNo = policyNo;
		this.from = from;
		this.to = to;
		this.policyLifeCount = 1;
		this.commenmanceDate = commenmanceDate;
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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getPolicyLifeCount() {
		return policyLifeCount;
	}

	public void setPolicyLifeCount(int policyLifeCount) {
		this.policyLifeCount = policyLifeCount;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
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
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + policyLifeCount;
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		LifePolicyTimeLineLog other = (LifePolicyTimeLineLog) obj;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (policyLifeCount != other.policyLifeCount)
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
