package com.greenview.classes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: max
 * @Date: 3-5-15
 * @Project: bbs
 */
public class ServerClientThread extends Thread {

    private Boolean clientIsConnected = false;
    private SocketChannel clientConnection = null;

    public void run(){
        this.getUpdates();
    }

    public void setup(SocketChannel client){
        this.clientConnection = client;
        this.clientIsConnected = true;

    }

    public void getUpdates(){
        Runnable updateCheck = () -> {
            while(this.clientIsConnected){
                try {

                    ByteBuffer buffer = ByteBuffer.allocate(48);
                    Integer messageBytes = this.clientConnection.read(buffer);

                    if(messageBytes > 0) {
                        System.out.println("///// New message:");
                        System.out.println("/////" + new String(buffer.array()));
                    }

                    Thread.sleep(Config.subThreadTimeout);
                } catch (Exception e) {

                }
            }
        };

        new Thread(updateCheck).start();
    }

    public void disconnect(){
        try {
            this.clientConnection.close();
            this.clientIsConnected = false;
        } catch (Exception e) {

        }

    }

}
