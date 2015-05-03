package com.greenview.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: max
 * @Date: 3-5-15
 * @Project: bbs
 */
public class ClientGroup {
    private List<Client> clients = new ArrayList<Client>();

    public void addClient(Client client){
        this.clients.add(client);
    }

    public void streamMessage(String message){
        for(Client client : this.clients){
            client.sendMessage(message);
        }
    }
}
