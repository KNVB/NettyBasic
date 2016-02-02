package com;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Calendar;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 * @author SITO3
 * 
 *
 */
public class Server 
{
	private Channel ch;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
	/**
	 * This is passive mode server
	 * @param fs FTP Session Handler
	 * @param host Server IP address
	 * @param port Passive port no.
	 */
	public Server(String host, int port,int mode,String fileName)
	{
		InetSocketAddress inSocketAddress=new InetSocketAddress(host,port); 
		try 
        {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(bossGroup, workerGroup);
            bootStrap.channel(NioServerSocketChannel.class);
            bootStrap.childHandler(new MyChannelInitializer(this, mode,fileName));
            bootStrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootStrap.bind(inSocketAddress);
            System.out.println("Server started");
        }
		catch (Exception eg)
		{
			eg.printStackTrace();
			stop();
		}
	}
	
	/**
	 * Send a file to client
	 * @param serverPath A file to be sent to client 
	 * @param responseCtx A ChannelHandlerContext for sending file transfer result to client
	 */
	public void sendFile(String serverPath) throws IOException 
	{
		ch.pipeline().addLast("streamer", new ChunkedWriteHandler());
		ch.pipeline().addLast("handler",new SendFileHandler(serverPath, this));
	}
	/**
	 * Receive a file from client
	 * @param serverPath the location of the file to be resided.
	 * @param responseCtx A ChannelHandlerContext for sending file receive result to client
	 */
	public void receiveFile(String serverPath, ChannelHandlerContext responseCtx) 
	{
		ch.pipeline().addLast(new ReceiveFileHandler(serverPath,this));
	}
	/**
	 * Stop the passive server and return passive port to passive port pool 
	 */
	public void stop()
	{
    	bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		Calendar endTime=Calendar.getInstance();
		System.out.println("Server is shutdown gracefully."+endTime.getTimeInMillis());
	}
	public static void main(String[] args) throws Exception 
	{
		
		@SuppressWarnings("unused")
		Server s=new Server("localhost",1234,MyFtpServer.SENDFILE,"D:\\SITO3\\Documents\\Xmas-20141224-310.jpg");
		//Server s=new Server("localhost",1234,MyFtpServer.RECEIVEFILE,"D:\\SITO3\\Desktop\\Xmas-20141224-310.jpg");
	}	
}
