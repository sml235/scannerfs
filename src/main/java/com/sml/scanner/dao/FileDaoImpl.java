package com.sml.scanner.dao;

import com.sml.scanner.model.ScannedFile;
import com.sml.scanner.service.FileScanner;
import com.sml.scanner.service.FileScannerMultithreadImpl;
import com.sml.scanner.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class FileDaoImpl implements FileDao {

    private SessionFactory factory = SessionFactoryUtil.getSessionFactory();

    @Override
    public void saveAll(List<ScannedFile> scannedFiles) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createNativeQuery("truncate table scanned_files").executeUpdate();
        FileScanner scanner = new FileScannerMultithreadImpl();
        scannedFiles.stream()
                .forEach(session::persist);
        session.getTransaction().commit();
    }

    @Override
    public List<ScannedFile> getByName(String name) {
        List<ScannedFile> scannedFiles;
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query<ScannedFile> query = session.createQuery(
                "from ScannedFile where fileName like :name", ScannedFile.class);
        query.setParameter("name", "%" + name + "%");
        query.setMaxResults(10);
        scannedFiles = query.list();
        session.getTransaction().commit();
        return scannedFiles;
    }
}
