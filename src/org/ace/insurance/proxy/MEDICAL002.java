// package org.ace.insurance.proxy;
//
// import java.io.Serializable;
// import java.util.Date;
//
// import org.ace.insurance.common.ISorter;
// import org.ace.insurance.common.Name;
// import org.ace.insurance.common.interfaces.IDataModel;
//
// public class MEDICAL002 implements Serializable, ISorter, IDataModel {
//
// private static final long serialVersionUID = 1L;
// private String id;
// private String proposalNo;
// private String customerName;
// private Date submittedDate;
// private Date pendingSince;
//
// public MEDICAL002() {
//
// }
//
// public MEDICAL002(String id, String proposalNo, Name cusName, Date
// submittedDate, Date pendingSince) {
// this.id = id;
// this.proposalNo = proposalNo;
// this.customerName = cusName.getFullName();
// this.submittedDate = submittedDate;
// this.pendingSince = pendingSince;
// }
//
// @Override
// public String getId() {
// return id;
// }
//
// public void setId(String id) {
// this.id = id;
// }
//
// public String getProposalNo() {
// return proposalNo;
// }
//
// public void setProposalNo(String proposalNo) {
// this.proposalNo = proposalNo;
// }
//
// public String getCustomerName() {
// return customerName;
// }
//
// public void setCustomerName(String customerName) {
// this.customerName = customerName;
// }
//
// public Date getSubmittedDate() {
// return submittedDate;
// }
//
// public void setSubmittedDate(Date submittedDate) {
// this.submittedDate = submittedDate;
// }
//
// public Date getPendingSince() {
// return pendingSince;
// }
//
// public void setPendingSince(Date pendingSince) {
// this.pendingSince = pendingSince;
// }
//
// @Override
// public String getRegistrationNo() {
// return proposalNo;
// }
//
// }
