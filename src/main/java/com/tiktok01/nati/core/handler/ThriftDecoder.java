package com.tiktok01.nati.core.handler;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiktok01.nati.core.TNatiTransport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Thrift message decoder
 * @author Kerwin
 *
 */
public class ThriftDecoder extends ByteToMessageDecoder {

	private final static Logger logger = LoggerFactory.getLogger(ThriftDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] dst = new byte[in.readableBytes()];
		in.readBytes(dst);
		TTransport transport = new TIOStreamTransport(new ByteArrayInputStream(dst));
		TNatiTransport natiTransport = new TNatiTransport(ctx.channel(), transport);
		out.add(natiTransport);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		super.exceptionCaught(ctx, cause);
	}
}
