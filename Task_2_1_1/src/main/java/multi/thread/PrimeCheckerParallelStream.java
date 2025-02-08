package multi.thread;

import java.util.Arrays;

/**
 *  checking for prime numbers using a parallel stream.
 */
public class PrimeCheckerParallelStream {

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static boolean containsNonPrime(int[] numbers) {
        return Arrays.stream(numbers)
                .parallel()  // // connecting a parallel stream
                .anyMatch(number -> !isPrime(number));
    }
}
