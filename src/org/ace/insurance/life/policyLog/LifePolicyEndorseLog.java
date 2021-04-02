package org.ace.insurance.life.policyLog;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFE_POLICY_ENDORSE_LOG)
@TableGenerator(name = "LIFEPOLICYENDORSELOG_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYTIMELINELOG_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifePolicyEndorseLog {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYENDORSELOG_GEN")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endorsementDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endorsementConfirmDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEENDORSEINFOID", referencedColumnName = "ID")
	private LifeEndorseInfo lifeEndorseInfo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYTIMELINELOGID", referencedColumnName = "ID")
	private LifePolicyTimeLineLog lifePolicyTimeLineLog;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicyEndorseLog() {

	}

	public LifePolicyEndorseLog(Date endorsementDate, Date endorsementConfirmDate, LifeEndorseInfo lifeEndorseInfo, LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		this.endorsementDate = endorsementDate;
		this.endorsementConfirmDate = endorsementConfirmDate;
		this.lifeEndorseInfo = lifeEndorseInfo;
		this.lifePolicyTimeLineLog = lifePolicyTimeLineLog;
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

	public Date getEndorsementDate() {
		return endorsementDate;
	}

	public void setEndorsementDate(Date endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public Date getEndorsementConfirmDate() {
		return endorsementConfirmDate;
	}

	public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
		this.endorsementConfirmDate = endorsementConfirmDate;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public void setLifeEndorseInfo(LifeEndorseInfo lifeEndorseInfo) {
		this.lifeEndorseInfo = lifeEndorseInfo;
	}

	public LifePolicyTimeLineLog getLifePolicyTimeLineLog() {
		return lifePolicyTimeLineLog;
	}

	public void setLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		this.lifePolicyTimeLineLog = lifePolicyTimeLineLog;
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
		result = prime * result + ((endorsementConfirmDate == null) ? 0 : endorsementConfirmDate.hashCode());
		result = prime * result + ((endorsementDate == null) ? 0 : endorsementDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LifePolicyEndorseLog other = (LifePolicyEndorseLog) obj;
		if (endorsementConfirmDate == null) {
			if (other.endorsementConfirmDate != null)
				return false;
		} else if (!endorsementConfirmDate.equals(other.endorsementConfirmDate))
			return false;
		if (endorsementDate == null) {
			if (other.endorsementDate != null)
				return false;
		} else if (!endorsementDate.equals(other.endorsementDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
