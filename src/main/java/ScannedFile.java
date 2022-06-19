import jakarta.persistence.*;

@Entity
@Table(name = "scanned_files")
public class ScannedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "file_id")
    private int fileId;

    @Column(name = "cat_path")
    private String catPath;

    @Column(name = "file_name")
    private String fileName;

    public ScannedFile(String catPath, String fileName) {
        this.catPath = catPath;
        this.fileName = fileName;
    }

    public ScannedFile() {
    }

    public String getCatPath() {
        return catPath;
    }

    public void setCatPath(String catPath) {
        this.catPath = catPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
