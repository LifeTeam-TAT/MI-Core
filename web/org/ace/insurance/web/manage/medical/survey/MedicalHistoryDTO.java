package org.ace.insurance.web.manage.medical.survey;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.icd10.ICD10;

public class MedicalHistoryDTO {
	private String id;
	private String tempId;
	private String causeOfHospitalization;
	private String result;
	private String medicalOfficer;
	private ICD10 icd10;
	private Hospital hospital;
	private int version;
	private boolean isExistsEntity;

	public MedicalHistoryDTO() {
		tempId = System.nanoTime() + "";
	}

	public MedicalHistoryDTO(MedicalHistoryDTO medicalHistoryDTO) {
		this.id = medicalHistoryDTO.getId();
		this.tempId = medicalHistoryDTO.getTempId();
		this.causeOfHospitalization = medicalHistoryDTO.getCauseOfHospitalization();
		this.result = medicalHistoryDTO.getResult();
		this.medicalOfficer = medicalHistoryDTO.getMedicalOfficer();
		this.icd10 = medicalHistoryDTO.getIcd10();
		this.hospital = medicalHistoryDTO.getHospital();
		this.version = medicalHistoryDTO.getVersion();
		this.isExistsEntity = medicalHistoryDTO.isExistsEntity();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getCauseOfHospitalization() {
		return causeOfHospitalization;
	}

	public void setCauseOfHospitalization(String causeOfHospitalization) {
		this.causeOfHospitalization = causeOfHospitalization;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMedicalOfficer() {
		return medicalOfficer;
	}

	public void setMedicalOfficer(String medicalOfficer) {
		this.medicalOfficer = medicalOfficer;
	}

	public ICD10 getIcd10() {
		return icd10;
	}

	public void setIcd10(ICD10 icd10) {
		this.icd10 = icd10;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isExistsEntity() {
		return isExistsEntity;
	}

	public void setExistsEntity(boolean isExistsEntity) {
		this.isExistsEntity = isExistsEntity;
	}

}
