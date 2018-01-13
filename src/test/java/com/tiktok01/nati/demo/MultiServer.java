package com.tiktok01.nati.demo;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;

import com.tiktok01.nati.core.handler.ThriftHandler;
import com.tiktok01.nati.server.NatiServer;
import com.tiktok01.nati.server.initializer.NatiInitializer;

public class MultiServer {

	public static void main(String[] args) {
		TMultiplexedProcessor processor = createMultiProcessor();
		ThriftHandler handler = new ThriftHandler(processor, TCompactProtocol.class);
		NatiInitializer initializer = new NatiInitializer(handler);
		NatiServer natiServer = new NatiServer(1234, initializer);
		natiServer.start();
	}
	
	private final static TMultiplexedProcessor createMultiProcessor() {
		TMultiplexedProcessor processor = new TMultiplexedProcessor();
		MultiHelloService.Iface helloService = new MultiHelloServiceImpl();
		MultiHelloService.Processor<MultiHelloService.Iface> helloProcessor = new MultiHelloService.Processor<MultiHelloService.Iface>(helloService);
		processor.registerProcessor(MultiHelloServiceImpl.SERVICE_NAME, helloProcessor);
		MultiSayService.Iface sayService = new MultiSayServiceImpl();
		MultiSayService.Processor<MultiSayService.Iface> sayProcessor = new MultiSayService.Processor<MultiSayService.Iface>(sayService);
		processor.registerProcessor(MultiSayServiceImpl.SERVICE_NAME, sayProcessor);
		return processor;
	}
	
	public static class MultiHelloServiceImpl implements MultiHelloService.Iface {

		public final static String SERVICE_NAME = "service_hello";
		@Override
		public String hello(String name) throws TException {
			return "Hello, " + name;
		}
	}
	
	public static class MultiSayServiceImpl implements MultiSayService.Iface {

		public final static String SERVICE_NAME = "service_say";
		@Override
		public String say(String name, String word) throws TException {
			return name + " say: " + word;
		}
		
	}
}
