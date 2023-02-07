import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    int processorCount = Runtime.getRuntime().availableProcessors();

    ExecutorService executor1 = Executors.newFixedThreadPool(processorCount);
    ExecutorService executor2 = Executors.newScheduledThreadPool(processorCount);
    ExecutorService executor3 = Executors.newWorkStealingPool(); // java-8 API
    ForkJoinPool forkJoinPool = new ForkJoinPool(processorCount);
}