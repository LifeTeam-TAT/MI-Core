package org.ace.insurance.web.manage.managementreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.managementreport.customer.CustomerReport;
import org.ace.insurance.managementreport.customer.service.interfaces.ICustomerReportService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ViewScoped
@ManagedBean(name = "ManagementCustomerReportActionBean")
public class ManagementCustomerReportActionBean extends BaseBean implements Serializable {
	@ManagedProperty(value = "#{CustomerReportService}")
	private ICustomerReportService customerReportService;

	public void setCustomerReportService(ICustomerReportService customerReportService) {
		this.customerReportService = customerReportService;
	}

	private CustomerReport customerReport;
	private CartesianChartModel barModel;
	private String selectedCustomerOverview;
	private List<Entry<String, Number>> entries;
	private int grandTotal;
	private SortedMap<String, Number> sortMap;

	@PostConstruct
	public void init() {
		setSelectedCustomerOverview(CustomerOverview.ACTIVE_POLICY.toString());
		search();
	}

	public void createCustomerByActivePoliciesBarChart() {
		grandTotal = 0;
		barModel = new CartesianChartModel();
		customerReport = customerReportService.findActiveCustomerByActivePolicies();
		ChartSeries chartSeries = new ChartSeries();
		Map<String, Number> pMap = customerReport.getPieChartMap();
		entries = new ArrayList<Entry<String, Number>>(sortByKey(pMap).entrySet());

		for (String str : sortByKey(pMap).keySet()) {
			chartSeries.set(str, pMap.get(str));
			grandTotal += Integer.parseInt(pMap.get(str).toString());
		}
		chartSeries.setLabel("Total");
		barModel.addSeries(chartSeries);
	}

	public void createCustomerByLocation() {
		barModel = new CartesianChartModel();
		customerReport = customerReportService.findActiveCustomerByLocation();
		ChartSeries chartSeries = new ChartSeries();

		Map<String, Number> pMap = customerReport.getPieChartMap();
		entries = new ArrayList<Entry<String, Number>>(pMap.entrySet());
		int count = 0;
		grandTotal = 0;
		for (String str : pMap.keySet()) {
			if (count < 15) {
				chartSeries.set(str, pMap.get(str));
				count++;
			}
			grandTotal += Integer.parseInt(pMap.get(str).toString());
		}
		chartSeries.setLabel("Total");
		barModel.addSeries(chartSeries);
	}

	public void createCustomerByGenderBarChart() {
		grandTotal = 0;
		barModel = new CartesianChartModel();
		customerReport = customerReportService.findActiveCustomerByGender();
		ChartSeries chartSeries = new ChartSeries();

		Map<String, Number> pMap = customerReport.getPieChartMap();
		entries = new ArrayList<Entry<String, Number>>(pMap.entrySet());
		for (String str : pMap.keySet()) {
			chartSeries.set(str, pMap.get(str));
			grandTotal += Integer.parseInt(pMap.get(str).toString());
		}
		chartSeries.setLabel("Total");
		barModel.addSeries(chartSeries);
	}

	public void createCustomerByTypeBarChart() {
		grandTotal = 0;
		barModel = new CartesianChartModel();
		customerReport = customerReportService.findActiveCustomerByType();
		Map<String, Number> pMap = customerReport.getPieChartMap();
		entries = new ArrayList<Entry<String, Number>>(pMap.entrySet());
		ChartSeries chartSeries = new ChartSeries();
		Number iCount = customerReport.getPieChartMap().get("INDIVIDUAL");
		Number oCount = customerReport.getPieChartMap().get("ORGANIZATION");
		chartSeries.setLabel("Total");
		chartSeries.set("INDIVIDUAL", iCount);
		chartSeries.set("ORGANIZATION", oCount);
		grandTotal = (Integer) (iCount) + (Integer) (oCount);
		barModel.addSeries(chartSeries);
	}

