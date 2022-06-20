package com.sml.scanner.service;

import com.sml.scanner.dao.FileDao;
import com.sml.scanner.dao.FileDaoImpl;
import com.sml.scanner.model.ScannedFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {
    private FileDao fileDao = new FileDaoImpl();

    @Override
    public void saveAll(List<Path> paths) {
        fileDao.saveAll(paths.stream()
                .map(path -> new ScannedFile(
                        path.getParent().toString(),
                        path.getFileName().toString())
                ).collect(Collectors.toList()));
    }

    @Override
    public List<ScannedFile> search(String name) {
        return fileDao.getByName(name);
    }
}
