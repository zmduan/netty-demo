package com.gyt;

import com.gyt.server.NettyHttpServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            new NettyHttpServer().start();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println( "Hello World!" );
    }
}
