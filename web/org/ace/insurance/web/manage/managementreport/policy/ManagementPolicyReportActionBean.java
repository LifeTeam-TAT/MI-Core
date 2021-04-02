package org.ace.insurance.web.manage.managementreport.policy;

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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.managementreport.policy.ActivePolicies;
import org.ace.insurance.managementreport.policy.service.interfaces.IActivePoliciesService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ViewScoped
@ManagedBean(name = "ManagementPolicyReportActionBean")
public class ManagementPolicyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ActivePoliciesService}")
	private IActivePoliciesService activePoliciesService;

	public void setActivePoliciesService(IActivePoliciesService activePoliciesService) {
		this.activePoliciesService = activePoliciesService;
	}

	private ActivePolicies activePolicies;
	private PieChartModel pieModel;
	private CartesianChartModel categoryModel;
	private CartesianChartModel lifeCategoryModel;
	private CartesianChartModel motorCategoryModel;
	private CartesianChartModel fireCategoryModel;
	private String selectedPolicyOverview;
	Number motorCount;
	Number fireCount;
	Number lifeCount;
	private List<Entry<String, Number>> entries;
	private List<Entry<String, Number>> motorEntries;
	private List<Entry<String, Number>> fireEntries;
	private double lifeGrandTotal;
	private double fireGrandTotal;
	private double motorGrandTotal;
	private int selectedYear;

	private SortedMap<String, Number> sortMap;

	@PostConstruct
	public void init() {
		selectedYear = Calendar.getInstance().get(Calendar.YEAR);
		setSelectedPolicyOverview(PolicyOverview.ACTIVEPOLICY.toString());
		search();
	}

	public ActivePolicies getActivePolicies() {
		return activePolicies;
	}

	public void setActivePolicies(ActivePolicies activePolicies) {
		this.activePolicies = activePolicies;
	}

	public PieChartModel getPieModel() {
		return pieModel;
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

	public enum PolicyOverview {
		ACTIVEPOLICY("ActivePolicy"), SUMINSURED("SumInsured"), PREMIUM("Premium"), TIMELINE("Timeline");
		private String label;

		private PolicyOverview(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public void search() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.SUMINSURED.toString())) {
			activePolicies = activePoliciesService.findTotalSumInsuredByProducts();
			pieModel = new PieChartModel();
			categoryModel = new CartesianChartModel();
			pieModel.setData(activePolicies.getPieChartMap());
			ChartSeries series = new ChartSeries();
			motorCount = activePolicies.getPieChartMap().get("Motor");
			fireCount = activePolicies.getPieChartMap().get("Fire");
			lifeCount = activePolicies.getPieChartMap().get("Life");
			series.setLabel("Total");
			series.set("Total Motor SumInsured ", motorCount);
			series.set("Total Fire SumInsured ", fireCount);
			series.set("Total Life SumInsured ", lifeCount);
			categoryModel.addSeries(series);

		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.PREMIUM.toString())) {
			activePolicies = activePoliciesService.findTotalPremiumByProducts();
			pieModel = new PieChartModel();
			categoryModel = new CartesianChartModel();
			pieModel.setData(activePolicies.getPieChartMap());
			ChartSeries series = new ChartSeries();
			motorCount = activePolicies.getPieChartMap().get("Motor");
			fireCount = activePolicies.getPieChartMap().get("Fire");
			lifeCount = activePolicies.getPieChartMap().get("Life");
			series.setLabel("Total");
			series.set("Total Motor Premium ", motorCount);
			series.set("Total Fire Premium ", fireCount);
			series.set("Total Life Premium ", lifeCount);
			categoryModel.addSeries(series);

		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.ACTIVEPOLICY.toString())) {
			activePolicies = activePoliciesService.findActivePoliciesByProducts();
			pieModel = new PieChartModel();
			categoryModel = new CartesianChartModel();
			pieModel.setData(activePolicies.getPieChartMap());
			ChartSeries series = new ChartSeries();
			motorCount = activePolicies.getPieChartMap().get("Total_Motor_Policies");
			fireCount = activePolicies.getPieChartMap().get("Total_Fire_Policies");
			lifeCount = activePolicies.getPieChartMap().get("Total_Life_Policies");
			series.setLabel("Total");
			series.set("Total Motor Policies ", motorCount);
			series.set("Total Fire Policies ", fireCount);
			series.set("Total Life Policies", lifeCount);
			categoryModel.addSeries(series);
		} else if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.TIMELINE.toString())) {
			// Life
			activePolicies = activePoliciesService.findLifePolicyByTimeLine(selectedYear);
			entries = new ArrayList<Entry<String, Number>>(sortByKey(activePolicies.getPieChartMap()).entrySet());
			ChartSeries series = new ChartSeries();
			Iterator iterator = sortByKey(activePolicies.getPieChartMap()).entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				series.set(entry.getKey(), (Number) entry.getValue());
				lifeGrandTotal += Double.parseDouble(entry.getValue().toString());
			}
			series.setLabel("Total");
			lifeCategoryModel = new CartesianChartModel();
			lifeCategoryModel.addSeries(series);

			// Fire
