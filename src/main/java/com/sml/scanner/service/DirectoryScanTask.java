package com.sml.scanner.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class DirectoryScanTask implements Runnable {

    private BlockingQueue<Path> queue;
    private List<Path> files;

    public DirectoryScanTask(BlockingQueue<Path> queue, List<Path> files) {
        this.queue = queue;
        this.files = files;
    }

    @Override
    public void run() {
        List<Path> paths;
        while (queue.peek() != null) {
            try {
                paths = Files.list(queue.take()).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
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
    }
}
