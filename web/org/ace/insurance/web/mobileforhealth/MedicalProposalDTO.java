package org.ace.insurance.web.mobileforhealth;

import java.util.Date;

public class MedicalProposalDTO {

	private String proposalNo;
	private Date submitteddate;
	private String salechanneltype;
	
	
	
	
	public MedicalProposalDTO() {
	
	}
	public MedicalProposalDTO(String proposalNo, Date submitteddate, String salechanneltype) {
	
		this.proposalNo = proposalNo;
		this.submitteddate = submitteddate;
		this.salechanneltype = salechanneltype;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	public Date getSubmitteddate() {
		return submitteddate;
	}
	public void setSubmitteddate(Date submitteddate) {
		this.submitteddate = submitteddate;
	}
	public String getSalechanneltype() {
		return salechanneltype;
	}
	public void setSalechanneltype(String salechanneltype) {
		this.salechanneltype = salechanneltype;
	}
	
	
	
}
