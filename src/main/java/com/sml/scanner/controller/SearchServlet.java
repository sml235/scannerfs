package com.sml.scanner.controller;

import com.sml.scanner.model.ScannedFile;
import com.sml.scanner.service.FileScanner;
import com.sml.scanner.service.FileScannerMultithreadImpl;
import com.sml.scanner.service.FileService;
import com.sml.scanner.service.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private FileService fileService = new FileServiceImpl();
    private FileScanner fileScanner = new FileScannerMultithreadImpl();

    @Override
    public void init() throws ServletException {
        List<Path> paths = fileScanner.scanFiles();
        fileService.saveAll(paths);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<ScannedFile> foundFiles = fileService.search(name);
        req.setAttribute("foundFiles", foundFiles);
        doGet(req, resp);
    }
}

