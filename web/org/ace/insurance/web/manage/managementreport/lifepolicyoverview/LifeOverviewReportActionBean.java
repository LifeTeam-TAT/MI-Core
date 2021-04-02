package org.ace.insurance.web.manage.managementreport.lifepolicyoverview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.managementreport.lifepolicyreport.LifeProductOverview;
import org.ace.insurance.managementreport.lifepolicyreport.service.interfaces.ILifeProductOverviewService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ViewScoped
@ManagedBean(name = "LifeOverviewReportActionBean")
public class LifeOverviewReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProductOverviewService}")
	private ILifeProductOverviewService lifeProductOverviewService;

	public void setLifeProductOverviewService(ILifeProductOverviewService lifeProductOverviewService) {
		this.lifeProductOverviewService = lifeProductOverviewService;
	}

	private LifeProductOverview lifeProductOverview;
	private CartesianChartModel categoryModel;
	private String selectedPolicyOverview;
	private SortedMap<String, Number> sortMap;
	private ValueComparator bvc;
	private List<Entry<String, Number>> entries;
	private double grandTotal;
	private int selectedYear;

	@PostConstruct
	public void init() {
		selectedYear = Calendar.getInstance().get(Calendar.YEAR);
		setSelectedPolicyOverview(PolicyOverview.PRODUCT_TYPE.toString());
		search();
	}

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public PolicyOverview[] getPolicyOverviewList() {
		return PolicyOverview.values();
	}

	public String getSelectedPolicyOverview() {
		return selectedPolicyOverview;
	}

	public void setSelectedPolicyOverview(String selectedPolicyOverview) {
		this.selectedPolicyOverview = selectedPolicyOverview;
	}

	public void search() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.TOWNSHIP.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByTownship();
			bvc = new ValueComparator(lifeProductOverview.getPieChartMap());
			sortMap = new TreeMap<String, Number>(bvc);
			sortMap.putAll(lifeProductOverview.getPieChartMap());
			entries = new ArrayList<Entry<String, Number>>(sortMap.entrySet());
			ChartSeries series = new ChartSeries();
			Iterator iterator = sortMap.entrySet().iterator();
			int count = 1;
			grandTotal = 0.0;
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				if (count < 11) {
					series.set(entry.getKey(), (Number) entry.getValue());
				}
				grandTotal += Double.parseDouble(entry.getValue().toString());
				count++;
			}
			series.setLabel("Total");
			categoryModel = new CartesianChartModel();
			categoryModel.addSeries(series);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.GENDER.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByGender();
			prepareSearch(lifeProductOverview);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.PRODUCT_TYPE.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByProductType();
			prepareSearch(lifeProductOverview);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.CHANNEL.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByChannel();
			prepareSearch(lifeProductOverview);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.MONTH.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByMonth(selectedYear);
			entries = new ArrayList<Entry<String, Number>>(sortByKey(lifeProductOverview.getPieChartMap()).entrySet());
			grandTotal = 0.0;
			ChartSeries series = new ChartSeries();
			Iterator iterator = sortByKey(lifeProductOverview.getPieChartMap()).entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				series.set(entry.getKey(), (Number) entry.getValue());
				grandTotal += Double.parseDouble(entry.getValue().toString());
			}

			series.setLabel("Total");
			categoryModel = new CartesianChartModel();
			categoryModel.addSeries(series);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.PAYMENTTYPE.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByPaymentType();
			prepareSearch(lifeProductOverview);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.AGE.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyByAge();
			sortMap = new TreeMap<String, Number>();
			ChartSeries series = new ChartSeries();
			grandTotal = 0.0;

			for (int i = 10; i < 66; i += 10) {
				int x = i;
				int y = i + 10;
				String key = x + "_" + y;
				String k = x + " - " + y;
				series.set(k, lifeProductOverview.getPieChartMap().get(key));
				grandTotal += Double.parseDouble(lifeProductOverview.getPieChartMap().get(key).toString());
				sortMap.put(k, lifeProductOverview.getPieChartMap().get(key));
			}
			entries = new ArrayList<Entry<String, Number>>(sortMap.entrySet());
			series.setLabel("Total");
			categoryModel = new CartesianChartModel();
			categoryModel.addSeries(series);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SI_BY_AGE.toString())) {
			lifeProductOverview = lifeProductOverviewService.findLifePolicyBySIAge();
			// entries = new ArrayList<Entry<String,
			// Number>>(lifeProductOverview.getPieChartMap().entrySet());
			grandTotal = 0.0;
			ChartSeries series = new ChartSeries();
			sortMap = new TreeMap<String, Number>();

			/*
			 * Iterator iterator =
			 * lifeProductOverview.getPieChartMap().entrySet().iterator();
			 * 
			 * while (iterator.hasNext()) { Map.Entry entry = (Map.Entry)
			 * iterator.next(); series.set(entry.getKey(),
			 * (Number)entry.getValue()); grandTotal +=
			 * Double.parseDouble(entry.getValue().toString()); }
			 */

			for (int i = 10; i < 66; i++) {
				String key = i + " years";
				if (lifeProductOverview.getPieChartMap().get(key) != null) {
					series.set(i + " years", lifeProductOverview.getPieChartMap().get(key));
					grandTotal += Double.parseDouble(lifeProductOverview.getPieChartMap().get(key).toString());
					sortMap.put(i + " years", lifeProductOverview.getPieChartMap().get(key));
				}
			}
			entries = new ArrayList<Entry<String, Number>>(sortMap.entrySet());
			series.setLabel("Total");
			categoryModel = new CartesianChartModel();
			categoryModel.addSeries(series);
		}

	}

	public void prepareSearch(LifeProductOverview lifeProductOverview) {
		entries = new ArrayList<Entry<String, Number>>(lifeProductOverview.getPieChartMap().entrySet());
		grandTotal = 0.0;
		ChartSeries series = new ChartSeries();
		Iterator iterator = lifeProductOverview.getPieChartMap().entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			series.set(entry.getKey(), (Number) entry.getValue());
			grandTotal += Double.parseDouble(entry.getValue().toString());
		}

		series.setLabel("Total");
		categoryModel = new CartesianChartModel();
		categoryModel.addSeries(series);
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public List<Entry<String, Number>> getEntries() {
		return entries;
	}

	public Map<String, Number> getSortMap() {
		return sortMap == null ? new TreeMap<String, Number>() : sortMap;
	}

	class ValueComparator implements Comparator<String> {
		Map<String, Number> base;

		public ValueComparator(Map<String, Number> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with
		// equals.
		public int compare(String a, String b) {
			if (base.get(a).intValue() >= base.get(b).intValue()) {
				return -1;
			} else {
				return 1;
			} // returning 0 would merge keys
		}
	}

	public enum PolicyOverview {
		PRODUCT_TYPE("Product Type"), MONTH("Month"), TOWNSHIP("Township"), PAYMENTTYPE("Payment Type"), CHANNEL("Channel"), AGE("Age"), SI_BY_AGE("SI By Age"), GENDER("Gender");
		private String label;

		private PolicyOverview(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public String getBarChartWidth() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.TOWNSHIP.toString()) || selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.AGE.toString())
				|| selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SI_BY_AGE.toString())) {
			return "width:1000px;height:400px;";
		} else {
			return "height:400px;";
		}
	}

	public String pattern() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SI_BY_AGE.toString())) {
			return "##,###.00";
		} else {
			return "";
		}
	}

	public String datableTitle() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SI_BY_AGE.toString())) {
			return "SumInsured";
		} else {
			return "Number Of Policies";
		}
	}

	public String datatableTemplate() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.TOWNSHIP.toString()) || selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SI_BY_AGE.toString())) {
			return "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";

		} else {
			return "";
		}
	}

	public String getDatatipFormat() {
		return "<span style=\"display:none;\">%s</span><span>%s</span>";
	}

	public static Map<String, Number> sortByKey(Map<String, Number> map) {
		List<Map.Entry<String, Number>> list = new LinkedList<Map.Entry<String, Number>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Number>>() {
			public int compare(Map.Entry<String, Number> o1, Map.Entry<String, Number> o2) {
				String a = o1.getKey().substring(5, o1.getKey().length());
				String b = o2.getKey().substring(5, o2.getKey().length());
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

	public String getRowSize() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.MONTH.toString())) {
			return "12";
		} else {
			return "10";
		}
	}

	public boolean isRenderYear() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.MONTH.toString())) {
			return true;
		} else {
			return false;
		}
	}

	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1999; startYear <= endYear; startYear++) {
			years.add(startYear);
		}
		Collections.reverse(years);
		return years;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getExtender() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.MONTH.toString())) {
			return "my_ext1";
		} else {
			return "my_ext";
		}
	}
}
