package com.example.pokemoncardprice.ui.card_info;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pokemoncardprice.databinding.FragmentCardinfoBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
public class CardsInfoFragment extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardinfoBinding binding;
    private String id;
    private String name;
    private String extension;
    private String extensionImage;
    private String cardMarketaverageSellPrice;
    private String date;
    private String releasedDate;

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
                str = verification(cardinfoItem.getTcgPlayerMarket());
                binding.tcgMarketMarketTextFill.setText(str);
                str = verification(cardinfoItem.getTcgPlayerLow());
                binding.tcgMarketLowFill.setText(str);
                Picasso.get().load(cardinfoItem.getCardImage()).into(binding.cardImage);
                binding.Stats.setText("Stats (" + cardinfoItem.getDate() + ")");
            }
        });
        binding.button2.setOnClickListener(v -> {
            boolean isFilePresent = isFilePresent(getActivity(), "data.json");
            //On vérifie que le fichier existe bien
            if(isFilePresent) {
                //On récupère le string du json
                String jsonString = read(getActivity(), "data.json");
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
                        JSONArray jsonArray = new JSONArray();
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
                        jsonArray.put(pokemon);
                        obj = new JSONObject(jsonString);
                        JSONArray array=obj.getJSONArray("data");
                        array.put(array.length(),pokemon);
                        writeToFile(obj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), name+" a été ajouté aux favoris", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Déjà dans les favoris", Toast.LENGTH_LONG).show();
                }
            }
            //création du fichier si ça n'existe pas
            else {
                boolean isFileCreated = create(getActivity(), "data.json", "{ \"data\": []}");
                Toast.makeText(getContext(), "Le fichier est créé, veuillez recliquer pour l'ajouter aux favoris", Toast.LENGTH_LONG).show();

            }
        });
        return root;
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("data.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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

    private boolean create(Context context, String fileName, String jsonString){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        System.out.println("path:"+path);
        File file = new File(path);
        return file.exists();
    }

    public String verification(String valeur){
        String str ="null";
        if(!valeur.equals(" / ")){
            str = ""+df.format(Float.valueOf(valeur)/1.1f)+"€";
        }
        return str;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}