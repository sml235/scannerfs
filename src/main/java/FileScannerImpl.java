import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileScannerImpl implements FileScanner {
    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");
    public static final Path START_CATALOG = Path.of(CONFIG.getString("start.catalog"));
    ArrayList<Path> files = new ArrayList<>();
    Queue<Path> queue = new LinkedList<>();

    @Override
    public List<Path> scanFiles() {

        queue.add(START_CATALOG);
        while (queue.peek() != null) {
            List<Path> paths;
            try {
                paths = Files.list(queue.poll()).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    queue.add(path);
                } else {
                    files.add(path);
                }
            });
        }

        return files;
    }
}
