package ru.client.server.md5;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

class Server {
    private static Socket serverSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static final int PORT = 8081;
    public static void main(String[] args) {
        String message;
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server: Started");
            serverSocket = server.accept(); //.accept() is waiting for connection from client
            System.out.println(server.getLocalPort());
            out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            message = in.readLine();
            String md5 = MD5Hash.getHash(message);
            System.out.println("Server: Sending MD5 hash " + md5 + " to client");
            out.write(md5 + "\n");
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                in.close();
                out.close();
                server.close();
                System.out.println("Server: Connection is closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
