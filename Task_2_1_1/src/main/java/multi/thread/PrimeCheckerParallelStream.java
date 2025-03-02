package multi.thread;

import java.util.Arrays;

/**
 *  checking for prime numbers using a parallel stream.
 */
public class PrimeCheckerParallelStream {
    /**
     * checking for the existence of non-prime numbers.
     *
     * @param numbers array of numbers
     * @return true if non-prime number will be found
     */
    public static boolean containsNonPrime(int[] numbers) {
        return Arrays.stream(numbers)
                .parallel()  // // connecting a parallel stream
                .anyMatch(number -> !IsPrimeChecker.isPrime(number));
    }
}
