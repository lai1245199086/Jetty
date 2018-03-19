package com.cll.current.limit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract class AbstractSpringMonitor implements MethodInterceptor, MonitorHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		boolean result = before();
		if (result) {
			try {
				method.proceed();
			} catch (Exception e) {

			} finally {
				after();
			}
		}
		return null;
	}
}
