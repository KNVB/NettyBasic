package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

public class MyHandler_text implements ChannelInboundHandler
{
	int i=0;
	boolean isCompleted=false;
	RandomAccessFile file;
	FileChannel fc;
	BufferedReader br;
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
	public MyHandler_text(String fileName,Server server)
	{
		this.fileName=fileName;
		this.server=server;
	}
	public void channelActive(ChannelHandlerContext ctx) throws IOException 
	{
//		ctx.writeAndFlush(new ChunkedFile(new File(this.fileName))).addListener(new FileTransferCompleteListener(server));
		br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"ISO-8859-1"));
		line = br.readLine();
		ctx.writeAndFlush(Unpooled.copiedBuffer(line+"\r\n",CharsetUtil.ISO_8859_1));
		
	}
	 public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception 
	 {
		 if (isCompleted)
		 {
			 br.close();
			 System.out.println((++i)+" I am here  "+ctx.channel().isWritable());
			 server.stop();
		 }
		 else
		 {
			 if (br.ready())
			 {
				 if (ctx.channel().isWritable())
				 {
					 line = br.readLine();
					 if (line==null)
						 isCompleted=true;
					 else	 
						 ctx.writeAndFlush(Unpooled.copiedBuffer(line+"\r\n",CharsetUtil.ISO_8859_1));
				 }
			 }
			 else
				 isCompleted=true; 
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
