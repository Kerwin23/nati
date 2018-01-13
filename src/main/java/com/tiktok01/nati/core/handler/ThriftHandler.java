package com.tiktok01.nati.core.handler;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiktok01.nati.core.TNatiWorker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * Thrift处理工具
 * @author Kerwin
 *
 */
@Sharable
public class ThriftHandler extends ChannelInboundHandlerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(ThriftHandler.class);
	
	private TNatiWorker worker;
	
	/**
	 * 
	 * @param processor	thrift原生processor对象
	 * @param cls	thrift使用的协议类，与客户端一致
	 */
	public ThriftHandler(TProcessor processor, Class<? extends TProtocol> cls) {
		this.worker = new TNatiWorker(processor, cls);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof TTransport) {
			TTransport transport = (TTransport) msg;
			worker.process(transport);
		} else {
			super.channelRead(ctx, msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		super.exceptionCaught(ctx, cause);
	}
	
	@Override
	public boolean isSharable() {
		return true;
	}
}
