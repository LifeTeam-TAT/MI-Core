/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.product;

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
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.product.ProductPremiumRateKeyFactor;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.addon.service.interfaces.IAddOnService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageProductPrmRateConfigActionBean")
public class ManageProductPrmRateConfigActionBean extends BaseBean implements Serializable {
	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	/**
	 * @param productService
	 *            the productService to set
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{AddOnService}")
	private IAddOnService addOnService;

	/**
	 * @param addOnService
	 *            the addOnService to set
	 */
	public void setAddOnService(IAddOnService addOnService) {
		this.addOnService = addOnService;
	}

	private ProductPremiumRate rate;
	private String productId;
	private String rateProductId;
	private String rateAddOnId;
	private List<ProductPremiumRateKeyFactor> rateKfList;
	private List<ProductPremiumRate> rateList;
	private boolean isNew;
	private List<RowColumn> rowColumns;
	private DecimalFormat decimalFormat;

	@PostConstruct
	public void init() {
		rateKfList = new ArrayList<ProductPremiumRateKeyFactor>();
		initializeInjection();
		loadDataFromDB();
		clearSessionOfThisPage();
	}

	private void initializeInjection() {
		productId = (String) getParam(Constants.PRODUCT_ID);
		if (isExistParam(Constants.RATE_PRODUCT_ID)) {
			rateProductId = (String) getParam(Constants.RATE_PRODUCT_ID);
		} else {
			rateAddOnId = (String) getParam(Constants.ADDON_ID);
		}
		decimalFormat = new DecimalFormat("###,###.00");
	}

	private void loadDataFromDB() {
		if (rateAddOnId != null) {
			rateList = productService.findProductPremiumRateByAddOnId(rateAddOnId);
		} else {
			rateList = productService.findProductPremiumRateByProduct(rateProductId);
		}
		createNewProductPremiumRate();
		loadRowColumn();
	}

	private void clearSessionOfThisPage() {
		removeParam(Constants.ADDON_ID);
		removeParam(Constants.PRODUCT_ID);
		removeParam(Constants.RATE_PRODUCT_ID);
	}

	public void createNewProductPremiumRate() {
		isNew = true;
		rate = new ProductPremiumRate();
		if (rateProductId != null) {
			Product product = productService.findProductById(rateProductId);
			rate.setProduct(product);
			if (product.isBaseOnKeyFactor() && !product.getKeyFactorList().isEmpty()) {
				createRateKeyFactorList(product.getKeyFactorList());
			}
		} else if (rateAddOnId != null) {
			AddOn addOn = addOnService.findAddOnById(rateAddOnId);
			rate.setAddon(addOn);
			if (addOn.isBaseOnKeyFactor() && !addOn.getKeyFactorList().isEmpty()) {
				createRateKeyFactorList(addOn.getKeyFactorList());
			}
		}
	}

	public void createRateKeyFactorList(List<KeyFactor> keyFactorList) {
		rateKfList = new ArrayList<ProductPremiumRateKeyFactor>();
		for (KeyFactor keyfactor : keyFactorList) {
			ProductPremiumRateKeyFactor rateKF = new ProductPremiumRateKeyFactor();
			rateKF.setKeyFactor(keyfactor);
			rateKfList.add(rateKF);
		}
	}

	public void prepareEditRate(ProductPremiumRate premiumRate) {
		isNew = false;
		rate = premiumRate;
		rateKfList = rate.getPremiumRateKeyFactor();
		List<KeyFactor> ratekfList = rate.getKeyFactorList();
		List<KeyFactor> kfList = Utils.isNull(rate.getProduct()) ? rate.getAddon().getKeyFactorList() : rate.getProduct().getKeyFactorList();
		for (KeyFactor kf : kfList) {
			if (!ratekfList.contains(kf)) {
				ProductPremiumRateKeyFactor prkf = new ProductPremiumRateKeyFactor();
				prkf.setKeyFactor(kf);
				rateKfList.add(prkf);
			}
		}
	}

