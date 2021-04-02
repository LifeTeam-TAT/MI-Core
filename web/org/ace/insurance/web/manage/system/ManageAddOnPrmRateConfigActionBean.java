package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.KeyFactorType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.product.ProductPremiumRateKeyFactor;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageAddOnPrmRateConfigActionBean")
public class ManageAddOnPrmRateConfigActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AddOnService}")
	private IAddOnService addOnService;

	public void setAddOnService(IAddOnService addOnService) {
		this.addOnService = addOnService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private AddOn addOn;
	private String rateAddOnId;
	private List<ProductPremiumRateKeyFactor> productPrmRateKfList;
	private List<ProductPremiumRate> rateList;
	private ProductPremiumRate productPrmRate;
	private boolean createNew;
	private List<RowColumn> rowColumns;
	private DecimalFormat decimalFormat;

	@PostConstruct
	public void init() {
		initializeInjection();
		loadDataFromDB();
	}

	private void initializeInjection() {
		if (isExistParam("addOnParam")) {
			this.addOn = ((this.addOn == null) ? (AddOn) getParam("addOnParam") : addOn);
			rateAddOnId = addOn.getId();
		}
		decimalFormat = new DecimalFormat("###,###.00");
	}

	private void loadDataFromDB() {
		if (rateAddOnId != null) {
			rateList = productService.findProductPremiumRateByAddOnId(rateAddOnId);
			// Collections.sort(rateList);
		}
		createNewProductPremiumRate();
		loadRowColumn();
	}

	public void createNewProductPremiumRate() {
		createNew = true;
		productPrmRate = new ProductPremiumRate();
		if (rateAddOnId != null) {
			AddOn addOn = addOnService.findAddOnById(rateAddOnId);
			productPrmRate.setAddon(addOn);
			if (addOn.isBaseOnKeyFactor() && !addOn.getKeyFactorList().isEmpty()) {
				createRateKeyFactorList(addOn.getKeyFactorList());
			}
		}
	}

	public void createRateKeyFactorList(List<KeyFactor> keyFactorList) {
		productPrmRateKfList = new ArrayList<ProductPremiumRateKeyFactor>();
		for (KeyFactor keyfactor : keyFactorList) {
			ProductPremiumRateKeyFactor rateKF = new ProductPremiumRateKeyFactor();
			rateKF.setKeyFactor(keyfactor);
			productPrmRateKfList.add(rateKF);
		}
	}

	public void addPremiumRate() {
		try {
			productPrmRate.setPremiumRateKeyFactor(productPrmRateKfList);
			productService.addNewProductPremiumRate(productPrmRate);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, productPrmRate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public List<KFRefValue> getKFRefValueList(KeyFactor keyFactor) {
		return productService.findReferenceValue(keyFactor.getValue(), null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EnumSet getKFEnumValueList(KeyFactor keyFactor) throws ClassNotFoundException {
		Class enumName = Class.forName("org.ace.insurance.common." + keyFactor.getValue());
		return EnumSet.allOf(enumName);

	}

	public void prepareEditRate(ProductPremiumRate premiumRate) {
		createNew = false;
		productPrmRate = premiumRate;
		productPrmRateKfList = productPrmRate.getPremiumRateKeyFactor();
		List<KeyFactor> ratekfList = productPrmRate.getKeyFactorList();
		List<KeyFactor> kfList = Utils.isNull(productPrmRate.getProduct()) ? productPrmRate.getAddon().getKeyFactorList() : productPrmRate.getProduct().getKeyFactorList();
		for (KeyFactor kf : kfList) {
			if (!ratekfList.contains(kf)) {
				ProductPremiumRateKeyFactor prkf = new ProductPremiumRateKeyFactor();
				prkf.setKeyFactor(kf);
				productPrmRateKfList.add(prkf);
			}
		}
	}

	public void updatePremiumRate() {
		try {
			productPrmRate.setPremiumRateKeyFactor(productPrmRateKfList);
			productService.updateProductPremiumRate(productPrmRate);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, productPrmRate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deletePremiumRate(ProductPremiumRate premiumRate) {
		try {
			productService.deleteProductPremiumRate(premiumRate);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, productPrmRate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	/*****************************
	 * Dynamic Column Group
	 **********************************/

	public class RowColumn implements Serializable {
		private static final long serialVersionUID = 1L;
		private List<Column> columns;

		public RowColumn() {
		}

		public List<Column> getColumns() {
			return columns;
		}

		public void setColumns(List<Column> columns) {
			this.columns = columns;
		}

	}

	public String getValue(ProductPremiumRate rate, ColumnValue columnValue) {
		String result = null;
		KFRefValue referenceName = null;
		for (ProductPremiumRateKeyFactor keyFactor : rate.getPremiumRateKeyFactor()) {
			if (keyFactor.getKeyFactor().equals(columnValue.getKeyFactor())) {
				switch (columnValue.getAttributeType()) {
					case FROM:
						result = decimalFormat.format(keyFactor.getFrom());
						break;
					case TO:
						result = decimalFormat.format(keyFactor.getTo());
						break;
					case REFERENCE:
						if (keyFactor.getValue() == null) {
							result = "";
						} else {
							referenceName = productService.findKFRefValueById(keyFactor.getKeyFactor().getValue(), keyFactor.getValue());
							result = referenceName.getName();
						}
						break;
					case ENUM:
						result = keyFactor.getValue();
						break;
					case VALUE:
						result = keyFactor.getValue();
						break;
				}
				break;
			}
		}

		return result;
	}

	public class Column implements Serializable {
		private static final long serialVersionUID = 1L;
		private int rowspan;
		private int colspan;
		private String name;

		public Column(int rowspan, int colspan, String name) {
			this.rowspan = rowspan;
			this.colspan = colspan;
			this.name = name;
		}

		public int getRowspan() {
			return rowspan;
		}

		public void setRowspan(int rowspan) {
			this.rowspan = rowspan;
		}

		public int getColspan() {
			return colspan;
		}

		public void setColspan(int colspan) {
			this.colspan = colspan;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public class ColumnValue implements Serializable {
		private static final long serialVersionUID = 1L;
		private KeyFactor keyFactor;
		private AttributeType attributeType;

		public ColumnValue(KeyFactor keyFactor, AttributeType attributeType) {
			this.keyFactor = keyFactor;
			this.attributeType = attributeType;
		}

		public KeyFactor getKeyFactor() {
			return keyFactor;
		}

		public void setKeyFactor(KeyFactor keyFactor) {
			this.keyFactor = keyFactor;
		}

		public AttributeType getAttributeType() {
			return attributeType;
		}

		public void setAttributeType(AttributeType attributeType) {
			this.attributeType = attributeType;
		}

	}

	public enum AttributeType implements Serializable {
		VALUE("Value"), REFERENCE("Reference"), ENUM("ENUM"), FROM("From"), TO("To");

		private String label;

		private AttributeType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

	public List<ColumnValue> columnsValues() {
		List<ColumnValue> values = new ArrayList<ColumnValue>();
		ColumnValue columnValue = null;
		List<KeyFactor> keyFactorList = new ArrayList<KeyFactor>();
		if (rateAddOnId != null) {
			keyFactorList = productPrmRate.getAddon().getKeyFactorList();
		}
		for (KeyFactor keyFactor : keyFactorList) {
			switch (keyFactor.getKeyFactorType()) {
				case FIXED:
					columnValue = new ColumnValue(keyFactor, AttributeType.VALUE);
					values.add(columnValue);
					break;
				case REFERENCE:
					columnValue = new ColumnValue(keyFactor, AttributeType.REFERENCE);
					values.add(columnValue);
					break;
				case ENUM:
					columnValue = new ColumnValue(keyFactor, AttributeType.ENUM);
					values.add(columnValue);
					break;
				case FROM_TO:
					columnValue = new ColumnValue(keyFactor, AttributeType.FROM);
					values.add(columnValue);
					columnValue = new ColumnValue(keyFactor, AttributeType.TO);
					values.add(columnValue);
					break;
				default:
					break;
			}
		}

		return values;
	}

	private void loadRowColumn() {
		rowColumns = new ArrayList<RowColumn>();
		List<KeyFactor> keyFactorList = new ArrayList<KeyFactor>();
		// if (rateProductId != null) {
		// keyFactorList = rate.getProduct().getKeyFactorList();
		// } else
		if (rateAddOnId != null) {
			keyFactorList = productPrmRate.getAddon().getKeyFactorList();
		}

		boolean haveFromTo = iscontainFromTo(keyFactorList);
		List<Column> columns = new ArrayList<Column>();
		Column column = null;
		RowColumn rowColumn = new RowColumn();
		columns.add(new Column(haveFromTo ? 2 : 1, 0, "No."));
		columns.add(new Column(haveFromTo ? 2 : 1, 0, "PREMIUM RATE"));
		for (KeyFactor keyFactor : keyFactorList) {
			if (haveFromTo) {
				if (!keyFactor.getKeyFactorType().equals(KeyFactorType.FROM_TO)) {
					if (keyFactor.getKeyFactorType().equals(KeyFactorType.ENUM)) {
						column = new Column(2, 1, keyFactor.getEnumValue().toUpperCase());
					} else {
						column = new Column(2, 1, keyFactor.getValue().toUpperCase());
					}
				} else {
					column = new Column(1, 2, keyFactor.getValue().toUpperCase());
				}
			} else {
				if (keyFactor.getKeyFactorType().equals(KeyFactorType.ENUM)) {
					column = new Column(1, 1, keyFactor.getEnumValue().toUpperCase());
				} else {
					column = new Column(1, 1, keyFactor.getValue().toUpperCase());
				}
			}

			columns.add(column);
		}
		columns.add(new Column(haveFromTo ? 2 : 1, 0, "UPDATE"));
		columns.add(new Column(haveFromTo ? 2 : 1, 0, "DELETE"));

		rowColumn.setColumns(columns);
		rowColumns.add(rowColumn);

		rowColumn = new RowColumn();
		columns = new ArrayList<Column>();
		if (haveFromTo) {
			// if (rateProductId != null) {
			// keyFactorList = rate.getProduct().getKeyFactorList();
			// } else
			if (rateAddOnId != null) {
				keyFactorList = productPrmRate.getAddon().getKeyFactorList();
			}
			for (KeyFactor keyFactor : keyFactorList) {
				if (keyFactor.getKeyFactorType().equals(KeyFactorType.FROM_TO)) {
					column = new Column(0, 0, "FROM");
					columns.add(column);
					column = new Column(0, 0, "TO");
					columns.add(column);
				}
			}
		}

		rowColumn.setColumns(columns);
		rowColumns.add(rowColumn);

	}

	private boolean iscontainFromTo(List<KeyFactor> keyFactors) {
		boolean result = false;
		for (KeyFactor keyFactor : keyFactors) {
			if (keyFactor.getKeyFactorType().equals(KeyFactorType.FROM_TO)) {
				return true;
			}
		}
		return result;
	}

	public List<ProductPremiumRateKeyFactor> getProductPrmRateKfList() {
		return productPrmRateKfList;
	}

	public void setProductPrmRateKfList(List<ProductPremiumRateKeyFactor> productPrmRateKfList) {
		this.productPrmRateKfList = productPrmRateKfList;
	}

	public List<RowColumn> getRowColumns() {
		return rowColumns;
	}

	public void setRowColumns(List<RowColumn> rowColumns) {
		this.rowColumns = rowColumns;
	}

	public String getRateAddOnId() {
		return rateAddOnId;
	}

	public List<ProductPremiumRate> getRateList() {
		return rateList;
	}

	public ProductPremiumRate getProductPrmRate() {
		return productPrmRate;
	}

	public boolean isCreateNew() {
		return createNew;
	}

}
