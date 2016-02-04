package com; 
import java.io.File;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
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
 */
@Sharable
public class SendFileHandler_org extends SimpleChannelInboundHandler<ByteBuf> implements ChannelHandler 
{
	String fileName;
	Server server=null;
	/**
	 * Send file handler
	 * @param fileName A file to be sent to client 
	 * @param fs FtpSessionHandler object
	 * @param responseCtx A ChannelHandlerContext for sending file name list transfer result to client
	 * @param passiveServer PassiveServer object
	 */
	public SendFileHandler_org(String fileName,Server server)
	{
		this.fileName=fileName;
		this.server=server;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws IOException 
	{ 
		ctx.writeAndFlush(new ChunkedFile(new File(this.fileName))).addListener(new FileTransferCompleteListener(server)); 
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
			throws Exception {
		t.printStackTrace();
	}
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf arg1)
			throws Exception {
		// TODO Auto-generated method stub
	}
}







