package com.greenview.classes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: max
 * @Date: 2-5-15
 * @Project: bbs
 */
public class ServerThread extends Thread {

    private ServerSocketChannel serverSocketChannel = null;
    private Boolean serverIsRunning = false;
    private List<ServerClientThread> connectedClients = new ArrayList<ServerClientThread>();

    public void run() {
        this.acceptConnectionRequests();
    }

    public void startServer(){

        try {

            this.serverSocketChannel = ServerSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(Config.connectionPort);
            this.serverSocketChannel.socket().bind(socketAddress);
            this.serverSocketChannel.configureBlocking(false);

            this.serverIsRunning = true;

            this.acceptConnectionRequests();
        } catch (IOException e) {
            this.serverIsRunning = false;
            e.printStackTrace();
        }

    }

    private void acceptConnectionRequests(){
        Runnable connectionCheck =  () -> {
            while(this.getServerIsRunning()){
                try{
                    SocketChannel socketChannel = this.serverSocketChannel.accept();

                    if(socketChannel != null){

                        //Connection opened
                        socketChannel.configureBlocking(false);

                        ServerClientThread newClient = new ServerClientThread();
                        newClient.setup(socketChannel);
                        newClient.start();

                        this.connectedClients.add(newClient);
                    }
                    Thread.sleep(Config.subThreadTimeout);
                }catch(Exception e){

                }

            }
        };
        new Thread(connectionCheck).start();
    }

    public void stopServer(){
        if(this.serverSocketChannel != null){
            try {
                this.disconnectClients();
                this.serverSocketChannel.close();
                this.serverIsRunning = false;
            } catch (Exception e) {

            }
        }
    }

    public Boolean getServerIsRunning(){
        return this.serverIsRunning;
    }

    public List<ServerClientThread> getConnectedClients(){
        return this.connectedClients;
    }

    public void disconnectClients(){
        for(ServerClientThread serverClientThread : this.connectedClients){
            serverClientThread.disconnect();
        }
    }
}
