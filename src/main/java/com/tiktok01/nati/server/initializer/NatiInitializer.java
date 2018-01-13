package com.tiktok01.nati.server.initializer;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiktok01.nati.core.handler.ThriftDecoder;
import com.tiktok01.nati.core.handler.ThriftEncoder;
import com.tiktok01.nati.core.handler.ThriftHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NatiInitializer extends ChannelInitializer<SocketChannel> {

	private final static Logger logger = LoggerFactory.getLogger(NatiInitializer.class);
	
	protected ThriftHandler thriftHandler;
	
	public NatiInitializer(ThriftHandler thriftHandler) {
		super();
		this.thriftHandler = thriftHandler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		logger.info("nati initialize netty channel...");
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ThriftDecoder());
		pipeline.addLast(new ThriftEncoder());
		pipeline.addLast(thriftHandler);
	}
	
	public final static NatiInitializer create(TProcessor processor, Class<? extends TProtocol> cls) {
		ThriftHandler thriftHandler = new ThriftHandler(processor, cls);
		return new NatiInitializer(thriftHandler);
	}
}
