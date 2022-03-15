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
    private MutableLiveData<CardItem> mCards;

    public CardsViewModel(Application application) {
        super(application);
    }

    public LiveData<CardItem> getLastPlayer() {
        return mCards;
    }

    public LiveData<CardItem> getCard(String playerTag) {
        mCards = new MutableLiveData<>();

        CardAPI.getCard(playerTag, getApplication().getApplicationContext(), response -> {
            try {
                String name = response.getJSONObject("data").getString("name");
                CardItem cardItem = new CardItem(playerTag, name);
                mCards.setValue(cardItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mCards.setValue(null);
            });
        return mCards;

    }
}
