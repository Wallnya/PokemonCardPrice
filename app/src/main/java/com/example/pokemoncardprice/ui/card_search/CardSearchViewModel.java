package com.example.pokemoncardprice.ui.card_search;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import com.example.pokemoncardprice.net.core.TranslateCard;

public class CardSearchViewModel extends AndroidViewModel {
    public static String frenchName;
    public static String englishName;

    public CardSearchViewModel(Application application) {
        super(application);
    }

    public String getCardName(String playerTag, final VolleyCallback callback) {
        CardSearchViewModel.frenchName = playerTag;
        TranslateCard.getTranslate(playerTag,getApplication().getApplicationContext(), response -> {
            int indexBeginningName = response.indexOf("Nom anglais</th><td colspan=\"3\">")+32;
            String substring = response.substring(indexBeginningName, indexBeginningName+20);
            int indexEndName = substring.indexOf("<");
            String pokemonName = response.substring(indexBeginningName, indexBeginningName + indexEndName);
            CardSearchViewModel.englishName = pokemonName;
            callback.onSuccess(pokemonName);
        }, error -> {
            Toast.makeText(getApplication().getApplicationContext(), "Pas de carte pour "+playerTag, Toast.LENGTH_LONG).show();
        });
        return CardSearchViewModel.englishName;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }



}