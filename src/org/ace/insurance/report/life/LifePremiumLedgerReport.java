package org.ace.insurance.report.life;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.payment.Payment;

public class LifePremiumLedgerReport {
	private LifePolicy lifePolicy;
	private Date surveyDate;
	private List<PremiumLedgerInfo> premiumLedgerInfoList;
	
	public LifePremiumLedgerReport() {
	}

	public LifePremiumLedgerReport(LifePolicy lifePolicy, List<Payment> paymentList, Date surveyDate) {
		this.lifePolicy = lifePolicy;
		this.surveyDate = surveyDate;
		premiumLedgerInfoList = new ArrayList<PremiumLedgerInfo>();
		PremiumLedgerInfo ledgerInfo = null;
		Date dueDate = lifePolicy.getCommenmanceDate();
		int periodMonths = lifePolicy.getPaymentType().getMonth(); 
		for (int i = 0; i < paymentList.size(); i++) {
			if (i == 0) {
				ledgerInfo = new PremiumLedgerInfo(null, paymentList.get(i).getPaymentDate(), 
						paymentList.get(i).getReceiptNo());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dueDate);
				calendar.add(Calendar.MONTH, periodMonths);
				dueDate = calendar.getTime();
				ledgerInfo = new PremiumLedgerInfo(dueDate, paymentList.get(i).getPaymentDate(), 
						paymentList.get(i).getReceiptNo());
			}
			premiumLedgerInfoList.add(ledgerInfo);
		}
		// sorting by receipt date
		Collections.sort(premiumLedgerInfoList, new Comparator<PremiumLedgerInfo>() {
		  public int compare(PremiumLedgerInfo o1, PremiumLedgerInfo o2) {
		      if (o1.getReceiptDate() == null || o2.getReceiptDate() == null)
		        return 0;
		      return o1.getReceiptDate().compareTo(o2.getReceiptDate());
		  }
		});
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}
	
	public List<PremiumLedgerInfo> getPremiumLedgerInfoList() {
		return premiumLedgerInfoList;
	}

	public void setPremiumLedgerInfoList( List<PremiumLedgerInfo> premiumLedgerInfoList) {
		this.premiumLedgerInfoList = premiumLedgerInfoList;
	}
	
	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public class PremiumLedgerInfo {
		private Date dueDate = null;
		private Date receiptDate;
		private String receiptNo;
		
		public PremiumLedgerInfo(){
		}

		public PremiumLedgerInfo(Date dueDate, Date receiptDate, String receiptNo) {
			super();
			this.dueDate = dueDate;
			this.receiptDate = receiptDate;
			this.receiptNo = receiptNo;
		}

		public Date getDueDate() {
			return dueDate;
		}

		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}

		public Date getReceiptDate() {
			return receiptDate;
		}

		public void setReceiptDate(Date receiptDate) {
			this.receiptDate = receiptDate;
		}

		public String getReceiptNo() {
			return receiptNo;
		}

		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}
		
	}
	
}
