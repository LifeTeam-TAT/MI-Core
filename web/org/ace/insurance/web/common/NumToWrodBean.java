package org.ace.insurance.web.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.web.common.ntw.eng.AbstractProcessor;
import org.ace.insurance.web.common.ntw.eng.DefaultProcessor;
import org.ace.insurance.web.common.ntw.mym.AbstractMynNumConvertor;
import org.ace.insurance.web.common.ntw.mym.DefaultConvertor;

@ViewScoped
@ManagedBean(name = "NumToWrodBean")
public class NumToWrodBean {
	public String getMymWord(double number) {
		AbstractMynNumConvertor convertor = new DefaultConvertor();
		return convertor.getName(number);
	}

	public String getEngWord(double number) {
		AbstractProcessor processor = new DefaultProcessor();
		return processor.getName(number);
	}
}
