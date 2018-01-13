package com.tiktok01.nati.core;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * 负责将thrift的Transport与netty连接，实现输入输出
 * @author Kerwin
 *
 */
public class TNatiTransport extends TTransport {

	private Channel channel;
	private TTransport in;
	private ByteBuf out;
	
	public TNatiTransport(Channel channel, TTransport in) {
		super();
		this.channel = channel;
		this.in = in;
		this.out = Unpooled.buffer();
	}

	@Override
	public boolean isOpen() {
		return in.isOpen();
	}

	@Override
	public void open() throws TTransportException {
	}

	@Override
	public void close() {
		in.close();
	}

	@Override
	public int read(byte[] buf, int off, int len) throws TTransportException {
		return in.read(buf, off, len);
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		out.writeBytes(buf, off, len);
	}

	@Override
	public void flush() {
		channel.writeAndFlush(out);
	}
}
