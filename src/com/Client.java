package com;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author SITO3
 */
public class Client 
{
	Server server;
	Logger logger;
	ChannelHandlerContext responseCtx;
	public Client(String host, int port, int mode, String fileName)
	{
		server=null;
		EventLoopGroup group = new NioEventLoopGroup();
		try 
		{
			Bootstrap b = new Bootstrap(); 
			b.group(group);
			b.channel(NioSocketChannel.class);
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
				group.shutdownGracefully(0,0,TimeUnit.MILLISECONDS).sync();
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
		//Client c=new Client("localhost",1234,MyFtpServer.RECEIVEFILE,"D:\\SITO3\\Desktop\\ntuser.ini");
		Client c=new Client("localhost",1234,MyFtpServer.RECEIVEFILE,"C:\\Users\\cstsang\\Desktop\\PAL95.rar");
		//Client c=new Client("localhost",1234,MyFtpServer.RECEIVEFILE,"C:\\Users\\cstsang\\Desktop\\test6.f");
		//Client c=new Client("localhost",1234,MyFtpServer.SENDFILE,"D:\\SITO3\\Documents\\Xmas-20141224-310.jpg");
	}
}
