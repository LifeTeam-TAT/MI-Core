package org.ace.insurance.system.common.surveyTeam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.SURVEYTEAM)
@TableGenerator(name = "SURVEYTEAM_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SURVEYTEAM_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "SurveyTeam.findAll", query = "Select t from SurveyTeam t order by t.name ASC") })
@EntityListeners(IDInterceptor.class)
public class SurveyTeam implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SURVEYTEAM_GEN")
	private String id;
	private String name;
	private String description;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SURVEYTEAM_SURVEYMEMBER_LINK", joinColumns = { @JoinColumn(name = "SURVEYTEAMID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SURVEYMEMBERID", referencedColumnName = "ID") })
	private List<SurveyMember> surveyMemberList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SurveyMember> getSurveyMemberList() {
		if (surveyMemberList == null) {
			surveyMemberList = new ArrayList<>();
		}
		return surveyMemberList;
	}

	public void setSurveyMemberList(List<SurveyMember> surveyMemberList) {
		this.surveyMemberList = surveyMemberList;
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
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SurveyTeam other = (SurveyTeam) obj;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
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
		if (version != other.version)
			return false;
		return true;
	}

}
