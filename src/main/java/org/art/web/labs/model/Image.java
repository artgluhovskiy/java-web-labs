package org.art.web.labs.model;

import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {

    public Image() {}

    public Image(Item item, String fileName) {
        this.item = item;
        this.fileName = fileName;
    }

    @Parent
    private Item item;

    @Column(name = "FILENAME")
    private String fileName;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Image{" +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
