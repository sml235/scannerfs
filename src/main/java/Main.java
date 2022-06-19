import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(final String[] args) throws Exception {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ScannedFile.class)
                .buildSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery("truncate table scanned_files").executeUpdate();
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            FileScanner scanner = new FileScannerMultithreadImpl();
            List<Path> paths = scanner.scanFiles();
            session.beginTransaction();
            paths.stream()
                    .map(path -> new ScannedFile(path.getParent().toString(), path.getFileName().toString()))
                    .forEach(session::persist);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}