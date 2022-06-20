package com.sml.scanner.service;

import com.sml.scanner.model.ScannedFile;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

    void saveAll(List<Path> paths);

    List<ScannedFile> search(String name);
}
