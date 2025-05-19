package org.example;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker {
    private static final AtomicBoolean running = new AtomicBoolean(true);

    public static void run(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Worker listening on port " + port);
            while (running.get()) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Worker accepted connection on port " + port);
                    handleClient(clientSocket);
                } catch (IOException e) {
                    if (running.get()) {
                        System.err.println("Error accepting connection: " + e.getMessage());
                    }
                } finally {
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            System.err.println("Error closing client socket: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start worker on port " + port + ": " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            boolean foundNotPrime = false;

            while ((inputLine = in.readLine()) != null && running.get()) {
                if (inputLine.equals("END")) {
                    break;
                }

                try {
                    int n = Integer.parseInt(inputLine.trim());
                    if (!Utils.isPrime(n)) {
                        out.println("NOT_PRIME");
                        foundNotPrime = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number received: " + inputLine);
                    out.println("ERROR");
                    break;
                }
            }

            if (!foundNotPrime) {
                out.println("DONE");
            }
        }
    }

    public static void shutdown() {
        running.set(false);
    }
}

