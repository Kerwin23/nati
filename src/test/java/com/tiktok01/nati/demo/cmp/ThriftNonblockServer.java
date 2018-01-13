package com.tiktok01.nati.demo.cmp;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.tiktok01.nati.demo.SimpleServer.SimpleTestServiceImpl;
import com.tiktok01.nati.demo.SimpleTestService;

public class ThriftNonblockServer {

	public static void main(String[] args) {
		SimpleTestService.Iface simpleService = new SimpleTestServiceImpl();
		SimpleTestService.Processor<SimpleTestService.Iface> processor = new SimpleTestService.Processor<SimpleTestService.Iface>(simpleService);
		TNonblockingServerSocket serverTransport = null;
		try {
			serverTransport = new TNonblockingServerSocket(1234);
			TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
			tArgs.processor(processor);
			tArgs.transportFactory(new TFramedTransport.Factory());
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			TNonblockingServer server = new TNonblockingServer(tArgs);
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
