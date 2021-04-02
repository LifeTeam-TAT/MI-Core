package org.ace.insurance.medical.claim;

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
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIM_INITIAL_REPORT_ICD)
@TableGenerator(name = "CLAIMINITIALREPORTICD_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIMINITIALREPORTICD_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class ClaimInitialReportICD {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIMINITIALREPORTICD_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ICDID", referencedColumnName = "ID")
	private ICD10 icd10;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMINITIALREPORTID", referencedColumnName = "ID")
	private ClaimInitialReport claimInitialReport;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public ClaimInitialReportICD() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ICD10 getIcd10() {
		return icd10;
	}

	public void setIcd10(ICD10 icd10) {
		this.icd10 = icd10;
	}

	public ClaimInitialReport getClaimInitialReport() {
		return claimInitialReport;
	}

	public void setClaimInitialReport(ClaimInitialReport claimInitialReport) {
		this.claimInitialReport = claimInitialReport;
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
		result = prime * result + ((claimInitialReport == null) ? 0 : claimInitialReport.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((icd10 == null) ? 0 : icd10.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ClaimInitialReportICD other = (ClaimInitialReportICD) obj;
		if (claimInitialReport == null) {
			if (other.claimInitialReport != null)
				return false;
		} else if (!claimInitialReport.equals(other.claimInitialReport))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (icd10 == null) {
			if (other.icd10 != null)
				return false;
		} else if (!icd10.equals(other.icd10))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
