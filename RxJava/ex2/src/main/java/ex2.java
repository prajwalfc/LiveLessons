import tests.ReactorTests;
import tests.RxJavaTests;
import tests.StreamsTests;
import utils.DownloadUtils;
import utils.Options;
import utils.RunTimer;

/**
 * This example shows how to download many images from a remote web
 * server via several different Java concurrency/parallelism
 * frameworks, including the parallel streams, RxJava, and Reactor.
 * It also compares the performance of the Java parallel streams
 * framework with and without the {@code ForkJoinPool.ManagedBlocker}
 * interface and the Java common fork-join pool.
 */
public class ex2 {
    /**
     * Logging tag.
     */
    private static final String TAG = ex2.class.getName();

    /**
     * The Java execution environment requires a static {@code main()}
     * entry point method to run the app.
     */
    public static void main(String[] args) {
        System.out.println("Entering the download tests program with "
                           + Runtime.getRuntime().availableProcessors()
                           + " cores available");

        // Initializes the Options singleton.
        Options.instance().parseArgs(args);

        // Warm up the common fork-join pool.
        warmUpThreadPool();

        /*

        // Runs the tests using the using the Java fork-join
        // framework's default behavior, which does not add any new
        // worker threads to the pool when blocking on I/O occurs.
        StreamsTests.runParallelStreams
            (DownloadUtils::downloadAndStoreImage,
             "testDefaultDownloadBehavior()");

        // Run the tests using the using the Java fork-join
        // framework's {@code ManagedBlocker} mechanism, which adds
        // new worker threads to the pool adaptively when blocking on
        // I/O occurs.
        StreamsTests.runParallelStreams
            (DownloadUtils::downloadAndStoreImageMB,
             "testAdaptiveMBDownloadBehavior()");

        // Run the tests using the using the {@code BlockingTask}
        // wrapper for the Java fork-join framework's {@code
        // ManagedBlocker} mechanism, which adds new worker threads to
        // the pool adaptively when blocking on I/O occurs.
        StreamsTests.runParallelStreams
            (DownloadUtils::downloadAndStoreImageBT,
             "testAdaptiveBTDownloadBehavior()");

        // Run the tests using RxJava's flatMap() parallelism
        // mechanism along with the {@code BlockingTask} wrapper for
        // the Java fork-join framework's {@code ManagedBlocker}
        // mechanism, which adds new worker threads to the pool
        // adaptively when blocking on I/O occurs.
        RxJavaTests.runFlatMap
            (DownloadUtils::downloadAndStoreImageBT,
             "testAdaptiveBTDownloadBehaviorRxflatMap()");

        // Run the tests using RxJava's ParallelFlowable mechanism
        // along with the {@code BlockingTask} wrapper for the Java
        // fork-join framework's {@code ManagedBlocker} mechanism,
        // which adds new worker threads to the pool adaptively when
        // blocking on I/O occurs.
        RxJavaTests.runParallelFlowable
            (DownloadUtils::downloadAndStoreImageBT,
             "testAdaptiveBTDownloadBehaviorRxParallelFlowable()");
        */

        // Run the tests using Reactor's flatMap() parallelism
        // mechanism along with the {@code BlockingTask} wrapper for
        // the Java fork-join framework's {@code ManagedBlocker}
        // mechanism, which adds new worker threads to the pool
        // adaptively when blocking on I/O occurs.
        ReactorTests.runFlatMap
            (DownloadUtils::downloadAndStoreImageBT,
             "testAdaptiveBTDownloadBehaviorReactorflatMap()");

        // Run the tests using Reactor's ParallelFlux mechanism along
        // with the {@code BlockingTask} wrapper for the Java
        // fork-join framework's {@code ManagedBlocker} mechanism,
        // which adds new worker threads to the pool adaptively when
        // blocking on I/O occurs.
        ReactorTests.runParallelFlux
            (DownloadUtils::downloadAndStoreImageBT,
             "testAdaptiveBTDownloadBehaviorReactorParallelFlux()");

        ReactorTests.runParallelFlux1
                (DownloadUtils::downloadAndStoreImageBT,
                        "testAdaptiveBTDownloadBehaviorReactorParallelFlux1()");

        // Print the results.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Leaving the download tests program");
    }

    /**
     * This method warms up the default thread pool.
     */
    private static void warmUpThreadPool() {
        StreamsTests.testDownloadStreams
            (DownloadUtils::downloadAndStoreImage,
             "warmup");
    }
}
