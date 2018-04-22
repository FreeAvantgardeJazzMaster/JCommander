package sample;

import java.util.Date;

public class FileObject {
    private String name;
    private Long size;
    private Date date;
    private String type;

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Long getSize() {
        return size;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileObject(String name, Long size, Date date, String type){
        setDate(date);
        setName(name);
        setSize(size);
        setType(type);
    }
}
