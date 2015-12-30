package com;

import java.util.Calendar;

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
		Calendar endTime=Calendar.getInstance();
		System.out.println("File transfer completed! "+endTime.getTimeInMillis());
		if(server!=null)
			server.stop();
		else
			cf.channel().close();
	}
}
