package com.tiktok01.nati.demo.cmp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.tiktok01.nati.demo.SimpleTestService;

public class ThrfitTestClient {

	public static void main(String[] args) {
//		serialTest(1000000);
//		parallelThriftTest(10, 10000);
//		parallelThriftPoolTest(1000, 100);
		parallelNatiTest(10000, 100);
	}
	
	/**
	 * 串行测试方法
	 * @param times
	 */
	public final static void serialTest(int times) {
		String prefix = "test";
		TSocket socket = new TSocket("localhost", 1234);
		try {
			socket.open();
			TBinaryProtocol protocol = new TBinaryProtocol(socket);
			SimpleTestService.Client client = new SimpleTestService.Client(protocol);
			long startTime = System.currentTimeMillis();
			for(int i = 0; i < times; i++) {
				String name = prefix + i;
				String result = client.hello(name);
				System.out.println(result);
			}
			long wasteTime = System.currentTimeMillis() - startTime;
			System.out.println("====>>>waste time: " + wasteTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
	
	public final static void parallelThriftTest(final int threads, final int times) {
		ExecutorService threadPool = Executors.newFixedThreadPool(threads);
		final CyclicBarrier beginBarrier = new CyclicBarrier(threads);
		final CountDownLatch endLatch = new CountDownLatch(threads);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < threads; i ++) {
			threadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("====>>>" + Thread.currentThread().getName() + " wait");
						beginBarrier.await();
						String prefix = "test";
						TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 1234));
						try {
							TBinaryProtocol protocol = new TBinaryProtocol(transport);
							transport.open();
							SimpleTestService.Client client = new SimpleTestService.Client(protocol);
							long startTime = System.currentTimeMillis();
							for(int i = 0; i < times; i++) {
								String name = prefix + i;
								String result = client.hello(name);
								System.out.println(result);
							}
							long wasteTime = System.currentTimeMillis() - startTime;
							System.out.println("====>>>waste time: " + wasteTime + "ms");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							transport.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					endLatch.countDown();
				}
			});
		}
		try {
			endLatch.await();
			long wasteTime = System.currentTimeMillis() - startTime;
			System.out.println("====>>>total waste time: " + wasteTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final static void parallelThriftPoolTest(final int threads, final int times) {
		ExecutorService threadPool = Executors.newFixedThreadPool(threads);
		final CyclicBarrier beginBarrier = new CyclicBarrier(threads);
		final CountDownLatch endLatch = new CountDownLatch(threads);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < threads; i ++) {
			threadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("====>>>" + Thread.currentThread().getName() + " wait");
						beginBarrier.await();
						String prefix = "test";
						TTransport transport = new TSocket("localhost", 1234);
						try {
							TBinaryProtocol protocol = new TBinaryProtocol(transport);
							transport.open();
							SimpleTestService.Client client = new SimpleTestService.Client(protocol);
							long startTime = System.currentTimeMillis();
							for(int i = 0; i < times; i++) {
								String name = prefix + i;
								String result = client.hello(name);
								System.out.println(result);
							}
							long wasteTime = System.currentTimeMillis() - startTime;
							System.out.println("====>>>waste time: " + wasteTime + "ms");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							transport.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					endLatch.countDown();
				}
			});
		}
		try {
			endLatch.await();
			long wasteTime = System.currentTimeMillis() - startTime;
			System.out.println("====>>>total waste time: " + wasteTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final static void parallelNatiTest(final int threads, final int times) {
		ExecutorService threadPool = Executors.newFixedThreadPool(threads);
		final CyclicBarrier beginBarrier = new CyclicBarrier(threads);
		final CountDownLatch endLatch = new CountDownLatch(threads);
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < threads; i ++) {
			threadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("====>>>" + Thread.currentThread().getName() + " wait");
						beginBarrier.await();
						serialTest(times);
					} catch (Exception e) {
						e.printStackTrace();
					}
					endLatch.countDown();
				}
			});
		}
		try {
			endLatch.await();
			long wasteTime = System.currentTimeMillis() - startTime;
			System.out.println("====>>>total waste time: " + wasteTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
