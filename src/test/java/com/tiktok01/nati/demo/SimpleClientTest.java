package com.tiktok01.nati.demo;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TSocket;

import junit.framework.TestCase;

/**
 * 简单的客户端
 * 
 * @author Kerwin
 *
 */
public class SimpleClientTest extends TestCase {

	public void testClient() throws Exception {
		TSocket socket = new TSocket("localhost", 1234);
		try {
			socket.open();
			TCompactProtocol protocol = new TCompactProtocol(socket);
			SimpleTestService.Client client = new SimpleTestService.Client(protocol);
			String result = client.hello("world");
			assertEquals(result, "Hello, world");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			socket.close();
		}
	}
}
