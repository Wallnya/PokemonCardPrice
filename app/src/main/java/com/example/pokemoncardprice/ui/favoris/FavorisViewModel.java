package com.example.pokemoncardprice.ui.favoris;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemoncardprice.models.CardItem;
import com.example.pokemoncardprice.net.CardAPI;
import com.example.pokemoncardprice.net.CardInfoAPI;
import com.example.pokemoncardprice.ui.card_info.CardsInfoViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FavorisViewModel extends AndroidViewModel {
    private MutableLiveData<List<CardItem>> mCards;
    private MutableLiveData<CardItem> cardItems;

    public FavorisViewModel(Application application) {
        super(application);
        mCards = new MutableLiveData<>();
        mCards = retrieveData(mCards,"");
    }

    public MutableLiveData<CardItem> updateCard(String playerTag) {
        System.out.println("test1");
        System.out.println("test1.5"+this.getApplication().getApplicationContext().toString());
        CardInfoAPI.getCardInfo(playerTag,this.getApplication().getApplicationContext(), response -> {
            System.out.println("test2");
            cardItems = retrieveDataOneCard(response);
            System.out.println("test2.5");
        }, error -> {
            cardItems.setValue(null);
        });
        System.out.println("test3");
        //System.out.println("test4"+cardItems.getValue().getId());
        return cardItems;
    }

    public MutableLiveData<List<CardItem>> getCardInfo() {
        mCards = retrieveData(mCards,"");
        return mCards;
    }

    private MutableLiveData<List<CardItem>> retrieveData(MutableLiveData<List<CardItem>> mCards, String id){
        List<CardItem> cardItems = new ArrayList<>();
        String jsonString = read(getApplication().getApplicationContext(), "data.json");
        JSONObject obj;
        try {
            obj = new JSONObject(jsonString);
            JSONArray array=obj.getJSONArray("data");
            for(int i=0;i<array.length();i++){
                String extensionUrl = array.getJSONObject(i).getString("extensionImage").replace("\"","");
                CardItem card = new CardItem(array.getJSONObject(i).getString("id"),array.getJSONObject(i).getString("name"),
                        array.getJSONObject(i).getString("extension"),extensionUrl,array.getJSONObject(i).getJSONArray("prices").getJSONObject(0).getString("prix"),array.getJSONObject(i).getJSONArray("prices").getJSONObject(0).getString("date"), array.getJSONObject(i).getString("releasedDate"));
                cardItems.add(card);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mCards.setValue(cardItems);
        return mCards;
    }


    public MutableLiveData<CardItem> retrieveDataOneCard(JSONObject response){
        try {
            System.out.println("test5");
            JSONObject cardInfo = response.getJSONObject("data");
            String id = cardInfo.getString("id");
            String cardMarketaverageSellPrice = " / ";
            String date = " / ";
            if(cardInfo.has("cardmarket")) {
                if (cardInfo.getJSONObject("cardmarket").has("prices")) {
                    cardMarketaverageSellPrice = cardInfo.getJSONObject("cardmarket").getJSONObject("prices").getString("trendPrice");
                    date = cardInfo.getJSONObject("cardmarket").getString("updatedAt");
                }
            }
            System.out.println("test6");
            CardItem cardItem = new CardItem(id,cardMarketaverageSellPrice,date);
            cardItems.setValue(cardItem);
            System.out.println("test7");
        } catch (JSONException e) {
            e.printStackTrace();
            cardItems.setValue(null);
        }
        System.out.println("test8"+cardItems.getValue().getId());
        return cardItems;
    }

    public String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

}
