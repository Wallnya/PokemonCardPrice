package com.pcp.pokemoncardprice.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ExtensionItem {
    private String id;
    private String name;
    private String image;
    private String releaseDate;

    public ExtensionItem(String id, String name, String image, String releaseDate){
        this.id = id;
        this.name = name;
        this.image = image;
        this.releaseDate = releaseDate;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public static final Comparator<ExtensionItem> byDate =new Comparator<ExtensionItem>() {
        @Override
        public int compare(ExtensionItem o1, ExtensionItem o2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            Date date2 = null;
            try {
                date = dateFormat.parse(o1.getReleaseDate());
                date2 = dateFormat.parse(o2.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date.compareTo(date2);
        }
    };
}
