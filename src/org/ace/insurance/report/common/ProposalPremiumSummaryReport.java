package org.ace.insurance.report.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProposalPremiumSummaryReport {
	private String name;
	private List<ProductPremiumInfo> productPremiumInfoList;

	public ProposalPremiumSummaryReport() {
	}

	public ProposalPremiumSummaryReport(List<Object> salemanPremium, List<Object> agentPremium, List<String> productList, String type) {
		productPremiumInfoList = new ArrayList<ProductPremiumInfo>();
		Map<String, ProductPremiumInfo> map = new HashMap<String, ProductPremiumInfo>();

		for (String product : productList) {
			map.put(product, new ProductPremiumInfo(product));
		}

		if (!salemanPremium.isEmpty() || salemanPremium != null) {
			for (Object object : salemanPremium) {
				Object[] objArray = (Object[]) object;
				map.get((String) objArray[1]).setSalemanTotalPremium((Double) objArray[0]);
			}
		}

		if (!agentPremium.isEmpty() || agentPremium != null) {
			for (Object object : agentPremium) {
				Object[] objArray = (Object[]) object;
				map.get((String) objArray[1]).setAgentTotalPremium((Double) objArray[0]);
			}
		}

		for (ProductPremiumInfo productPremiumInfo : map.values()) {
			productPremiumInfoList.add(productPremiumInfo);
		}

	}

	public class ProductPremiumInfo {
		private String productName = null;
		private double salemanTotalPremium;
		private double agentTotalPremium;

		public ProductPremiumInfo() {
		}

		public ProductPremiumInfo(String productName) {
			this.productName = productName;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public double getSalemanTotalPremium() {
			return salemanTotalPremium;
		}

		public void setSalemanTotalPremium(double salemanTotalPremium) {
			this.salemanTotalPremium = salemanTotalPremium;
		}

		public double getAgentTotalPremium() {
			return agentTotalPremium;
		}

		public void setAgentTotalPremium(double agentTotalPremium) {
			this.agentTotalPremium = agentTotalPremium;
		}

		public double gettotal() {
			return agentTotalPremium + salemanTotalPremium;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductPremiumInfo> getProductPremiumInfoList() {
		if (productPremiumInfoList == null) {
			productPremiumInfoList = new ArrayList<ProductPremiumInfo>();
		}
		return productPremiumInfoList;
	}

	public void setProductPremiumInfoList(List<ProductPremiumInfo> productPremiumInfoList) {
		this.productPremiumInfoList = productPremiumInfoList;
	}

	public double getTotalSaleman() {
		double result = 0;
		for (ProductPremiumInfo productPremiumInfo : productPremiumInfoList) {
			result += productPremiumInfo.getSalemanTotalPremium();
		}
		return result;
	}

	public double getTotalAgent() {
		double result = 0;
		for (ProductPremiumInfo productPremiumInfo : productPremiumInfoList) {
			result += productPremiumInfo.getAgentTotalPremium();
		}
		return result;
	}

	public double getTotal() {
		return getTotalSaleman() + getTotalAgent();
	}

}
