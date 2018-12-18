package ru.client.server.md5;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        String message;
        String serverResponce;
        try {
            System.out.println("Client: Started");
            clientSocket = new Socket("localhost", 8081);
            reader = new BufferedReader(new InputStreamReader(System.in)); // request to server for connection
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            System.out.println("Client: Started connection to sever");
            System.out.println("Client: Enter your message to server");
            message = reader.readLine();
            String md5 = MD5Hash.getHash(message);
            out.write(message + "\n");
            out.flush();
            serverResponce = in.readLine();
            System.out.println("Client: receiving message from server: " + serverResponce);
            if (serverResponce.equals(md5)){
                System.out.println("MD5 hashes is equal");
            }
            else{
                System.out.println("MD5 hashes is not equal");
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client: connection is closed");
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}