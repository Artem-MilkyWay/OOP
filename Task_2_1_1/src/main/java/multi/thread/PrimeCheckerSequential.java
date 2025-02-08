package multi.thread;

/**
 * sequential checking for prime numbers.
 */
public class PrimeCheckerSequential {

    /**
     *checking that a number is prime.
     *
     * @param number the number to be checked
     * @return result of checking
     */
    public static boolean isPrime(int number){
        if (number <= 1) {
            return false;
        }
        for (int i=2; i<=Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * checking that array includes not a prime number.
     *
     * @param numbers array of numbers
     * @return result of checking
     */
    public static boolean containsNonPrime(int [] numbers)
    {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
