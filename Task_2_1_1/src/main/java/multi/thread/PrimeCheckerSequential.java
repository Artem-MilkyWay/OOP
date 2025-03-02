package multi.thread;

/**
 * sequential checking for prime numbers.
 */
public class PrimeCheckerSequential {
    /**
     * checking that array includes not a prime number.
     *
     * @param numbers array of numbers
     * @return result of checking
     */
    public static boolean containsNonPrime(int [] numbers) {
        for (int number : numbers) {
            if (!IsPrimeChecker.isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
