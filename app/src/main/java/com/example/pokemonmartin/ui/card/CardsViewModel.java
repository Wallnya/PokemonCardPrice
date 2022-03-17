package com.example.pokemonmartin.ui.card;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokemonmartin.models.CardItem;
import com.example.pokemonmartin.net.CardAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsViewModel extends AndroidViewModel {
    private MutableLiveData<List<CardItem>> mCards = null;
    public static String playerTag;

    public CardsViewModel(Application application) {
        super(application);
        mCards = new MutableLiveData<>();
        List<CardItem> cardItems = new ArrayList<>();
        CardAPI.getCard(playerTag, getApplication().getApplicationContext(), response -> {
            try {
                JSONArray cardList = response.getJSONArray("data");
                for (int i = 0, size = cardList.length(); i < size; i++) {
                    JSONObject cardItemJson = cardList.getJSONObject(i);
                    String id = cardItemJson.getString("id");
                    String set = cardItemJson.getJSONObject("set").getString("name");
                    CardItem cardItem = new CardItem(id, playerTag, set);
                    cardItems.add(cardItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mCards.setValue(null);
            }
            mCards.setValue(cardItems);
        }, error -> {
            mCards.setValue(null);
        });
        mCards.setValue(cardItems);
    }

    public LiveData<List<CardItem>> getCard(String playerTag) {
        CardsViewModel.playerTag = playerTag;
        mCards = new MutableLiveData<>();
        List<CardItem> cardItems = new ArrayList<>();
        CardAPI.getCard(playerTag, getApplication().getApplicationContext(), response -> {
            try {
                JSONArray cardList = response.getJSONArray("data");
                for (int i = 0, size = cardList.length(); i < size; i++) {
                    JSONObject cardItemJson = cardList.getJSONObject(i);
                    String id = cardItemJson.getString("id");
                    String set = cardItemJson.getJSONObject("set").getString("name");
                    CardItem cardItem = new CardItem(id, playerTag, set);
                    cardItems.add(cardItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mCards.setValue(null);
            }
            mCards.setValue(cardItems);
        }, error -> {
            mCards.setValue(null);
        });
        return mCards;
    }

    public LiveData<List<CardItem>> getRankings() {
        return mCards;
    }
}
