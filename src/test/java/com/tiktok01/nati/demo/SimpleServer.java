package com.tiktok01.nati.demo;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;

import com.tiktok01.nati.core.handler.ThriftHandler;
import com.tiktok01.nati.server.NatiServer;
import com.tiktok01.nati.server.initializer.NatiInitializer;

/**
 * 简单的server
 * @author Kerwin
 *
 */
public class SimpleServer {
	
	public static void main(String[] args) {
		SimpleTestService.Iface simpleService = new SimpleTestServiceImpl();
		SimpleTestService.Processor<SimpleTestService.Iface> processor = new SimpleTestService.Processor<SimpleTestService.Iface>(simpleService);
		//ThriftHandler handler = new ThriftHandler(processor, TBinaryProtocol.class);
		ThriftHandler handler = new ThriftHandler(processor, TCompactProtocol.class);
		NatiInitializer initializer = new NatiInitializer(handler);
		NatiServer natiServer = new NatiServer(1234, initializer);
		natiServer.start();
	}
	
	public static class SimpleTestServiceImpl implements SimpleTestService.Iface {
		@Override
		public String hello(String name) throws TException {
			return "Hello, " + name;
		}
	}
	
}
