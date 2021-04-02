package org.ace.insurance.system.common.bank;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = TableName.COA)
@TableGenerator(name = "COA_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "COA_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class Coa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String acName;
	private String acCode;
	private char acType;
	private String acCodeType;
	private Date pDate;
	private String ibsbACode;

	private String headId;

	private String groupId;
	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public Coa() {
	}

	public Coa(String acCode, char acType) {
		this.acCode = acCode;
		this.acType = acType;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public char getAcType() {
		return this.acType;
	}

	public void setAcType(char acType) {
		this.acType = acType;
	}

	public Date getpDate() {
		return pDate;
	}

	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAcCode() {
		return acCode;
	}

	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}

	public String getAcCodeType() {
		return acCodeType;
	}

	public void setAcCodeType(String acCodeType) {
		this.acCodeType = acCodeType;
	}

	public String getIbsbACode() {
		return ibsbACode;
	}

	public void setIbsbACode(String ibsbACode) {
		this.ibsbACode = ibsbACode;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
