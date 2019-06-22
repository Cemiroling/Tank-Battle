package com.mygdxshit.game.Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadOfSend extends Thread {

    private Socket clientSocket;
    private String mull = "0";
    private String message = "4";//0 - upClicked;1 - rightClicked;2 - downClicked;
    private InetAddress ip;

    {
        try {
            ip = InetAddress.getByName("192.168.43.123");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, 4444);
            int a = 2;
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                if (a > 0) {
                    outputStream.writeObject(message);
                    inputStream.readObject();
                    a--;
                } else {
                    outputStream.writeObject(mull);
                    inputStream.readObject();
                    a = 2;
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setMessage(String action) {
        this.message = action;

    }

    public void setMull(String m) {
        mull = m;
    }
}
