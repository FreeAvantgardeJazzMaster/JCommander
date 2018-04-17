package sample;

import java.util.Date;

public class FileObject {
    private String name;
    private String size;
    private Date date;

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getSize() {
        return size;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public FileObject(String name, String size, Date date){
        setDate(date);
        setName(name);
        setSize(size);
    }
}
