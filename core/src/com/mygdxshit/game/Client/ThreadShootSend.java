package com.mygdxshit.game.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadShootSend extends Thread {

    private Socket clientSocket;
    private boolean isShoot = false;
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
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                if (isShoot) {
                    outputStream.writeObject("1");
                    inputStream.readObject();
                    isShoot = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setIsShoot(boolean state) {
        isShoot = state;
    }
}