package com.gyt.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by zmduan on 2018/4/9.
 */
public class NettyHttpServer {
    private Integer port = 88;

    public NettyHttpServer(){

    }

    public NettyHttpServer(int port){
        this.port = port;
    }

    public void start() throws Exception{
        ServerBootstrap server = new ServerBootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        server.group(eventLoopGroup);
        server.channel(NioServerSocketChannel.class);
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                System.out.println("channel init"+channel);
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("decoder",new HttpRequestDecoder());
                pipeline.addLast("encoder",new HttpResponseEncoder());
                pipeline.addLast("aggregator",new HttpObjectAggregator(512*1024));
                pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
                pipeline.addLast("handler",new HttpHandler());

            }
        });
        server.option(ChannelOption.SO_BACKLOG,128);
        server.option(ChannelOption.SO_KEEPALIVE,true);
        server.bind(this.port).sync().channel().closeFuture().sync();
    }

}
