package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Master {
    private final List<Socket> workers = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService;
    private final AtomicBoolean resultFound = new AtomicBoolean(false);
    private final CountDownLatch taskCompletionLatch;
    private final AtomicInteger activeWorkers = new AtomicInteger(0);

    public Master(String[] workerAddresses) {
        executorService = Executors.newFixedThreadPool(workerAddresses.length);
        taskCompletionLatch = new CountDownLatch(1);
        connectToWorkers(workerAddresses);
    }

    private void connectToWorkers(String[] workerAddresses) {
        for (String address : workerAddresses) {
            try {
                String[] parts = address.split(":");
                if (parts.length != 2) {
                    System.err.println("Invalid worker address format: " + address);
                    continue;
                }
                String host = parts[0];
                int port = Integer.parseInt(parts[1]);
                
                Socket worker = new Socket();
                worker.connect(new java.net.InetSocketAddress(host, port), 5000);
                workers.add(worker);
                activeWorkers.incrementAndGet();
            } catch (Exception e) {
                System.err.println("Failed to connect to worker " + address + ": " + e.getMessage());
            }
        }
        
        if (workers.isEmpty()) {
            System.out.println("No available workers");
            taskCompletionLatch.countDown();
            return;
        }
    }

    public boolean processNumbers(String[] numbers) {
        if (workers.isEmpty()) {
            System.out.println("No available workers");
            return true;
        }

        BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();
        for (String number : numbers) {
            taskQueue.offer(number);
        }

        List<Future<?>> futures = new ArrayList<>();
        for (Socket worker : workers) {
            futures.add(executorService.submit(() -> {
                try {
                    processTasks(worker, taskQueue);
                } catch (Exception e) {
                    System.err.println("Worker failed: " + e.getMessage());
                    synchronized (workers) {
                        workers.remove(worker);
                        activeWorkers.decrementAndGet();
                    }
                    if (activeWorkers.get() == 0) {
                        System.out.println("No available workers");
                        resultFound.set(true);
                        taskCompletionLatch.countDown();
                    }
                }
            }));
        }

        try {
            taskCompletionLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Future<?> future : futures) {
            future.cancel(true);
        }

        return activeWorkers.get() == 0 || resultFound.get();
    }

    private void processTasks(Socket worker, BlockingQueue<String> taskQueue) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(worker.getInputStream()));
             PrintWriter out = new PrintWriter(worker.getOutputStream(), true)) {
            
            while (!resultFound.get() && !Thread.currentThread().isInterrupted()) {
                String number = taskQueue.poll(1, TimeUnit.SECONDS);
                if (number == null) {
                    break;
                }

                out.println(number);
                String response = in.readLine();
                if (response == null) {
                    throw new IOException("Worker disconnected");
                }

                if (response.equals("NOT_PRIME")) {
                    resultFound.set(true);
                    taskCompletionLatch.countDown();
                    break;
                } else if (!response.equals("PRIME")) {
                    System.err.println("Unexpected response from worker: " + response);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void cleanup() {
        for (Socket worker : workers) {
            try {
                worker.close();
            } catch (IOException e) {
                System.err.println("Error closing worker connection: " + e.getMessage());
            }
        }
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

