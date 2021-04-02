package org.ace.insurance.report.ClaimVoucher;

import java.util.Date;

import org.ace.insurance.common.Utils;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.web.common.ntw.eng.AbstractProcessor;
import org.ace.insurance.web.common.ntw.eng.DefaultProcessor;

public class ClaimVoucherDTO implements IDataModel {
	private String tempId;
	private String coaId;
	private double claimAmount;
	private String acName;
	private String baseAcName;
	private String status;
	private String narration;

	public ClaimVoucherDTO() {
		tempId = System.nanoTime() + "";
	}

	public ClaimVoucherDTO(String coaId, String narration, String acName, String status, double claimAmount) {
		this();
		this.coaId = coaId;
		this.claimAmount = claimAmount;
		this.acName = acName;
		this.status = transfromToLongTerm(status);
		this.narration = narration;
	}

	public String transfromToLongTerm(String status) {
		if (status.charAt(1) == 'C') {
			return "CREDIT";
		}
		return "DEBIT";
	}

	public String getGlCode() {
		return coaId.substring(0, 3).concat("000");
	}

	public String getClaimAmountStr() {
		StringBuffer sb = new StringBuffer();
		AbstractProcessor processor = new DefaultProcessor();
		for (String str : processor.getName(claimAmount).split(" ")) {
			sb.append(str.substring(0, 1).toUpperCase() + str.substring(1) + " ");
		}
		return sb.toString();
	}

	public String getCurrentD() {
		return Utils.formattedDate(new Date());
	}

	public String getCoaId() {
		return coaId;
	}

	public void setCoaId(String coaId) {
		this.coaId = coaId;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	@Override
	public String getId() {
		return tempId;
	}

	public String getBaseAcName() {
		return baseAcName;
	}

	public void setBaseAcName(String baseAcName) {
		this.baseAcName = baseAcName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

}
