package org.ace.insurance.report.TLF;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.web.common.ntw.eng.AbstractProcessor;
import org.ace.insurance.web.common.ntw.eng.DefaultProcessor;
import org.ace.java.component.SystemException;

public class TLFVoucherDTO implements IDataModel {

	private String tempId;
	private String coaId;
	private String narration;
	private String status;
	private String acName;
	private String baseAcName;
	private Date confirmDate;
	private double homeAmount;
	private Currency currency;
	private double rate;
	private double amount;
	private static Properties pro;
	private double basicPremium;
	private double addOnPremium;
	private double netPremium;
	private double localAmount;
	private String companyName;
	private String glCode;

	static {
		try {
			pro = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("mym-number.properties");
			pro.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load mym-number.properties");
		}
	}

	public TLFVoucherDTO() {
		tempId = System.nanoTime() + "";
	}

	public TLFVoucherDTO(String coaId, String narration, String status, String acName, Currency currency, Date confirmDate, double homeAmount, double rate, double amount,
			double basicPremium, double addOnPremium, double localAmount, String glName, String glCode) {
		this();
		this.coaId = coaId;
		this.narration = narration;
		this.status = transfromToLongTerm(status);
		this.acName = acName;
		this.currency = currency;
		this.confirmDate = confirmDate;
		this.homeAmount = homeAmount;
		this.rate = rate;
		this.amount = amount;
		this.addOnPremium = addOnPremium;
		this.basicPremium = basicPremium;
		this.netPremium = addOnPremium + basicPremium;
		this.localAmount = localAmount;
		this.baseAcName = glName;
		this.glCode = glCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCoaId() {
		return coaId;
	}

	public void setCoaId(String coaId) {
		this.coaId = coaId;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public double getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(double localAmount) {
		this.localAmount = localAmount;
	}

	public double getHomeAmount() {
		return homeAmount;
	}

	public void setHomeAmount(double homeAmount) {
		this.homeAmount = homeAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String transfromToLongTerm(String status) {
		if (status.charAt(1) == 'C') {
			return "CREDIT/" + pro.getProperty("MYM_CREDIT");
		}
		return "DEBIT/" + pro.getProperty("MYM_DEBIT");
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public String getConfirmDateString() {
		return Utils.getDateFormatString(confirmDate);
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getBaseAcName() {
		return baseAcName;
	}

	public void setBaseAcName(String baseAcName) {
		this.baseAcName = baseAcName;
	}

	public String getGlCode() {
		return glCode;
	}

	public String getFigure() {
		AbstractProcessor processor = new DefaultProcessor();
		return processor.getNameWithDecimal(homeAmount, currency);
	}

	public String getAmountByString() {
		String result = "";
		AbstractProcessor processor = new DefaultProcessor();
		result = processor.getName(homeAmount);
		String[] arr = result.split("And");
		if (arr.length > 1) {
			result = "KYAT " + arr[0] + "And PYA" + arr[1];
		} else {
			result = "KYAT " + arr[0];
		}
		return result;
	}

	public String getAmountUSDByString() {
		AbstractProcessor processor = new DefaultProcessor();
		return processor.getNameWithDecimal(localAmount, currency);
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String getId() {
		return tempId;
	}

	public double getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(double basicPremium) {
		this.basicPremium = basicPremium;
	}

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public String getHomeAmountByString() {
		return Utils.getCurrencyFormatString(homeAmount);

	}

	public String getLocalAmountByString() {
		return Utils.getCurrencyFormatString(localAmount);

	}

	public String getRateByString() {
		return Utils.getCurrencyFormatString(rate);

	}

	public static void main(String[] args) {
		String result = "asdfadf adfafd afdafadf";
		String[] arr = result.split("And");
		System.out.println(arr[0]);

	}

}