//to FIXME by THK
			activePolicies = new ActivePolicies();
			activePolicies = activePoliciesService.findFirePolicyByTimeLine(selectedYear);
			fireEntries = new ArrayList<Entry<String, Number>>(sortByKey(activePolicies.getPieChartMap()).entrySet());
			series = new ChartSeries();
			Iterator iteratorF = sortByKey(activePolicies.getPieChartMap()).entrySet().iterator();
			while (iteratorF.hasNext()) {
				Map.Entry entry = (Map.Entry) iteratorF.next();
				series.set(entry.getKey(), (Number) entry.getValue());
				fireGrandTotal += Double.parseDouble(entry.getValue().toString());
			}
			series.setLabel("Total");
			fireCategoryModel = new CartesianChartModel();
			fireCategoryModel.addSeries(series);

			// Motor

			activePolicies = new ActivePolicies();
			activePolicies = activePoliciesService.findMotorPolicyByTimeLine(selectedYear);
			motorEntries = new ArrayList<Entry<String, Number>>(sortByKey(activePolicies.getPieChartMap()).entrySet());
			series = new ChartSeries();
			Iterator iteratorM = sortByKey(activePolicies.getPieChartMap()).entrySet().iterator();
			while (iteratorM.hasNext()) {
				Map.Entry entry = (Map.Entry) iteratorM.next();
				series.set(entry.getKey(), (Number) entry.getValue());
				motorGrandTotal += Double.parseDouble(entry.getValue().toString());
			}
			series.setLabel("Total");
			motorCategoryModel = new CartesianChartModel();
			motorCategoryModel.addSeries(series);
		}
	}

	public Number getMotorCount() {
		return motorCount;
	}

	public void setMotorCount(Number motorCount) {
		this.motorCount = motorCount;
	}

	public Number getFireCount() {
		return fireCount;
	}

	public void setFireCount(Number fireCount) {
		this.fireCount = fireCount;
	}

	public Number getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(Number lifeCount) {
		this.lifeCount = lifeCount;
	}

	public List<Entry<String, Number>> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry<String, Number>> entries) {
		this.entries = entries;
	}

	public double getLifeGrandTotal() {
		return lifeGrandTotal;
	}

	public void setLifeGrandTotal(double lifeGrandTotal) {
		this.lifeGrandTotal = lifeGrandTotal;
	}

	public CartesianChartModel getLifeCategoryModel() {
		return lifeCategoryModel;
	}

	public void setLifeCategoryModel(CartesianChartModel lifeCategoryModel) {
		this.lifeCategoryModel = lifeCategoryModel;
	}

	public CartesianChartModel getMotorCategoryModel() {
		return motorCategoryModel;
	}

	public void setMotorCategoryModel(CartesianChartModel motorCategoryModel) {
		this.motorCategoryModel = motorCategoryModel;
	}

	public CartesianChartModel getFireCategoryModel() {
		return fireCategoryModel;
	}

	public void setFireCategoryModel(CartesianChartModel fireCategoryModel) {
		this.fireCategoryModel = fireCategoryModel;
	}

	public double getFireGrandTotal() {
		return fireGrandTotal;
	}

	public void setFireGrandTotal(double fireGrandTotal) {
		this.fireGrandTotal = fireGrandTotal;
	}

	public double getMotorGrandTotal() {
		return motorGrandTotal;
	}

	public void setMotorGrandTotal(double motorGrandTotal) {
		this.motorGrandTotal = motorGrandTotal;
	}

	public List<Entry<String, Number>> getMotorEntries() {
		return motorEntries;
	}

	public void setMotorEntries(List<Entry<String, Number>> motorEntries) {
		this.motorEntries = motorEntries;
	}

	public List<Entry<String, Number>> getFireEntries() {
		return fireEntries;
	}

	public void setFireEntries(List<Entry<String, Number>> fireEntries) {
		this.fireEntries = fireEntries;
	}

	public boolean isChartRender() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.TIMELINE.toString())) {
			return true;
		} else {
			return false;
		}
	}

	public SortedMap<String, Number> getSortMap() {
		return sortMap;
	}

	public void setSortMap(SortedMap<String, Number> sortMap) {
		this.sortMap = sortMap;
	}

	public String formatData() {
		if (selectedPolicyOverview.equalsIgnoreCase(PolicyOverview.ACTIVEPOLICY.toString())) {
			return "";
		} else {
			return "##,###.00";
		}
	}

	public String getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR) + "/";
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

	public String getDatatipFormat() {
		return "<span style=\"display:none;\">%s</span><span>%s</span>";
	}

}
