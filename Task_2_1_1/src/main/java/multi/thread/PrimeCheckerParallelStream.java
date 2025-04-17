package multi.thread;

import java.util.Arrays;

/**
 *  check for prime numbers using a parallel stream.
 */
public class PrimeCheckerParallelStream {
    /**
     * check that a least one non-prime number exists.
     *
     * @param numbers array of numbers
     * @return true if non-prime number is found
     */
    public static boolean containsNonPrime(int[] numbers) {
        return Arrays.stream(numbers)
                .parallel()  // connecting a parallel stream
                .anyMatch(number -> !PrimeChecker.isPrime(number));
    }
}
