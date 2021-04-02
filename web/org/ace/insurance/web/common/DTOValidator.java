package org.ace.insurance.web.common;


public interface DTOValidator<T> {
	public ValidationResult validate(T dto);
}
