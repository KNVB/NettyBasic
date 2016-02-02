package com;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyHandler extends ChannelInboundHandlerAdapter 
{
	RandomAccessFile file;
	FileChannel fc;
	ByteBuffer buffer=ByteBuffer.allocate(1024);
	String fileName;
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
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws IOException 
	{
		file=new RandomAccessFile(fileName,"r");
		fc=file.getChannel();
		fc.read(buffer);
		ctx.writeAndFlush(Unpooled.copiedBuffer(buffer));
	}
	 @Override
	 public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception 
	 {
		 System.out.println("I am here");
		 fc.close();
		 file.close();
	 }
}
