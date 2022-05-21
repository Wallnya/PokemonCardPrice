package com.pcp.pokemoncardprice.ui.card_by_set;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.net.CardAPI;
import com.pcp.pokemoncardprice.net.CardBySetAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardsBySetViewModel extends AndroidViewModel {
    public MutableLiveData<List<CardItem>> mCards;
    public static String playerTag;
    public static String setId;


    public CardsBySetViewModel(Application application) {
        super(application);
        mCards = new MutableLiveData<>();
    }

    public LiveData<List<CardItem>> getCardbySet(String setId) {
        CardsBySetViewModel.setId = setId;
        mCards = new MutableLiveData<>();
        List<CardItem> cardItems = new ArrayList<>();
        CardBySetAPI.getCardBySet(setId, getApplication().getApplicationContext(), response -> {
            mCards = retrieveData(response,cardItems);
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
                String releasedDate = cardItemJson.getJSONObject("set").getString("releaseDate");

                SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
                Date newDate = spf.parse(releasedDate);
                spf = new SimpleDateFormat("dd/MM/yyyy");
                String newDateString = spf.format(newDate);

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
                            if(cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").has("market")){
                                tcgPlayerMarket = cardItemJson.getJSONObject("tcgplayer").getJSONObject("prices").getJSONObject("holofoil").getString("market");
                            }
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
                        tcgPlayerLow,tcgPlayerMid,tcgPlayerHigh,rarity,newDateString);
                cardItems.add(cardItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mCards.setValue(null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mCards;
    }

}
