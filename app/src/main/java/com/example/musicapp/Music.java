package com.example.musicapp;

public class Music {
    private String Name;
    private String Author;
    private int Image;
    private int File;
    private String Url;

    public Music(String name, String author, int image, int file, String url) {
        Name = name;
        Author = author;
        Image = image;
        File = file;
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
