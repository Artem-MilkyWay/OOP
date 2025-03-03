import multi.thread.PrimeChecker;
import multi.thread.PrimeCheckerParallelStream;
import multi.thread.PrimeCheckerParallelThread;
import multi.thread.PrimeCheckerSequential;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для всех трёх реализаций.
 */
public class PrimeCheckerTest {

    private final int [] nonPrimes1 = {1};
    private final int [] nonPrimes2 = {2, 5, 4};
    private final int [] emptyArray = {};

    /**
     * Тест 1: последовательная проверка.
     */
    @Test
    public void testSequentialExecution() {
        int[] largePrimeArray = generatePrimeArray(100000);
        long startTime = System.currentTimeMillis();
        boolean result1 = PrimeCheckerSequential.containsNonPrime(largePrimeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential execution time: " + (endTime - startTime) + " ms");
        assertFalse(result1); // on full of primes array
        // on arrays containing non-primes numbers
        assertTrue(PrimeCheckerSequential.containsNonPrime(nonPrimes1));
        assertTrue(PrimeCheckerSequential.containsNonPrime(nonPrimes2));
        // on empty array
        assertFalse(PrimeCheckerSequential.containsNonPrime(emptyArray));

    }

    /**
     * Тест 2: многопоточная проверка.
     */
    @Test
    public void testParallelExecution() {
        int[] largePrimeArray = generatePrimeArray(100000);
        int[] threadCounts = {2, 4, 8}; // примеры с разным числом потоков

        for (int numThreads : threadCounts) {
            long startTime = System.currentTimeMillis();
            try {
                boolean result2 = PrimeCheckerParallelThread
                        .containsNonPrime(largePrimeArray, numThreads);
                long endTime = System.currentTimeMillis();
                System.out.println("Parallel execution with "
                        + numThreads + " " + "threads time: " + (endTime - startTime) + " ms");
                assertFalse(result2);
                // on arrays containing non-primes numbers
                assertTrue(PrimeCheckerParallelThread.containsNonPrime(nonPrimes1, numThreads));
                assertTrue(PrimeCheckerParallelThread.containsNonPrime(nonPrimes2, numThreads));
                // on empty array
                assertFalse(PrimeCheckerParallelThread.containsNonPrime(emptyArray, numThreads));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *  Тест 3: использование параллельных потоков.
     */
    @Test
    public void testParallelStreamExecution() {
        int[] largePrimeArray = generatePrimeArray(100000);
        long startTime = System.currentTimeMillis();
        boolean result3 = PrimeCheckerParallelStream.containsNonPrime(largePrimeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Parallel execution with parallelStream time: "
                + (endTime - startTime) + " ms");
        assertFalse(result3);
        // on arrays containing non-primes numbers
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(nonPrimes1));
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(nonPrimes2));
        // on empty array
        assertFalse(PrimeCheckerParallelStream.containsNonPrime(emptyArray));
    }

    /**
     * Генератор массива простых чисел.
     *
     * @param size размер массива
     * @return массив простых чисел
     */
    private static int[] generatePrimeArray(int size) {
        int[] primes = new int[size];
        int count = 0;
        int number = 2;
        while (count < size) {
            if (PrimeChecker.isPrime(number)) {
                primes[count] = number;
                count++;
            }
            number++;
        }
        return primes;
    }
}
