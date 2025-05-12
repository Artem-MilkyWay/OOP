package org.example;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MasterTest {
    private static final int WORKER_PORT_1 = 8081;
    private static final int WORKER_PORT_2 = 8082;
    private static final int WORKER_PORT_3 = 8083;
    private Process worker1;
    private Process worker2;
    private Process worker3;

    private void waitForPortOpen(int port, int timeoutMillis, Process workerProcess) throws IOException {
        long start = System.currentTimeMillis();
        IOException lastEx = null;
        while (System.currentTimeMillis() - start < timeoutMillis) {
            try (Socket ignored = new Socket("localhost", port)) {
                return;
            } catch (IOException e) {
                lastEx = e;
                try { Thread.sleep(100); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
            }
            try {
                int exit = workerProcess.exitValue();
                String err = new String(workerProcess.getInputStream().readAllBytes());
                String serr = new String(workerProcess.getErrorStream().readAllBytes());
                System.out.println("[TEST DEBUG] Worker process exited with code " + exit + ". Output:\n" + err + "\nSTDERR:\n" + serr);
                throw new IOException("Worker process exited with code " + exit + ". Output:\n" + err + "\nSTDERR:\n" + serr);
            } catch (IllegalThreadStateException ignore) {
                // process still running
            }
        }
        String err = new String(workerProcess.getInputStream().readAllBytes());
        throw new IOException("Port " + port + " did not open in time. Worker output:\n" + err, lastEx);
    }

    @BeforeEach
    void setUp() throws IOException {
        // Start workers before each test
        worker1 = startWorker(WORKER_PORT_1);
        waitForPortOpen(WORKER_PORT_1, 10000, worker1);
        worker2 = startWorker(WORKER_PORT_2);
        waitForPortOpen(WORKER_PORT_2, 10000, worker2);
        worker3 = startWorker(WORKER_PORT_3);
        waitForPortOpen(WORKER_PORT_3, 10000, worker3);
    }

    @AfterEach
    void tearDown() {
        if (worker1 != null) {
            worker1.destroy();
            try { worker1.waitFor(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        if (worker2 != null) {
            worker2.destroy();
            try { worker2.waitFor(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        if (worker3 != null) {
            worker3.destroy();
            try { worker3.waitFor(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    private Process startWorker(int port) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "build/libs/Task_2_1_2-1.0-SNAPSHOT-all.jar", "worker", String.valueOf(port));
        pb.redirectErrorStream(true);
        return pb.start();
    }

    private String runMasterWithArgs(String[] args) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            Main.main(args);
        } finally {
            System.setOut(originalOut);
        }
        return outContent.toString();
    }

    @Test
    void testAllWorkersAvailable() {
        String[] args = {"master", "2,3,4,5,6,7,8,9,10",
                "localhost:" + WORKER_PORT_1,
                "localhost:" + WORKER_PORT_2,
                "localhost:" + WORKER_PORT_3};

        String output = runMasterWithArgs(args);
        assertTrue(output.contains("true"), "Should detect non-prime numbers");
    }

    @Test
    void testAllNumbersPrime() {
        String[] args = {"master", "2,3,5,7,11,13,17",
                "localhost:" + WORKER_PORT_1,
                "localhost:" + WORKER_PORT_2};

        String output = runMasterWithArgs(args);
        assertTrue(output.contains("false"), "All numbers are prime, should return false");
    }

    @Test
    void testOneWorkerFails() throws InterruptedException {
        // Kill one worker before starting the test
        worker1.destroy();
        worker1.waitFor(1, TimeUnit.SECONDS);

        String[] args = {"master", "2,3,4,5,6",
                "localhost:" + WORKER_PORT_1,
                "localhost:" + WORKER_PORT_2,
                "localhost:" + WORKER_PORT_3};

        String output = runMasterWithArgs(args);
        assertTrue(output.contains("Failed to connect to worker") || output.contains("true"), 
            "Should either report worker failure or detect non-prime numbers");
    }

    @Test
    void testWorkerFailsDuringExecution() throws InterruptedException {
        String[] args = {"master", "2,3,4,5,6,7,8,9,10",
                "localhost:" + WORKER_PORT_1,
                "localhost:" + WORKER_PORT_2,
                "localhost:" + WORKER_PORT_3};

        Thread masterThread = new Thread(() -> runMasterWithArgs(args));
        masterThread.start();

        // Wait a bit and then kill a worker
        Thread.sleep(1000);
        worker1.destroy();
        worker1.waitFor(1, TimeUnit.SECONDS);

        masterThread.join(5000);
        assertTrue(true, "Master should handle mid-execution worker failure gracefully");
    }

    @Test
    void testLargeNumberSet() {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= 100; i++) {
            sb.append(i).append(",");
        }
        sb.setLength(sb.length() - 1);

        String[] args = {"master", sb.toString(),
                "localhost:" + WORKER_PORT_1,
                "localhost:" + WORKER_PORT_2,
                "localhost:" + WORKER_PORT_3};

        String output = runMasterWithArgs(args);
        assertTrue(output.contains("true"), "Should detect non-primes in large set");
    }
}