	public void addPremiumRate() {
		try {
			rate.setPremiumRateKeyFactor(rateKfList);
			productService.addNewProductPremiumRate(rate);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, rate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void UpdatePremiumRate() {
		try {
			rate.setPremiumRateKeyFactor(rateKfList);
			productService.updateProductPremiumRate(rate);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, rate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deletePremiumRate(ProductPremiumRate premiumRate) {
		try {
			productService.deleteProductPremiumRate(premiumRate);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, rate.getPremiumRate());
			loadDataFromDB();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String backToPreviousPage() {
		if (rateProductId != null) {
			return "manageProduct";
		} else if (rateAddOnId != null) {
			putParam(Constants.PRODUCT_ID, productId);
			return "manageAddOn";
		} else {
			return "manageProduct";
		}
	}

	public List<KFRefValue> getKFRefValueList(KeyFactor keyFactor) {
		return productService.findReferenceValue(keyFactor.getValue(), null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EnumSet getKFEnumValueList(KeyFactor keyFactor) throws ClassNotFoundException {
		Class enumName = Class.forName("org.ace.insurance.common." + keyFactor.getValue());
		return EnumSet.allOf(enumName);
		// Class cls = Gender.class;
		// Class Gender = Class.forName("org.ace.insurance.common.Gender");
		// return EnumSet.allOf(Gender);
	}

	/**
	 * @return the rate
	 */
	public ProductPremiumRate getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(ProductPremiumRate rate) {
		this.rate = rate;
	}

	/**
	 * @return the rateKfList
	 */
	public List<ProductPremiumRateKeyFactor> getRateKfList() {
		return rateKfList;
	}

	/**
	 * @param rateKfList
	 *            the rateKfList to set
	 */
	public void setRateKfList(List<ProductPremiumRateKeyFactor> rateKfList) {
		this.rateKfList = rateKfList;
	}

	/**
	 * @return the rateProductId
	 */
	public String getRateProductId() {
		return rateProductId;
	}

	/**
	 * @return the rateAddOnId
	 */
	public String getRateAddOnId() {
		return rateAddOnId;
	}

	/**
	 * @return the rateList
	 */
	public List<ProductPremiumRate> getRateList() {
		return rateList;
	}

	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * @return the rowColumns
	 */
	public List<RowColumn> getRowColumns() {
		return rowColumns;
	}

	/**
	 * @param rowColumns
	 *            the rowColumns to set
	 */
	public void setRowColumns(List<RowColumn> rowColumns) {
		this.rowColumns = rowColumns;
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

	/*****************************
	 * Dynamic Column Value
	 **********************************/
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
		VALUE("Value"), REFERENCE("Reference"), ENUM("ENUM"), FROM("From"), TO("To"), BOOLEAN("Boolean");

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
		if (rateProductId != null) {
			keyFactorList = rate.getProduct().getKeyFactorList();
		} else if (rateAddOnId != null) {
			keyFactorList = rate.getAddon().getKeyFactorList();
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
				case BOOLEAN:
					columnValue = new ColumnValue(keyFactor, AttributeType.BOOLEAN);
					values.add(columnValue);
					break;
				default:
					break;
			}
		}

		return values;
	}

	/*****************************
	 * Retried value for dynamic column
	 **********************************/

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
					case BOOLEAN:
						result = keyFactor.getValue();
						break;
					default:
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

	private void loadRowColumn() {
		rowColumns = new ArrayList<RowColumn>();
		List<KeyFactor> keyFactorList = new ArrayList<KeyFactor>();
		if (rateProductId != null) {
			keyFactorList = rate.getProduct().getKeyFactorList();
		} else if (rateAddOnId != null) {
			keyFactorList = rate.getAddon().getKeyFactorList();
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
			if (rateProductId != null) {
				keyFactorList = rate.getProduct().getKeyFactorList();
			} else if (rateAddOnId != null) {
				keyFactorList = rate.getAddon().getKeyFactorList();
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

}
