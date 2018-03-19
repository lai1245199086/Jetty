package com.cll.current.limit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流的作用 实现一个缓冲队列，让一部分进入等待状态 区间监控 如果一个线程返回特别慢怎么办，
 * 比如在release之前抛了异常
 * 
 * @author 百恼 2013-04-11上午11:26:36
 * 
 */
public class FlowMonitor {

	// 默认最大的并发数默认为100，可以配置
	private int maxFlowSize = 100;
	// 最大并发数
	private int maxRunningSize = 0;

	// 当前并发数
	private AtomicInteger runningSize = new AtomicInteger();

	// 通过的数量
	private AtomicInteger passSize = new AtomicInteger();

	// 失败的数量
	private AtomicInteger loseSize = new AtomicInteger();

	public FlowMonitor() {
		super();
	}

	public FlowMonitor(int maxFlowSize) {
		this();
		this.maxFlowSize = maxFlowSize;
	}

	/**
	 * entry()是在调用目标方法之前调用
	 * 线程进入开关，即使这里用了一些Atomic类，这里仍然会有并发问题。
	 * 
	 * @return
	 */
	public boolean entry() {
		// 每个类中一个配置maxFlowSize
		if (maxFlowSize > 0) {
			if (maxFlowSize <= runningSize.get()) {
				// 已经超过最大限制
				loseSize.incrementAndGet();
				return false;
			}
			// 并发数+1
			runningSize.incrementAndGet();
			if (runningSize.get() > maxRunningSize) {
				// 记录最大的并发数，有并发问题
				maxRunningSize = runningSize.get();
			}
			// 记录通过的线程数
			passSize.incrementAndGet();
		}
		return true;
	}

	/**
	 * release()是在调用目标方法之后调用
	 * 执行完后，并发数-1
	 * 
	 * @param key
	 */
	public void release() {
		runningSize.decrementAndGet();
	}

	public AtomicInteger getRunningSize() {
		return runningSize;
	}

	public AtomicInteger getPassSize() {
		return passSize;
	}

	public AtomicInteger getLoseSize() {
		return loseSize;
	}

	public int getMaxRunningSize() {
		return maxRunningSize;
	}

	/**
	 * 重置,可以分时段进行监控
	 */
	public void reset() {
		passSize.set(0);
		loseSize.set(0);
		maxRunningSize = 0;
	}

}
