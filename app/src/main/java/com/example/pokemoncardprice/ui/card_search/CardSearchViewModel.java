package com.example.pokemoncardprice.ui.card_search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.pokemoncardprice.net.core.TranslateCard;


public class CardSearchViewModel extends AndroidViewModel {
    public static String playerTag;

    public CardSearchViewModel(Application application) {
        super(application);
    }

    public String getCardName(String playerTag, final VolleyCallback callback) {
        TranslateCard.getTranslate(playerTag,getApplication().getApplicationContext(), response -> {
            int test2 = response.indexOf("Nom anglais</th><td colspan=\"3\">")+32;
            System.out.println("quel index: "+test2);
            String test3 = response.substring(test2,test2+20);
            System.out.println("Résultat du substring :"+test3);

            int test4 = test3.indexOf("<");
            System.out.println("quel index: "+test4);
            String pokemonName = response.substring(test2,test2+test4);
            System.out.println("Résultat du substring :"+ pokemonName);
            System.out.println("test2+"+response.isEmpty());
            CardSearchViewModel.playerTag = pokemonName;
            System.out.println("Normalement ici à la fin, on devrait avoir cette valeur"+CardSearchViewModel.playerTag);
            callback.onSuccess(pokemonName);

        }, error -> {
            System.out.println("error");
        });
        return CardSearchViewModel.playerTag;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }



}