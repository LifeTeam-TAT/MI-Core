package org.ace.insurance.product;

import org.ace.insurance.common.InsuranceType;

public class KeyFactorConfig {
	private String id;
	private String entity;
	private String keyColumn;
	private String displayColumn;
	private String condition;

	public KeyFactorConfig(String id, String entity, String keyColumn, String displayColumn, String condition) {
		this.id = id;
		this.entity = entity;
		this.keyColumn = keyColumn;
		this.displayColumn = displayColumn;
		this.condition = condition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public String getDisplayColumn() {
		return displayColumn;
	}

	public void setDisplayColumn(String displayColumn) {
		this.displayColumn = displayColumn;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getQuery(InsuranceType insuranceType) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT NEW " + KFRefValue.class.getName() + "(var." + keyColumn + ", var." + displayColumn + ") FROM " + entity + " var ");
		if (insuranceType != null) {
			buffer.append("WHERE " + condition);
		}
		return buffer.toString();
	}

	public String getQuery(String entityId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT NEW " + KFRefValue.class.getName() + "(TRIM(var." + keyColumn + "), TRIM(var." + displayColumn + ")) FROM " + entity + " var ");
		buffer.append("WHERE var." + keyColumn + " = '" + entityId + "'");
		return buffer.toString();
	}
}
