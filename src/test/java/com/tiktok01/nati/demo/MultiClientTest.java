package com.tiktok01.nati.demo;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.tiktok01.nati.demo.MultiServer.MultiHelloServiceImpl;
import com.tiktok01.nati.demo.MultiServer.MultiSayServiceImpl;

import junit.framework.TestCase;

/**
 * 测试类
 * @author Kerwin
 *
 */
public class MultiClientTest extends TestCase {

	public void testClient() throws Exception {
		TSocket socket = new TSocket("localhost", 1234);
		try {
			socket.open();
			TCompactProtocol protocol = new TCompactProtocol(socket);
			TMultiplexedProtocol helloPotocol = new TMultiplexedProtocol(protocol, MultiHelloServiceImpl.SERVICE_NAME);
			MultiHelloService.Client helloClient = new MultiHelloService.Client(helloPotocol);
			String result = helloClient.hello("world");
			assertEquals(result, "Hello, world");
			TMultiplexedProtocol sayProtocol = new TMultiplexedProtocol(protocol, MultiSayServiceImpl.SERVICE_NAME);
			MultiSayService.Client sayClient = new MultiSayService.Client(sayProtocol);
			result = sayClient.say("Tom", "hello");
			assertEquals(result, "Tom say: hello");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			socket.close();
		}
	}
}
