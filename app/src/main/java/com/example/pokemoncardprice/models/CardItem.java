package com.example.pokemoncardprice.models;

import java.util.Date;
import java.util.HashMap;

public class CardItem {

    private String id;
    private String name;
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
    private HashMap<String, String> prices;
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
    public CardItem(String id, String name, String extension, String extensionImage, String cardMarketaverageSellPrice, String cardMarketavg1, String cardMarketavg7,
                    String cardMarketavg30, String tcgPlayerMarket, String tcgPlayerLow, String tcgPlayerMid, String tcgPlayerHigh, String rarity, String  cardImage,
                    String date) {
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
        this.cardImage = cardImage;
        this.date = date;
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

    public CardItem(String id, HashMap prices) {
        this.id = id;
        this.prices = prices;
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

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getExtensionImage() {
        return extensionImage;
    }

    public void setExtensionImage(String extensionImage) {
        this.extensionImage = extensionImage;
    }

    public String getcardMarketaverageSellPrice() { return cardMarketaverageSellPrice; }

    public void setcardMarketaverageSellPrice(String cardMarketaverageSellPrice) { this.cardMarketaverageSellPrice = cardMarketaverageSellPrice; }

    public String getcardMarketavg1() { return cardMarketavg1; }

    public void setcardMarketavg1(String cardMarketavg1) { this.cardMarketavg1 = cardMarketavg1; }

    public String getcardMarketavg7() { return cardMarketavg7; }

    public void setcardMarketavg7(String cardMarketavg7) { this.cardMarketavg7 = cardMarketavg7; }

    public String getcardMarketavg30() { return cardMarketavg30; }

    public void setcardMarketavg30(String cardMarketavg30) { this.cardMarketavg30 = cardMarketavg30; }

    public String getTcgPlayerMarket() { return tcgPlayerMarket; }

    public void setTcgPlayerMarket(String tcgPlayerMarket) { this.tcgPlayerMarket = tcgPlayerMarket; }

    public String getTcgPlayerLow() { return tcgPlayerLow; }

    public void setTcgPlayerLow(String tcgPlayerLow) { this.tcgPlayerLow = tcgPlayerLow; }

    public String getTcgPlayerMid() { return tcgPlayerMid; }

    public void setTcgPlayerMid(String tcgPlayerMid) { this.tcgPlayerMid = tcgPlayerMid; }

    public String getTcgPlayerHigh() { return tcgPlayerHigh; }

    public void setTcgPlayerHigh(String tcgPlayerHigh) { this.tcgPlayerHigh = tcgPlayerHigh; }

    public String getRarity() { return rarity; }

    public void setRarity(String rarity) { this.rarity = rarity; }

    public String getCardImage() { return cardImage; }

    public String getDate() { return date; }

    public HashMap<String, String> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<String, String> prices) {
        this.prices = prices;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }
}