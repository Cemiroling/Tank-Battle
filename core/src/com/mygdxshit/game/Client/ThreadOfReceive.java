package com.mygdxshit.game.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadOfReceive extends Thread {

    private Socket clientSocket;
    private String message;//0 - upClicked;1 - rightClicked;2 - downClicked;
    private InetAddress ip;

    {
        try {
            ip = InetAddress.getByName("192.168.43.123");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public ThreadOfReceive() {
        message = "4";
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, 4444);

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                String tmp = inputStream.readObject().toString();
                outputStream.writeObject("1");
                setMessage(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

