package com.example.pokemoncardprice.ui.favoris;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentFavorisBinding;
import com.example.pokemoncardprice.jsonreader.JsonReader;
import com.example.pokemoncardprice.models.CardItem;
import com.example.pokemoncardprice.models.VerticalSpacingDecoration;
import com.example.pokemoncardprice.ui.card_info.CardsInfoViewModel;
import com.example.pokemoncardprice.ui.graph.GraphViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class FavorisFragment extends Fragment {

    private FavorisViewModel favorisViewModel;
    private GraphViewModel graphViewModel;
    private CardsInfoViewModel cardsInfoViewModel;
    private JsonReader jsonReader = new JsonReader();

    private FragmentFavorisBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 24;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        favorisViewModel =
                new ViewModelProvider(requireActivity()).get(FavorisViewModel.class);

        graphViewModel =
                new ViewModelProvider(requireActivity()).get(GraphViewModel.class);

        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentFavorisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));

        favorisViewModel.getCardInfo().observe(getViewLifecycleOwner(), cardListItems -> {
            FavorisListItemAdapter adapter = new FavorisListItemAdapter(cardListItems);
            adapter.setClickListener((view, position) -> {
                CardItem selectedCardItem = cardListItems.get(position);

                graphViewModel.getCardInfo(selectedCardItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    if (cardItem != null) {
                        Navigation.findNavController(root).navigate(R.id.action_favorisFragment2_to_navigation_dashboard);
                    } else {
                        Toast.makeText(getContext(), "Une erreur est parvenue pendant la recherche de la carte", Toast.LENGTH_LONG).show();
                    }
                });
            });
            recyclerView.setAdapter(adapter);
        });

        String jsonString = jsonReader.read(getActivity(), "data.json");
        if(jsonString!=null) {
            binding.button.setOnClickListener(v -> {
                JSONObject obj;
                try {
                    obj = new JSONObject(jsonString);
                    JSONArray array = obj.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        int finalI = i;

                        cardsInfoViewModel.updateCard(array.getJSONObject(i).getString("id")).observe(getViewLifecycleOwner(), cardItems -> {
                            JSONObject prices = new JSONObject();
                            int longeur = 0;
                            try {
                                longeur = array.getJSONObject(finalI).getJSONArray("prices").length();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                boolean found = false;
                                for (int j = 0; j < array.getJSONObject(finalI).getJSONArray("prices").length(); j++) {
                                    if (cardItems.getDate().equals(array.getJSONObject(finalI).getJSONArray("prices").getJSONObject(j).getString("date"))) {
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    if(cardItems.getId().equals(array.getJSONObject(finalI).getString("id"))) {
                                        prices.put("date", cardItems.getDate());
                                        prices.put("prix", cardItems.getcardMarketaverageSellPrice());
                                        array.getJSONObject(finalI).getJSONArray("prices").put(longeur, prices);
                                        jsonReader.writeToFile(obj.toString(),getContext());
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Actualisation finie", Toast.LENGTH_LONG).show();
            });
        }
        else{
            binding.button.setBackgroundColor(getResources().getColor(R.color.blue));
            binding.button.setClickable(false);
        }
        return root;
    }

  /*  private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("data.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}