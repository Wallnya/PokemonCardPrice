package com.example.pokemonmartin.models;

public class CardItem {

    private String id;
    private String name;
    private String extension;
    private String extensionImage;

    public CardItem(String id, String name, String extension, String extensionImage) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.extensionImage = extensionImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getExtensionImage() {
        return extensionImage;
    }

    public void setExtensionImage(String extensionImage) {
        this.extensionImage = extensionImage;
    }
}
