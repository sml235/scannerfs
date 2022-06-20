package com.sml.scanner.dao;

import com.sml.scanner.model.ScannedFile;

import java.util.List;

public interface FileDao {

    void saveAll(List<ScannedFile> scannedFiles);

    List<ScannedFile> getByName(String name);
}
