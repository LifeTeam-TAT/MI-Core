package org.ace.insurance.life.policyLog;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFE_POLICY_ID_LOG)
@TableGenerator(name = "LIFEPOLICYIDLOG_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYIDLOG_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifePolicyIdLog {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYIDLOG_GEN")
	private String id;

	private String fromId;
	private String toId;
	private String proposalId;
	@Column(name = "ENTRY_TYPE")
	@Enumerated(EnumType.STRING)
	private ProposalType entryType;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYTIMELINELOGID", referencedColumnName = "ID")
	private LifePolicyTimeLineLog lifePolicyTimeLineLog;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicyIdLog() {
	}

	public LifePolicyIdLog(String fromId, String toId, String proposalId, ProposalType entryType, LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		this.fromId = fromId;
		this.toId = toId;
		this.proposalId = proposalId;
		this.entryType = entryType;
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

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public ProposalType getEntryType() {
		return entryType;
	}

	public void setEntryType(ProposalType entryType) {
		this.entryType = entryType;
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
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((fromId == null) ? 0 : fromId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifePolicyTimeLineLog == null) ? 0 : lifePolicyTimeLineLog.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((proposalId == null) ? 0 : proposalId.hashCode());
		result = prime * result + ((toId == null) ? 0 : toId.hashCode());
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
		LifePolicyIdLog other = (LifePolicyIdLog) obj;
		if (entryType != other.entryType)
			return false;
		if (fromId == null) {
			if (other.fromId != null)
				return false;
		} else if (!fromId.equals(other.fromId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifePolicyTimeLineLog == null) {
			if (other.lifePolicyTimeLineLog != null)
				return false;
		} else if (!lifePolicyTimeLineLog.equals(other.lifePolicyTimeLineLog))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (proposalId == null) {
			if (other.proposalId != null)
				return false;
		} else if (!proposalId.equals(other.proposalId))
			return false;
		if (toId == null) {
			if (other.toId != null)
				return false;
		} else if (!toId.equals(other.toId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
