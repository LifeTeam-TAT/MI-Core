package org.ace.insurance.life.policy;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPOLICY_ENDORSEMENT_PRINT)
@TableGenerator(name = "LIFEPOLICY_ENDORSEMENT_PRINT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICY_ENDORSEMENT_PRINT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "EndorsementLifePolicyPrint.findAll", query = "SELECT f FROM EndorsementLifePolicyPrint f "),
		@NamedQuery(name = "EndorsementLifePolicyPrint.findById", query = "SELECT f FROM EndorsementLifePolicyPrint f WHERE f.id = :id") })
@EntityListeners(IDInterceptor.class)
public class EndorsementLifePolicyPrint implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICY_ENDORSEMENT_PRINT_GEN")
	private String id;

	private String extraRegulation;
	private String endorseChange;
	private String endorseChangeDetail;
	private String endorsementDescription;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public EndorsementLifePolicyPrint() {
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

	public String getExtraRegulation() {
		return extraRegulation;
	}

	public void setExtraRegulation(String extraRegulation) {
		this.extraRegulation = extraRegulation;
	}

	public String getEndorsementDescription() {
		return endorsementDescription;
	}

	public void setEndorsementDescription(String endorsementDescription) {
		this.endorsementDescription = endorsementDescription;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public String getEndorseChange() {
		return endorseChange;
	}

	public void setEndorseChange(String endorseChange) {
		this.endorseChange = endorseChange;
	}

	public String getEndorseChangeDetail() {
		return endorseChangeDetail;
	}

	public void setEndorseChangeDetail(String endorseChangeDetail) {
		this.endorseChangeDetail = endorseChangeDetail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endorseChange == null) ? 0 : endorseChange.hashCode());
		result = prime * result + ((endorseChangeDetail == null) ? 0 : endorseChangeDetail.hashCode());
		result = prime * result + ((endorsementDescription == null) ? 0 : endorsementDescription.hashCode());
		result = prime * result + ((extraRegulation == null) ? 0 : extraRegulation.hashCode());
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
		EndorsementLifePolicyPrint other = (EndorsementLifePolicyPrint) obj;
		if (endorseChange == null) {
			if (other.endorseChange != null)
				return false;
		} else if (!endorseChange.equals(other.endorseChange))
			return false;
		if (endorseChangeDetail == null) {
			if (other.endorseChangeDetail != null)
				return false;
		} else if (!endorseChangeDetail.equals(other.endorseChangeDetail))
			return false;
		if (endorsementDescription == null) {
			if (other.endorsementDescription != null)
				return false;
		} else if (!endorsementDescription.equals(other.endorsementDescription))
			return false;
		if (extraRegulation == null) {
			if (other.extraRegulation != null)
				return false;
		} else if (!extraRegulation.equals(other.extraRegulation))
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
