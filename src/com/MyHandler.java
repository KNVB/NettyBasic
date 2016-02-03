package com;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class MyHandler implements ChannelInboundHandler
{
	int i=0,byteRead;
	boolean isCompleted=false;
	RandomAccessFile file;
	FileChannel fc;
	ByteBuffer buffer=ByteBuffer.allocate(1024);
	String fileName,line;
	Server server=null;
	/**
	 * Send file handler
	 * @param fileName A file to be sent to client 
	 * @param fs FtpSessionHandler object
	 * @param responseCtx A ChannelHandlerContext for sending file name list transfer result to client
	 * @param passiveServer PassiveServer object
	 */
	public MyHandler(String fileName,Server server)
	{
		this.fileName=fileName;
		this.server=server;
	}
	public void channelActive(ChannelHandlerContext ctx) throws IOException 
	{
		file=new RandomAccessFile(fileName,"r");
		fc=file.getChannel();
		byteRead=fc.read(buffer);
		System.out.println("a byteRead="+byteRead);
		ctx.writeAndFlush(Unpooled.wrappedBuffer(buffer.array(),0,byteRead));
	}
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception 
	{
		System.out.println("Hello");
		if (isCompleted)
		{
			fc.close();
			file.close();
			System.out.println("Server:Transfer completed.");
			server.stop();	
		}
		else
		{
			if (fc.position()==file.length())
			{
				 isCompleted=true;
			}
			else
			{
				 if (ctx.channel().isWritable())
				 {
					 buffer.clear();
					 byteRead=fc.read(buffer);
					 System.out.println("b byteRead="+byteRead);
					 if (byteRead>0)
					 {
						 ctx.writeAndFlush(Unpooled.wrappedBuffer(buffer.array(),0,byteRead));
					 }
					 else
						 isCompleted=true;
				 }
				 else
					 System.out.println("Is writable=false");
			}
		}
	}
	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext arg0)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext arg0)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable t)
			throws Exception {
		t.printStackTrace();
		
	}
	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
		
		
	}
}
