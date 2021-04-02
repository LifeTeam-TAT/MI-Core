package org.ace.insurance.web.manage.report.timeline;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class PolicyInfo {

	private String policyId;
	private String text;
	private String tempId;
	private PolicyInfoType type;
	
	public PolicyInfo() {
		this.tempId = String.valueOf(System.nanoTime());
	}

	public PolicyInfo(String policyId, String text) {
		this.tempId = String.valueOf(System.nanoTime());
		this.policyId = policyId;
		this.text = text;
	}
	
	public PolicyInfo(String text, PolicyInfoType type) {
		this.tempId = String.valueOf(System.nanoTime());
		this.text = text;
		this.type = type;
	}
	
	public PolicyInfo(String policyId, String text, PolicyInfoType type) {
		this.policyId = policyId;
		this.text = text;
		this.type = type;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public PolicyInfoType getType() {
		return type;
	}

	public void setType(PolicyInfoType type) {
		this.type = type;
	}

	@Override  
    public boolean equals(Object o) {  
        if (this == o) {  
            return true;  
        }  
  
        if (o == null || getClass() != o.getClass()) {  
            return false;  
        }  
  
        PolicyInfo policyInfo = (PolicyInfo) o;  
  
        if (text != policyInfo.text) {  
            return false;  
        }  
  
        return true;  
    }  
	  
    @Override  
    public int hashCode() {  
    	return HashCodeBuilder.reflectionHashCode(this);
    }  
}
