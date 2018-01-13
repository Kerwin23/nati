package com.tiktok01.nati.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Thrift message encoder
 * @author Kerwin
 *
 */
public class ThriftEncoder extends MessageToByteEncoder<ByteBuf> {

	private final static Logger logger = LoggerFactory.getLogger(ThriftEncoder.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		out.writeBytes(msg, msg.writerIndex());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		super.exceptionCaught(ctx, cause);
	}
}
