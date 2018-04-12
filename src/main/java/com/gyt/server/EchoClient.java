package com.gyt.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zmduan on 2018/4/12.
 */
public class EchoClient {
    private  int serverProt = 89;
    private  String host = "localhost";

    public EchoClient(String host,int serverProt){
        this.serverProt = serverProt;
        this.host = host;
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                new EchoClient("localhost",89).start();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                new EchoClient("localhost",89).start();
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                new EchoClient("localhost",89).start();
            }
        });
    }

    public void start(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(eventLoopGroup).channel(NioSocketChannel.class);
        client.remoteAddress(this.host,this.serverProt);
        client.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("client initChannel");
                ch.pipeline().addLast(new EchoClientHandler());
            }
        });
        try {
            client.connect().sync().channel().closeFuture().sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
