package com;
import java.io.*;

import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class ReceiveFileHandler extends ChannelInboundHandlerAdapter
{
	private Logger logger;
	private String fileName;
	private BufferedOutputStream bos=null;
	private Server server=null;
	/**
	 * Receive file handler
	 * @param fs FtpSessionHandler object
	 * @param fileName the location of the file to be resided.
	 * @param responseCtx A ChannelHandlerContext for sending file receive result to client
	 * @param passiveServer PassiveServer object
	 */
	public ReceiveFileHandler(String fileName,Server server)
	{
		this.fileName=fileName;
		this.server=server;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{ 
		try
		{
			bos=new BufferedOutputStream(new FileOutputStream(new File(fileName)));
		}
		catch (FileNotFoundException err)
		{
			err.printStackTrace();
		}
    }
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception 
	{ 
		ByteBuf in = (ByteBuf) msg;
		
	    try 
	    {
	        while (in.isReadable()) 
	        { 
	        	in.readBytes(bos,in.readableBytes());
	        }
	        bos.flush();
	    } finally {
	        in.release();
	    }
    }
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{ 
		if (bos==null)
		{
			ctx.channel().close();
		}
		else			
		{
			try
			{
				bos.flush();
				bos.close();
				bos=null;
				ctx.channel().close().addListener(new FileTransferCompleteListener(server));
			}
			catch (Exception err)
			{
				logger.debug(err.getMessage());
				ctx.channel().close();
			}
		}
    }
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
	}
}