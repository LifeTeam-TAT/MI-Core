package org.ace.insurance.system.common.bank;

import java.io.Serializable;

public class COA001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String acode;
	private String acName;

	public COA001(String id, String acode, String acName) {
		this.id = id;
		this.acode = acode;
		this.acName = acName;
	}

	public String getId() {
		return id;
	}

	public String getAcode() {
		return acode;
	}

	public String getAcName() {
		return acName;
	}

}
