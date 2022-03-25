package com.example.pokemoncardprice.ui.favoris;

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

    public FavorisViewModel(@NonNull Application application) {
        super(application);
        mCards = new MutableLiveData<>();
        mCards = retrieveData(mCards,"");
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
                        array.getJSONObject(i).getString("extension"),extensionUrl,array.getJSONObject(i).getJSONObject("prices").getString("prix"),array.getJSONObject(i).getJSONObject("prices").getString("date"));
                cardItems.add(card);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mCards.setValue(cardItems);
        return mCards;
    }

    private String read(Context context, String fileName) {
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
