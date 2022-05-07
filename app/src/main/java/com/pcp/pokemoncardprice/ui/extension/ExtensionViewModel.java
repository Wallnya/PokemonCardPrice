package com.pcp.pokemoncardprice.ui.extension;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.models.ExtensionItem;
import com.pcp.pokemoncardprice.net.CardAPI;
import com.pcp.pokemoncardprice.net.SetAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtensionViewModel extends AndroidViewModel {
    public MutableLiveData<List<ExtensionItem>> mExtension;

    public ExtensionViewModel(Application application) {
        super(application);
        mExtension = new MutableLiveData<>();
        List<ExtensionItem> extensionItems = new ArrayList<>();
        SetAPI.getExtension(getApplication().getApplicationContext(), response -> {
            mExtension = retrieveData(response,extensionItems);
            mExtension.setValue(extensionItems);
        }, error -> {
            mExtension.setValue(null);
        });
        mExtension.setValue(extensionItems);
    }

    public LiveData<List<ExtensionItem>> getExtension() {
        mExtension = new MutableLiveData<>();
        List<ExtensionItem> extensionItems = new ArrayList<>();
        SetAPI.getExtension(getApplication().getApplicationContext(), response -> {
            mExtension = retrieveData(response,extensionItems);
            mExtension.setValue(extensionItems);
        }, error -> {
            mExtension.setValue(null);
        });
        System.out.println("test :"+mExtension);
        return mExtension;
    }

    public MutableLiveData<List<ExtensionItem>> retrieveData(JSONObject response, List<ExtensionItem> extensionItems){
        try {
            JSONArray cardList = response.getJSONArray("data");
            for (int i = 0, size = cardList.length(); i < size; i++) {
                JSONObject cardItemJson = cardList.getJSONObject(i);
                String id = cardItemJson.getString("id");
                String name = cardItemJson.getString("name");
                String releaseDate = cardItemJson.getString("releaseDate");

                SimpleDateFormat spf = new SimpleDateFormat("yyyy/MM/dd");
                Date newDate = spf.parse(releaseDate);
                spf = new SimpleDateFormat("dd/MM/yyyy");
                String newDateString = spf.format(newDate);

                String image = cardItemJson.getJSONObject("images").getString("symbol");

                ExtensionItem extensionItem = new ExtensionItem(id, name, image, newDateString);
                extensionItems.add(extensionItem);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            mExtension.setValue(null);
        }
        return mExtension;
    }

}
