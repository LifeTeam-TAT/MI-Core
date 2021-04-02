package org.ace.insurance.report.TLF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.salesPoints.SalesPoints;

public class TlfCriteria {

	private SalesPoints salePoint;
	private Date startDate;
	private Date endDate;
	private PaymentChannel paymentChannel;
	private List<String> productIdList;

	public TlfCriteria() {

	}

	public TlfCriteria(SalesPoints salePoint, Date startDate, Date endDate, PaymentChannel paymentChannel, List<String> productIdList) {
		super();
		this.salePoint = salePoint;
		this.startDate = startDate;
		this.endDate = endDate;
		this.paymentChannel = paymentChannel;
		this.productIdList = productIdList;
	}

	public SalesPoints getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalesPoints salePoint) {
		this.salePoint = salePoint;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PaymentChannel getPaymentChannel() {

		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public List<String> getProductIdList() {
		if (productIdList == null || productIdList.isEmpty()) {
			return productIdList = new ArrayList<String>();
		} else {
			return productIdList;
		}
	}

	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}

}
