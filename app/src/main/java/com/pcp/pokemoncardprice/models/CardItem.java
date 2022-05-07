package com.pcp.pokemoncardprice.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class CardItem {

    private String id;
    private String name;
    private String frenchName;
    private String extension;
    private String extensionImage;
    private String  cardMarketaverageSellPrice;
    private String  cardMarketavg1;
    private String  cardMarketavg7;
    private String  cardMarketavg30;
    private String  tcgPlayerMarket;
    private String  tcgPlayerLow;
    private String  tcgPlayerMid;
    private String  tcgPlayerHigh;
    private String  rarity;
    private String  cardImage;
    private String  date;
    private String releasedDate;

    public CardItem(String id, String name, String extension, String extensionImage, String cardMarketaverageSellPrice, String cardMarketavg1, String cardMarketavg7,
                    String cardMarketavg30, String tcgPlayerMarket, String tcgPlayerLow, String tcgPlayerMid, String tcgPlayerHigh, String rarity, String releasedDate) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.extensionImage = extensionImage;
        this.cardMarketaverageSellPrice = cardMarketaverageSellPrice;
        this.cardMarketavg1 = cardMarketavg1;
        this.cardMarketavg7 = cardMarketavg7;
        this.cardMarketavg30 = cardMarketavg30;
        this.tcgPlayerMarket = tcgPlayerMarket;
        this.tcgPlayerLow = tcgPlayerLow;
        this.tcgPlayerMid = tcgPlayerMid;
        this.tcgPlayerHigh = tcgPlayerHigh;
        this.rarity = rarity;
        this.releasedDate = releasedDate;
    }

    public CardItem(String id, String name, String extension, String extensionImage, String cardMarketaverageSellPrice, String date, String releasedDate) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.extensionImage = extensionImage;
        this.cardMarketaverageSellPrice = cardMarketaverageSellPrice;
        this.date = date;
        this.releasedDate = releasedDate;
    }

    public CardItem(String id, String cardMarketaverageSellPrice, String date) {
        this.id = id;
        this.cardMarketaverageSellPrice = cardMarketaverageSellPrice;
        this.date = date;
    }

    public CardItem(String id, String name, String set, String setImage, String cardMarketaverageSellPrice, String cardMarketavg1, String cardMarketavg7, String cardMarketavg30, String tcgPlayerMarket, String tcgPlayerLow, String tcgPlayerMid, String tcgPlayerHigh, String rarity, String cardImage, String date, String newDateString) {
        this.id = id;
        this.name = name;
        this.extension = set;
        this.extensionImage = setImage;
        this.cardMarketaverageSellPrice = cardMarketaverageSellPrice;
        this.cardMarketavg1 = cardMarketavg1;
        this.cardMarketavg7 = cardMarketavg7;
        this.cardMarketavg30 = cardMarketavg30;
        this.tcgPlayerMarket = tcgPlayerMarket;
        this.tcgPlayerLow = tcgPlayerLow;
        this.tcgPlayerMid = tcgPlayerMid;
        this.tcgPlayerHigh = tcgPlayerHigh;
        this.rarity = rarity;
        this.cardImage = cardImage;
        this.date = date;
        this.releasedDate = newDateString;
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

    public String getExtensionImage() {
        return extensionImage;
    }

    public String getcardMarketaverageSellPrice() { return cardMarketaverageSellPrice; }

    public String getcardMarketavg1() { return cardMarketavg1; }

    public String getcardMarketavg7() { return cardMarketavg7; }

    public String getTcgPlayerMarket() { return tcgPlayerMarket; }


    public String getTcgPlayerLow() { return tcgPlayerLow; }

    public String getRarity() { return rarity; }

    public String getCardImage() { return cardImage; }

    public String getDate() { return date; }

    public String getReleasedDate() {
        return releasedDate;
    }

    public static final Comparator<CardItem> byDate =new Comparator<CardItem>() {
        @Override
        public int compare(CardItem o1, CardItem o2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            Date date2 = null;
            try {
                date = dateFormat.parse(o1.getReleasedDate());
                date2 = dateFormat.parse(o2.getReleasedDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date.compareTo(date2);
        }
    };
}