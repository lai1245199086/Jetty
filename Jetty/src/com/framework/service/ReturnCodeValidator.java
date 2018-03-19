package com.framework.service;

import com.framework.exception.ValidationException;

public interface ReturnCodeValidator {
	public abstract void check(Object object) throws ValidationException;

}
