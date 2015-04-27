package com.greenview.classes;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: max
 * @Date: 27-4-15
 * @Project: bbs
 */
public class Server {
    public final Integer PORT = 2112;

    private ServerSocketChannel serverSocketChannel = null;
    private Boolean serverIsRunning = false;

    public Server(){
        try {

            this.serverSocketChannel = ServerSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(this.PORT);
            this.serverSocketChannel.socket().bind(socketAddress);
            this.serverSocketChannel.configureBlocking(false);

            this.serverIsRunning = true;

            //this.acceptConnectionRequests();
        } catch (IOException e) {
            this.serverIsRunning = false;
            e.printStackTrace();
        }
    }

    public ServerClient acceptConnectionRequests() throws Exception {

        //while(serverIsRunning){
            try {
                SocketChannel socketChannel = this.serverSocketChannel.accept();
                socketChannel.configureBlocking(false);

                if(socketChannel != null){
                    //Connection opened
                    System.out.println("Client request");
                    return new ServerClient(socketChannel);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        //}
        throw new Exception("No incomming requests");
    }

    public void close(){
        try {
            this.serverSocketChannel.close();
            this.serverIsRunning = false;
        } catch (Exception e){
            System.out.println("unable to stop server. But was able to stop incomming requests");
        }
    }
}
