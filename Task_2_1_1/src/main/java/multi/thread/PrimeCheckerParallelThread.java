package multi.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * checking for prime numbers using threads.
 */
public class PrimeCheckerParallelThread extends Thread {

    private final int[] numbers;
    private final int start;
    private final int end;
    private boolean hasNonPrime;

    /**
     * constructor for each thread.
     *
     * @param numbers array of all numbers
     * @param start index of chunk's start
     * @param end index of chunk's end
     */
    public PrimeCheckerParallelThread(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    /**
     * checking that number is prime.
     *
     * @param number
     * @return true if its prime
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (!isPrime(numbers[i])) {
                hasNonPrime = true;
                break;
            }
        }
    }

    public boolean hasNonPrime() {
        return hasNonPrime;
    }

    /**
     * checking for existence of non-prime numbers.
     *
     * @param numbers array of numbers
     * @param numOfThreads number of threads that will be used for checking
     * @return result of checking
     * @throws InterruptedException
     */
    public static boolean containsNonPrime(int[] numbers, int numOfThreads) throws InterruptedException {
        int length = numbers.length;
        List<PrimeCheckerParallelThread> threads = new ArrayList<>(); // creating array of threads
        int chunkSize = length / numOfThreads; // size of the part for each thread

        // division into equal parts for threads execution
        for (int i = 0; i < numOfThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numOfThreads - 1) ? length : (i + 1) * chunkSize;
            PrimeCheckerParallelThread thread = new PrimeCheckerParallelThread(numbers, start, end);
            threads.add(thread);
            thread.start();
        }

        // checking that all threads dont have non-prime numbers
        for (PrimeCheckerParallelThread thread : threads){
            thread.join();
            if (thread.hasNonPrime) {
                return true;
            }
        }

        return false;
    }
}
