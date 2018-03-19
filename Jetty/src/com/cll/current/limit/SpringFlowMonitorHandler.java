package com.cll.current.limit;

/**
 * 使用spring拦截器实现监控
 * 
 * @author 百恼 2013-4-9下午06:36:24
 */
public class SpringFlowMonitorHandler extends AbstractSpringMonitor {

	private FlowMonitor flowMonitor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuzhipeng.monitor.MonitorHandler#before()
	 */
	@Override
	public boolean before() {
		if (!flowMonitor.entry()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuzhipeng.monitor.MonitorHandler#after()
	 */
	@Override
	public boolean after() {
		flowMonitor.release();
		return true;
	}

	public FlowMonitor getFlowMonitor() {
		return flowMonitor;
	}

	public void setFlowMonitor(FlowMonitor flowMonitor) {
		this.flowMonitor = flowMonitor;
	}
}
