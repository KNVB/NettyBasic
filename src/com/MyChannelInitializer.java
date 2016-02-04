package com;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class MyChannelInitializer extends ChannelInitializer<Channel> 
{
	private int mode;
	private Server server=null;
    private String fileName;
    public MyChannelInitializer(Server server, int mode,String fileName)
    {
        this.mode=mode;
        this.server=server;
        this.fileName=fileName;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception 
    {
        if (this.mode==MyFtpServer.RECEIVEFILE)
        {
            //ch.pipeline().addLast(new ChannelTrafficShapingHandler(0L,10240L));  
            ch.pipeline().addLast(new ReceiveFileHandler(this.fileName,server));
        }
        else
        {
            //ch.pipeline().addLast(new ChannelTrafficShapingHandler(10240L,0L));
            //ch.pipeline().addLast("streamer", new ChunkedWriteHandler()); 
            //ch.pipeline().addLast("handler",new SendFileHandler(this.fileName,server));
            //SendFileHandler sendFileHandler=new MyHandler(this.fileName,server);
            SendFileHandler sendFileHandler=new MyHandler_text(this.fileName,server);
        	ch.pipeline().addLast("handler",sendFileHandler);
        	ch.closeFuture().addListener(sendFileHandler);
        }
    }   
}
