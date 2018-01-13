package com.tiktok01.nati.demo.cmp;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.tiktok01.nati.demo.SimpleServer.SimpleTestServiceImpl;
import com.tiktok01.nati.demo.SimpleTestService;

public class ThriftTheadpoolServer {

	public static void main(String[] args) {
		SimpleTestService.Iface simpleService = new SimpleTestServiceImpl();
		SimpleTestService.Processor<SimpleTestService.Iface> processor = new SimpleTestService.Processor<SimpleTestService.Iface>(simpleService);
		TServerSocket serverSocket = null;
		try {
			serverSocket = new TServerSocket(1234);
			TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverSocket);
			tArgs.processor(processor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			tArgs.maxWorkerThreads(10000);
			TThreadPoolServer server = new TThreadPoolServer(tArgs);
			System.out.println("server start...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				serverSocket.close();
			}
		}
	}
}
