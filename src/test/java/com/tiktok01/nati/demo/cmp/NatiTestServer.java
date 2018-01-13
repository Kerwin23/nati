package com.tiktok01.nati.demo.cmp;

import org.apache.thrift.protocol.TBinaryProtocol;

import com.tiktok01.nati.core.handler.ThriftHandler;
import com.tiktok01.nati.demo.SimpleServer.SimpleTestServiceImpl;
import com.tiktok01.nati.demo.SimpleTestService;
import com.tiktok01.nati.server.NatiServer;
import com.tiktok01.nati.server.initializer.NatiInitializer;

public class NatiTestServer {

	public static void main(String[] args) {
		SimpleTestService.Iface simpleService = new SimpleTestServiceImpl();
		SimpleTestService.Processor<SimpleTestService.Iface> processor = new SimpleTestService.Processor<SimpleTestService.Iface>(simpleService);
		ThriftHandler handler = new ThriftHandler(processor, TBinaryProtocol.class);
		NatiInitializer initializer = new NatiInitializer(handler);
		NatiServer natiServer = new NatiServer(1234, initializer);
		natiServer.start();
	}
}
