package sample;

import java.io.File;

public class FileToDelete {
    private String name;
    private File source;

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileToDelete(String name, File source){
        setName(name);
        setSource(source);
    }
}
