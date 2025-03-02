package multi.thread;


public class IsPrimeChecker {
    /**
     * checking if number is prime.
     *
     * @param number number for checking
     * @return true if prime
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
}
