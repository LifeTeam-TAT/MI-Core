package org.ace.insurance.web.common;


public interface Validator<T> {
	public ValidationResult validate(T dto);
}
