package com.tiktok01.nati.server;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiktok01.nati.server.initializer.NatiInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Nati Server端
 * @author Kerwin
 *
 */
public class NatiServer {

	private final static Logger logger = LoggerFactory.getLogger(NatiServer.class);
	
	private String host;
	private Integer port;
	private Integer parentThreads;
	private Integer childThreads;
	private ServerBootstrap serverBootstrap;
	private NatiInitializer natiInitializer;

	public NatiServer() {
		super();
		this.serverBootstrap = new ServerBootstrap();
	}

	public NatiServer(String host, Integer port, Integer parentThreads, Integer childThreads) {
		super();
		this.host = host;
		this.port = port;
		this.parentThreads = parentThreads;
		this.childThreads = childThreads;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(String host, Integer port, Integer parentThreads, Integer childThreads, NatiInitializer natiInitializer) {
		super();
		this.host = host;
		this.port = port;
		this.parentThreads = parentThreads;
		this.childThreads = childThreads;
		this.natiInitializer = natiInitializer;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(String host, Integer port) {
		super();
		this.host = host;
		this.port = port;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(String host, Integer port, NatiInitializer natiInitializer) {
		super();
		this.host = host;
		this.port = port;
		this.natiInitializer = natiInitializer;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(Integer port, Integer parentThreads, Integer childThreads) {
		super();
		this.port = port;
		this.parentThreads = parentThreads;
		this.childThreads = childThreads;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(Integer port, Integer parentThreads, Integer childThreads, NatiInitializer natiInitializer) {
		super();
		this.port = port;
		this.parentThreads = parentThreads;
		this.childThreads = childThreads;
		this.natiInitializer = natiInitializer;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(Integer port) {
		super();
		this.port = port;
		this.serverBootstrap = new ServerBootstrap();
	}
	
	public NatiServer(Integer port, NatiInitializer natiInitializer) {
		super();
		this.port = port;
		this.natiInitializer = natiInitializer;
		this.serverBootstrap = new ServerBootstrap();
	}
	/**
	 * 运行server
	 */
	public void start() {
		validate();
		EventLoopGroup parentGroup = createParentGroup();
		EventLoopGroup childGroup = createChildGroup();
		try {
			serverBootstrap.group(parentGroup, childGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.childHandler(natiInitializer);
			ChannelFuture future = serverBind(serverBootstrap);
			logger.info("start running nati server...");
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.info("nati server shutdown...");
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}
	
	public <T> NatiServer serverOption(ChannelOption<T> option, T value) {
		this.serverBootstrap.option(option, value);
		return this;
	}
	
	public <T> NatiServer childOption(ChannelOption<T> option, T value) {
		this.serverBootstrap.childOption(option, value);
		return this;
	}
	
	private NioEventLoopGroup createParentGroup() {
		if(parentThreads != null) {
			return new NioEventLoopGroup(parentThreads);
		}
		return new NioEventLoopGroup();
	}
	
	private NioEventLoopGroup createChildGroup() {
		if(childThreads != null) {
			return new NioEventLoopGroup(childThreads);
		}
		return new NioEventLoopGroup();
	}
	
	private ChannelFuture serverBind(ServerBootstrap serverBootstrap) {
		logger.info("server bind {host: " + host + ", port: " + port + "}");
		ChannelFuture future = null;
		if(StringUtils.isNotBlank(host)) {
			future = serverBootstrap.bind(host, port);
		} else {
			future = serverBootstrap.bind(port);
		}
		return future;
	}
	
	private void validate() {
		logger.debug("start validate nati server");
		if(port == null || port < 0 || port > 65535) {
			throw new RuntimeException("error nati server port: " + port);
		}
		if(natiInitializer == null) {
			throw new RuntimeException("nati ChannelInitializer is null");
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getParentThreads() {
		return parentThreads;
	}

	public void setParentThreads(Integer parentThreads) {
		this.parentThreads = parentThreads;
	}

	public Integer getChildThreads() {
		return childThreads;
	}

	public void setChildThreads(Integer childThreads) {
		this.childThreads = childThreads;
	}

	public NatiInitializer getNatiInitializer() {
		return natiInitializer;
	}

	public void setNatiInitializer(NatiInitializer natiInitializer) {
		this.natiInitializer = natiInitializer;
	}
}
