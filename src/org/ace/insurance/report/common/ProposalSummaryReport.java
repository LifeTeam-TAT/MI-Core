package org.ace.insurance.report.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProposalSummaryReport {
	private String name;
	private List<ProductInfo> productInfoList;

	public ProposalSummaryReport() {
	}

	public ProposalSummaryReport(List<Object> saleman, List<Object> agent, List<String> productList, String type) {
		name = type;
		productInfoList = new ArrayList<ProductInfo>();
		Map<String, ProductInfo> map = new HashMap<String, ProductInfo>();

		for (String product : productList) {
			map.put(product, new ProductInfo(product));
		}

		if (!saleman.isEmpty() || saleman != null) {
			for (Object object : saleman) {
				Object[] objArray = (Object[]) object;
				map.get((String) objArray[1]).setSalemanCount((Long) objArray[0]);
			}
		}

		if (!agent.isEmpty() || agent != null) {
			for (Object object : agent) {
				Object[] objArray = (Object[]) object;
				map.get((String) objArray[1]).setAgentCount((Long) objArray[0]);
			}
		}

		for (ProductInfo productInfo : map.values()) {
			productInfoList.add(productInfo);
		}

	}

	public class ProductInfo {
		private String productName = null;
		private Long salemanCount;
		private Long agentCount;

		public ProductInfo() {
		}

		public ProductInfo(String productName) {
			this.productName = productName;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Long getSalemanCount() {
			return salemanCount == null ? 0L : salemanCount;
		}

		public void setSalemanCount(Long salemanCount) {
			this.salemanCount = salemanCount;
		}

		public Long getAgentCount() {
			return agentCount == null ? 0L : agentCount;
		}

		public void setAgentCount(Long agentCount) {
			this.agentCount = agentCount;
		}

		public Long gettotal() {
			if (agentCount == null)
				agentCount = 0L;
			if (salemanCount == null)
				salemanCount = 0L;
			return agentCount + salemanCount;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductInfo> getProductInfoList() {
		if (productInfoList == null) {
			productInfoList = new ArrayList<ProductInfo>();
		}
		return productInfoList;
	}

	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public int getTotalSaleman() {
		int result = 0;
		for (ProductInfo productInfo : productInfoList) {
			result += productInfo.getSalemanCount();
		}
		return result;
	}

	public int getTotalAgent() {
		int result = 0;
		for (ProductInfo productInfo : productInfoList) {
			result += productInfo.getAgentCount();
		}
		return result;
	}

	public int getTotal() {
		return getTotalSaleman() + getTotalAgent();
	}

}
