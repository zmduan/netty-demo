package com.gyt.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zmduan on 2018/4/12.
 */
public class EchoServer {
    private int prot = 89;

    public EchoServer(int port){
        this.prot = port;
    }

    public static void main(String[] args) throws  Exception{
        new EchoServer(89).start();
    }

    public void start() throws Exception{
        final EchoServerHandler handler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(eventLoopGroup).channel(NioServerSocketChannel.class);
        server.localAddress(this.prot);
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("initChannel");
                ch.pipeline().addLast(handler);
            }
        });
        server.bind().sync().channel().closeFuture();

    }
}
