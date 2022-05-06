package com.pcp.pokemoncardprice.ui.card_search;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import com.pcp.pokemoncardprice.net.core.TranslateCard;

import java.util.ArrayList;
import java.util.List;

public class CardSearchViewModel extends AndroidViewModel {
    public static String frenchName;
    public static String englishName;
    public List<String> research;

    public CardSearchViewModel(Application application) {
        super(application);
    }

    public String getCardName(String playerTag, final VolleyCallback callback) {
        research = new ArrayList<String>();
        research.add("Nom anglais</th>");
        research.add("Nom anglais\n</th>\n<td>");
        research.add("Nom anglais\n</th>\n<td><span lang=\"en\">");
        research.add("<th>Nom anglais\n" +
                "</th>\n" +
                "<td><span lang=\"en\" title=\"Nom anglais de la carte\">");

        CardSearchViewModel.frenchName = playerTag;
        int space = playerTag.indexOf(" ");
        if (space != -1)
            playerTag = UpperCaseEachWord(playerTag);
        if(playerTag.compareTo("Échange") == 0){
            playerTag = "Échange_(Diamant_%26_Perle_119)";
        }
        String finalPlayerTag = playerTag;
        TranslateCard.getTranslate(playerTag,getApplication().getApplicationContext(), response -> {
            int indexBeginningName = response.indexOf(research.get(0))+32;
            System.out.println("index 0:"+indexBeginningName);
            String substring = response.substring(indexBeginningName, indexBeginningName+20);
            System.out.println("sub 0:"+substring);
            int indexEndName = substring.indexOf("<");
            if(indexEndName != -1){
                String pokemonName = response.substring(indexBeginningName, indexBeginningName + indexEndName);
                CardSearchViewModel.englishName = pokemonName;
                callback.onSuccess(pokemonName);
            }
            else{
                //échange
                indexBeginningName = response.indexOf(research.get(3))+74;
                System.out.println("index 1:"+indexBeginningName);
                substring = response.substring(indexBeginningName, indexBeginningName+25);
                System.out.println("sub 1:"+substring);
                System.out.println("sub 1.1:"+substring.indexOf("en\">"));
                //poké ball
                if(substring.indexOf("</span>")== -1){
                    indexBeginningName = response.indexOf(research.get(2))+38;
                    System.out.println("index 2:"+indexBeginningName);
                    substring = response.substring(indexBeginningName, indexBeginningName+40);
                    System.out.println("sub 2:"+substring);
                }
                //normal
                if(substring.indexOf("</span>")== -1){
                    indexBeginningName = response.indexOf(research.get(1))+22;
                    System.out.println("index 3:"+indexBeginningName);
                    substring = response.substring(indexBeginningName, indexBeginningName+40);
                    System.out.println("sub 3:"+substring);
                }
                indexEndName = substring.indexOf("<");
                if(indexEndName != -1){
                    String pokemonName = response.substring(indexBeginningName, indexBeginningName + indexEndName);
                    System.out.println("pokemonName :"+pokemonName);
                    CardSearchViewModel.englishName = pokemonName;
                    callback.onSuccess(pokemonName);
                }
                else{
                    Toast.makeText(getApplication().getApplicationContext(), "Ce n'est pas un pokemon!", Toast.LENGTH_LONG).show();
                }
            }

        }, error -> {
            Toast.makeText(getApplication().getApplicationContext(), "Pas de carte pour "+ finalPlayerTag, Toast.LENGTH_LONG).show();
        });
        return CardSearchViewModel.englishName;
    }

    public String UpperCaseEachWord(String message){
        // stores each characters to a char array
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if(Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if(foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }

            else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        // convert the char array to the string
        message = String.valueOf(charArray);
        return message;
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }



}