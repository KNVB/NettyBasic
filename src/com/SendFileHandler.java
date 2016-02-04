package com;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public abstract class SendFileHandler implements ChannelInboundHandler,ChannelFutureListener  
{

	public abstract void handlerAdded(ChannelHandlerContext ctx) throws Exception;
	public abstract void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
	public abstract void operationComplete(ChannelFuture future) throws Exception;
	public abstract void channelRegistered(ChannelHandlerContext ctx) throws Exception;
	public abstract void channelUnregistered(ChannelHandlerContext ctx) throws Exception;	
	public abstract void channelActive(ChannelHandlerContext ctx) throws Exception;
	public abstract void channelInactive(ChannelHandlerContext ctx) throws Exception;
	public abstract void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception;	
	public abstract void channelReadComplete(ChannelHandlerContext ctx) throws Exception;
	public abstract void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception;
	public abstract void channelWritabilityChanged(ChannelHandlerContext ctx)throws Exception;	
	public abstract void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception;
}
