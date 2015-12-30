package com;

import java.net.InetSocketAddress;
import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;


/**
 * 
 * @author SITO3
 */
public class OClient 
{
	Server server;
	Logger logger;
	ChannelHandlerContext responseCtx;
	/** 
	* This is an active mode client for file transfer and file listing transfer	
	 * @param fileName 
	 * @param mode 
	 * @param port 
	 * @param host 
	**/
	public OClient(String host, int port, int mode, String fileName)
	{
		server=null;
		EventLoopGroup group = new OioEventLoopGroup();
		try 
		{
			Bootstrap b = new Bootstrap(); 
			b.group(group);
			b.channel(OioSocketChannel.class);
			b.remoteAddress(new InetSocketAddress(host, port));
			b.handler(new MyChannelInitializer(server, mode,fileName));
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
			System.out.println("client started");
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar endTime=Calendar.getInstance();
			System.out.println("client stopped "+endTime.getTimeInMillis());
		}
	}
	public static void main(String[] args) throws Exception 
	{
		@SuppressWarnings("unused")
		//Client c=new Client("localhost",1234,MyFtpServer.RECEIVEFILE,"D:\\SITO3\\Desktop\\Xmas-20141224-310.jpg");
		Client c=new Client("localhost",1234,MyFtpServer.SENDFILE,"D:\\SITO3\\Documents\\Xmas-20141224-310.jpg");
	}
}
