package sample;

import java.io.File;

public class FileToCopy {
    private String name;
    private File source;
    private File dest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public File getDest() {
        return dest;
    }

    public void setDest(File dest) {
        this.dest = dest;
    }

    public FileToCopy(String name, File source, File dest){
        setDest(dest);
        setName(name);
        setSource(source);
    }
}