	public void createCustomerByProductBarChart() {
		grandTotal = 0;
		barModel = new CartesianChartModel();
		customerReport = customerReportService.findActiveCustomerByProduct();
		Map<String, Number> pMap = customerReport.getPieChartMap();
		entries = new ArrayList<Entry<String, Number>>(pMap.entrySet());
		ChartSeries iChartSeries = new ChartSeries();
		ChartSeries oChartSeries = new ChartSeries();
		Number imCount = customerReport.getPieChartMap().get("M_INDIVIDUAL");
		Number omCount = customerReport.getPieChartMap().get("M_ORGANIZATION");
		Number ifCount = customerReport.getPieChartMap().get("F_INDIVIDUAL");
		Number ofCount = customerReport.getPieChartMap().get("F_ORGANIZATION");
		Number ilCount = customerReport.getPieChartMap().get("L_INDIVIDUAL");
		Number olCount = customerReport.getPieChartMap().get("L_ORGANIZATION");
		iChartSeries.setLabel("INDIVIDUAL");
		iChartSeries.set("MOTOR", imCount);
		iChartSeries.set("FIRE", ifCount);
		iChartSeries.set("LIFE", ilCount);
		oChartSeries.setLabel("ORGANIZATION");
		oChartSeries.set("MOTOR", omCount);
		oChartSeries.set("FIRE", ofCount);
		oChartSeries.set("LIFE", olCount);
		grandTotal = (Integer) (imCount) + (Integer) (ifCount) + (Integer) (ilCount) + (Integer) (omCount) + (Integer) (ofCount) + (Integer) (olCount);
		barModel.addSeries(iChartSeries);
		barModel.addSeries(oChartSeries);
	}

	public void search() {
		if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.ACTIVE_POLICY.toString())) {
			createCustomerByActivePoliciesBarChart();
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.GENDER.toString())) {
			createCustomerByGenderBarChart();
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.TYPE.toString())) {
			createCustomerByTypeBarChart();
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.PRODUCT_TYPE.toString())) {
			createCustomerByProductBarChart();
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.LOCATION.toString())) {
			createCustomerByLocation();
		}
	}

	public enum CustomerOverview {
		ACTIVE_POLICY("Active Policy"), PRODUCT_TYPE("Product Type"), GENDER("Gender"), TYPE("Type"), LOCATION("Location");
		private String label;

		private CustomerOverview(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public CustomerOverview[] getCustomerOverviewList() {
		return CustomerOverview.values();
	}

	public CustomerReport getCustomerReport() {
		return customerReport;
	}

	public void setCustomerReport(CustomerReport customerReport) {
		this.customerReport = customerReport;
	}

	public String getSelectedCustomerOverview() {
		return selectedCustomerOverview;
	}

	public void setSelectedCustomerOverview(String selectedCustomerOverview) {
		this.selectedCustomerOverview = selectedCustomerOverview;
	}

	public CartesianChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(CartesianChartModel barModel) {
		this.barModel = barModel;
	}

	public List<Entry<String, Number>> getEntries() {
		return entries;
	}

	public int getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getDatatipFormat() {
		return "<span style=\"display:none;\">%s</span><span>%s</span>";
	}

	public static Map<String, Number> sortByKey(Map<String, Number> map) {
		List<Map.Entry<String, Number>> list = new LinkedList<Map.Entry<String, Number>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Number>>() {
			public int compare(Map.Entry<String, Number> o1, Map.Entry<String, Number> o2) {
				String a = o1.getKey();
				String b = o2.getKey();
				int l1 = Integer.parseInt(a);
				int l2 = Integer.parseInt(b);
				if (l1 < l2) {
					return -1;
				} else if (l1 > l2) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		Map<String, Number> result = new LinkedHashMap<String, Number>();
		for (Map.Entry<String, Number> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public String getDataTableTitle() {
		if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.ACTIVE_POLICY.toString())) {
			return "Active Policy";
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.GENDER.toString())) {
			return "Gender";
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.TYPE.toString())) {
			return "Type";
		} else if (selectedCustomerOverview.equalsIgnoreCase(CustomerOverview.PRODUCT_TYPE.toString())) {
			return "Product Type";
		} else {
			return "Location";
		}

	}
}
