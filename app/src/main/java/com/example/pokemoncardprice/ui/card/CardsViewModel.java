package com.example.pokemoncardprice.ui.card;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemoncardprice.models.CardItem;
import com.example.pokemoncardprice.net.CardAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsViewModel extends AndroidViewModel {
    private MutableLiveData<List<CardItem>> mCards;
    public static String playerTag;

    public CardsViewModel(Application application) {
        super(application);
        mCards = new MutableLiveData<>();
        List<CardItem> cardItems = new ArrayList<>();
        CardAPI.getCard(playerTag, getApplication().getApplicationContext(), response -> {
            mCards = retrieveData(response,cardItems);
            mCards.setValue(cardItems);
        }, error -> {
            mCards.setValue(null);
        });
        mCards.setValue(cardItems);
    }

    public LiveData<List<CardItem>> getCard(String playerTag) {
        System.out.println("coucou1");
        CardsViewModel.playerTag = playerTag;
        System.out.println("coucou2");
        mCards = new MutableLiveData<>();
        List<CardItem> cardItems = new ArrayList<>();
        CardAPI.getCard(playerTag, getApplication().getApplicationContext(), response -> {
            System.out.println("coucou3");
            mCards = retrieveData(response,cardItems);
            System.out.println("coucou4");
            mCards.setValue(cardItems);
        }, error -> {
            mCards.setValue(null);
        });
        return mCards;
    }

    public MutableLiveData<List<CardItem>> retrieveData(JSONObject response, List<CardItem> cardItems){
        try {
            JSONArray cardList = response.getJSONArray("data");
            for (int i = 0, size = cardList.length(); i < size; i++) {
                JSONObject cardItemJson = cardList.getJSONObject(i);
                String id = cardItemJson.getString("id");
                String name = cardItemJson.getString("name");
                String set = cardItemJson.getJSONObject("set").getString("name");
                String setImage = cardItemJson.getJSONObject("set").getJSONObject("images").getString("symbol");
                String cardMarketaverageSellPrice = " / ";
                String cardMarketavg1 = " / ";
                String cardMarketavg7 = " / ";
                String cardMarketavg30 = " / ";
                if(cardItemJson.has("cardmarket")) {
                    if (cardItemJson.getJSONObject("cardmarket").has("prices")) {
                        if(cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").has("trendPrice")){
                            cardMarketaverageSellPrice = cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").getString("trendPrice");
                        }  if(cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").has("avg1")){
                            cardMarketavg1 = cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").getString("avg1");
                        }
                        if(cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").has("avg7")){
                            cardMarketavg7 = cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").getString("avg7");
                        }if(cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").has("avg30")){
                            cardMarketavg30 = cardItemJson.getJSONObject("cardmarket").getJSONObject("prices").getString("avg30");
                        }
                    }
                }
                String tcgPlayerMarket = " / ";
                String tcgPlayerLow = " / ";
                String tcgPlayerMid = " / ";
                String tcgPlayerHigh = " / ";
                if(cardItemJson.has("tcgplayer")){
                    if(cardItemJson.getJSONObject("tcgplayer").has("prices")){
                        if(cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").has("holofoil")){
                            tcgPlayerMarket = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("market");
                            tcgPlayerLow = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("low");
                            tcgPlayerMid = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("mid");
                            tcgPlayerHigh = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("high");
                        }
                        else if (cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").has("normal")){
                            tcgPlayerMarket = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("market");
                            tcgPlayerLow = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("low");
                            tcgPlayerMid = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("mid");
                            tcgPlayerHigh = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("normal").getString("high");
                        }
                    }
                }
                String rarity = " / ";
                if(cardItemJson.has("rarity")) {
                    rarity = cardItemJson.getString("rarity");
                }
                else{
                    rarity = "no rarity";
                }
                CardItem cardItem = new CardItem(id, name, set,setImage,cardMarketaverageSellPrice,cardMarketavg1,cardMarketavg7,cardMarketavg30,tcgPlayerMarket,
                        tcgPlayerLow,tcgPlayerMid,tcgPlayerHigh,rarity);
                cardItems.add(cardItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mCards.setValue(null);
        }
        return mCards;
    }

}
