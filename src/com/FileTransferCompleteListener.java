package com;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class FileTransferCompleteListener implements ChannelFutureListener 
{
	Server server;

	public FileTransferCompleteListener(Server server) 
	{
		this.server=server;
	}

	@Override
	public void operationComplete(ChannelFuture cf) throws Exception 
	{
		System.out.println("File transfer completed!");
		if(server!=null)
			server.stop();
		else
			cf.channel().close();
	}
}
