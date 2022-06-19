import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class FileScannerMultithreadImpl implements FileScanner {
    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");
    public static final Path START_CATALOG = Path.of(CONFIG.getString("start.catalog"));
    public static final Integer NUM_THREADS = Integer.valueOf(CONFIG.getString("threads.count"));
    private List<Path> files = new CopyOnWriteArrayList<>();
    private BlockingQueue<Path> queue = new LinkedBlockingQueue<>();

    public List<Path> scanFiles() {

        queue.add(START_CATALOG);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(new DirectoryScanTask(queue, files));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!executorService.isTerminated()) {
            executorService.shutdownNow();
        }
        return files;
    }
}

