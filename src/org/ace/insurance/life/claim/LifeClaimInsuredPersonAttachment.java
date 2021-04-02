package org.ace.insurance.life.claim;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.java.component.idgen.service.IDInterceptor;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

// import org.ace.insurance.life.claim.garbage.ClaimInsuredPerson;
@Entity
@Table(name = TableName.LIFECLAIM_INSUREDPERSON_ATTACHMENT)
@TableGenerator(name = "LIFECLAIM_INSURED_PERSON_ATTACHMENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIM_INSURED_PERSON_ATTACHMENT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimInsuredPersonAttachment.findAll", query = "SELECT m FROM LifeClaimInsuredPersonAttachment m "),
		@NamedQuery(name = "ClaimInsuredPersonAttachment.findById", query = "SELECT m FROM LifeClaimInsuredPersonAttachment m WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeClaimInsuredPersonAttachment implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIM_INSURED_PERSON_ATTACHMENT_GEN")
	private String id;

	private String name;
	private String filePath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMINSUREDPERSONID", referencedColumnName = "ID")
	private LifeClaimInsuredPerson lifeClaimInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeClaimInsuredPersonAttachment() {
	}

	public LifeClaimInsuredPersonAttachment(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	public LifeClaimInsuredPersonAttachment(String name, String filePath, LifeClaimInsuredPerson lifeClaimInsuredPerson) {
		this.name = name;
		this.filePath = filePath;
		this.lifeClaimInsuredPerson = lifeClaimInsuredPerson;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LifeClaimInsuredPerson getClaimInsuredPerson() {
		return lifeClaimInsuredPerson;
	}

	public void setLifeClaimInsuredPerson(LifeClaimInsuredPerson claimInsuredPerson) {
		this.lifeClaimInsuredPerson = claimInsuredPerson;
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
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
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
		LifeClaimInsuredPersonAttachment other = (LifeClaimInsuredPersonAttachment) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
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
