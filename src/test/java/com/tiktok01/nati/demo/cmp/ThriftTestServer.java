package com.tiktok01.nati.demo.cmp;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.tiktok01.nati.demo.SimpleTestService;
import com.tiktok01.nati.demo.SimpleServer.SimpleTestServiceImpl;

public class ThriftTestServer {

	public static void main(String[] args) {
		SimpleTestService.Iface simpleService = new SimpleTestServiceImpl();
		SimpleTestService.Processor<SimpleTestService.Iface> processor = new SimpleTestService.Processor<SimpleTestService.Iface>(simpleService);
		TServerTransport serverTransport = null;
		try {
			serverTransport = new TServerSocket(1234);
			TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
			System.out.println("server start...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(serverTransport != null) {
				serverTransport.close();
			}
		}
	}
}
