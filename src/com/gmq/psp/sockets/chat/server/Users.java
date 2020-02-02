package com.gmq.psp.sockets.chat.server;
    import java.net.Socket;
    import java.util.HashMap;
public class Users {
    private HashMap<Integer, Socket>list;

    public Users(HashMap<Integer, Socket>list){
        this.list=list;

    }

    public Users(){
        list=new HashMap<Integer,Socket>();

    }

    public HashMap<Integer, Socket> getUsers(){
        return list;

    }

    public synchronized void addUser(int key,Socket socket){
        list.put(key, socket);

    }

    public synchronized void removeUser(int key){
        list.remove(key);

    }

    public synchronized int listSize(){
        return list.size();

    }
}
