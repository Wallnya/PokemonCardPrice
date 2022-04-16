package com.example.pokemoncardprice.ui.card_info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentCardinfoBinding;
import com.example.pokemoncardprice.jsonreader.JsonReader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class CardsInfoFragment extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private JsonReader jsonReader = new JsonReader();

    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardinfoBinding binding;
    private String id;
    private String name;
    private String extension;
    private String extensionImage;
    private String cardMarketaverageSellPrice;
    private String date;
    private String releasedDate;

    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentCardinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardsInfoViewModel.getLastPlayer().observe(getViewLifecycleOwner(), cardinfoItem -> {
            if (!cardinfoItem.equals(null)) {
                id = cardinfoItem.getId();
                name = cardinfoItem.getName();
                extension = cardinfoItem.getExtension();
                extensionImage = cardinfoItem.getExtensionImage();
                releasedDate = cardinfoItem.getReleasedDate();
                cardMarketaverageSellPrice = cardinfoItem.getcardMarketaverageSellPrice();
                date = cardinfoItem.getDate();
                binding.pokemonname.setText(cardinfoItem.getName() + " - " + cardinfoItem.getRarity());
                String str = cardinfoItem.getcardMarketaverageSellPrice()+"€";
                binding.cardMarketAverageTextFill.setText(str);
                str = cardinfoItem.getcardMarketavg1()+"€";
                binding.cardMarketAvg1Fill.setText(str);
                str = cardinfoItem.getcardMarketavg7()+"€";
                binding.cardMarketAvg2Fill.setText(str);
                str = priceChecking(cardinfoItem.getTcgPlayerMarket());
                binding.tcgMarketMarketTextFill.setText(str);
                str = priceChecking(cardinfoItem.getTcgPlayerLow());
                binding.tcgMarketLowFill.setText(str);
                Picasso.get().load(cardinfoItem.getCardImage()).into(binding.cardImage);
                binding.Stats.setText("Stats (" + cardinfoItem.getDate() + ")");
                if(cardinfoItem.getcardMarketaverageSellPrice().equals("0")){
                    binding.button2.setBackgroundColor(getResources().getColor(R.color.blue));
                    binding.button2.setClickable(false);
                }
            }
        });

        //Changement du nom du bouton en fonction de si l'id est déjà présent dans le fichier ou non
        buttonChecking();

        //Action du bouton
        binding.button2.setOnClickListener(v -> {
            boolean isFilePresent2 = jsonReader.isFilePresent(getActivity(), "data.json");
            //On vérifie que le fichier existe bien
            if(isFilePresent2) {
                //On récupère le string du json
                String jsonString = jsonReader.read(getActivity(), "data.json");
                JSONObject obj = null;
                Boolean alreadyfound = false;
                try {
                    obj = new JSONObject(jsonString);
                    JSONArray array=obj.getJSONArray("data");
                    for(int i=0;i<array.length();i++){
                        if (array.getJSONObject(i).getString("id").equals(id))
                            alreadyfound = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Si la carte ne fait pas parti des sauvegardes
                if(!alreadyfound){
                    try {
                        //On crée la carte avec les infos intéressantes
                        JSONObject pokemon = new JSONObject();
                        JSONObject prices = new JSONObject();
                        JSONArray jsonArrayInter = new JSONArray();
                        try {
                            pokemon.put("id", id);
                            pokemon.put("name", name);
                            pokemon.put("extension", extension);
                            pokemon.put("extensionImage", extensionImage);
                            pokemon.put("releasedDate", releasedDate);
                            prices.put("date",date);
                            prices.put("prix",cardMarketaverageSellPrice);
                            jsonArrayInter.put(prices);
                            pokemon.put("prices",jsonArrayInter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = new JSONObject(jsonString);
                        JSONArray array=obj.getJSONArray("data");
                        array.put(array.length(),pokemon);
                        jsonReader.writeToFile(obj.toString(),getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), name+" a été ajouté à la collection", Toast.LENGTH_LONG).show();
                    buttonChecking();
                }
                //Sinon on le retire des favoris
                else{
                    try {
                        int position =0;
                        obj = new JSONObject(jsonString);
                        JSONArray array=obj.getJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            if (array.getJSONObject(i).getString("id").equals(CardsInfoViewModel.playerTag)) {
                                position = i;
                            }
                        }
                        array.remove(position);
                        jsonReader.writeToFile(obj.toString(),getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Pokemon retiré de la collection", Toast.LENGTH_LONG).show();
                    buttonChecking();
                }
            }
            //création du fichier si ça n'existe pas
            else {
                boolean isFileCreated = jsonReader.create(getActivity(), "data.json", "{ \"data\": []}");
                Toast.makeText(getContext(), "Le fichier est créé, veuillez recliquer pour l'ajouter aux favoris", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    private void buttonChecking(){
        boolean isFilePresent = jsonReader.isFilePresent(getActivity(), "data.json");
        if(isFilePresent) {
            //On récupère le string du json
            String jsonString = jsonReader.read(getActivity(), "data.json");
            JSONObject obj = null;
            Boolean alreadyfound = false;
            try {
                obj = new JSONObject(jsonString);
                JSONArray array=obj.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    if (array.getJSONObject(i).getString("id").equals(CardsInfoViewModel.playerTag))
                        alreadyfound = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(alreadyfound){
                binding.button2.setText("Retirer de la collection");
            }
            else{
                binding.button2.setText("Ajouter à la collection");
            }
        }
    }

    public String priceChecking(String valeur) {
        String str = "null";
        if (valeur != null){
            if (!valeur.equals(" / ")) {
                str = "" + df.format(Float.valueOf(valeur) / 1.1f) + "€";
            }
        }
        return str;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}