package com.greenview.classes;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: max
 * @Date: 27-4-15
 * @Project: bbs
 */
public class Client {

    private SocketChannel socketChannel = null;
    private ByteBuffer byteBuffer       = null;

    public Client(String ip, Integer port){

        try{
            this.socketChannel = SocketChannel.open();
            this.socketChannel.connect(new InetSocketAddress(ip, port));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String message){
        try{

            this.byteBuffer = ByteBuffer.allocate(48);
            this.byteBuffer.clear();
            this.byteBuffer.put(message.getBytes("UTF-8"));
            this.byteBuffer.flip();

            while(this.byteBuffer.hasRemaining()){
                this.socketChannel.write(this.byteBuffer);

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void close(){
        try{
            this.socketChannel.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
