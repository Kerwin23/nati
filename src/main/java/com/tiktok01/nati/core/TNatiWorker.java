package com.tiktok01.nati.core;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.TTransport;

/**
 * 核心处理类，负责对输入输出数据进行反序列化调用等处理
 * @author Kerwin
 *
 */
public class TNatiWorker {
    TProtocolFactory inputProtocolFactory = null;
    TProtocolFactory outputProtocolFactory = null;
	private TProcessor processor;
	
	public TNatiWorker(TProcessor processor, Class<? extends TProtocol> cls) {
		super();
		this.processor = processor;
		if(cls.equals(TBinaryProtocol.class)) {
			inputProtocolFactory = new TBinaryProtocol.Factory();
			outputProtocolFactory = new TBinaryProtocol.Factory();
		} else if(cls.equals(TCompactProtocol.class)) {
			inputProtocolFactory = new TCompactProtocol.Factory();
			outputProtocolFactory = new TCompactProtocol.Factory();
		} else if(cls.equals(TJSONProtocol.class)) {
			inputProtocolFactory = new TJSONProtocol.Factory();
			outputProtocolFactory = new TJSONProtocol.Factory();
		} else if(cls.equals(TSimpleJSONProtocol.class)) {
			inputProtocolFactory = new TSimpleJSONProtocol.Factory();
			outputProtocolFactory = new TSimpleJSONProtocol.Factory();
		} else {
			throw new RuntimeException("unknown thrift protocol");
		}
	}
	
	public void process(TTransport transport) {
		try {
			TProtocol in = inputProtocolFactory.getProtocol(transport);
			TProtocol out = outputProtocolFactory.getProtocol(transport);
			processor.process(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				transport.flush();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
