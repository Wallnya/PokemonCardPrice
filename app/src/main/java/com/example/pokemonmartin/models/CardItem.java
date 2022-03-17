package com.example.pokemonmartin.models;

import java.util.List;

public class CardItem {

    private String id;
    private String name;
    private String extension;

    public CardItem(String id, String name, String extension) {
        this.id = id;
        this.name = name;
        this.extension = extension;

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
}
