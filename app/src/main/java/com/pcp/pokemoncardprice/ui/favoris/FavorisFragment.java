package com.pcp.pokemoncardprice.ui.favoris;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.databinding.FragmentFavorisBinding;
import com.pcp.pokemoncardprice.jsonreader.JsonReader;
import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.models.VerticalSpacingDecoration;
import com.pcp.pokemoncardprice.ui.card_info.CardsInfoViewModel;
import com.pcp.pokemoncardprice.ui.graph.GraphViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class FavorisFragment extends Fragment {

    private FavorisViewModel favorisViewModel;
    private GraphViewModel graphViewModel;
    private CardsInfoViewModel cardsInfoViewModel;
    private JsonReader jsonReader = new JsonReader();
    private float sumPrices;

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
        updateAveragePrice();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));

        favorisViewModel.getCardInfo().observe(getViewLifecycleOwner(), cardListItems -> {
            Collections.sort(cardListItems, CardItem.byDate);
            FavorisListItemAdapter adapter = new FavorisListItemAdapter(cardListItems);
            binding.searchView.setIconified(false);
            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });
            adapter.setClickListener((view, position, text) -> {
                //Récupérer la bonne valeur quand on selectionne
                String name = (String) text;
                for (int i = 0; i < adapter.originalDateSet.size(); i++) {
                    if (name.equals(adapter.originalDateSet.get(i).getName())) {
                        position = i;
                        break;
                    }
                }
                CardItem selectedCardItem = cardListItems.get(position);

                graphViewModel.getCardInfo(selectedCardItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    if (cardItem != null) {
                        binding.searchView.setQuery("",false);
                        binding.searchView.clearFocus();
                        binding.searchView.onActionViewCollapsed();
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
                                System.out.println("sumprices : "+sumPrices);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Actualisation finie", Toast.LENGTH_LONG).show();
                updateAveragePrice();
            });
        }
        else{
            binding.button.setBackgroundColor(getResources().getColor(R.color.blue));
            binding.button.setClickable(false);
        }

        return root;
    }

    private void updateAveragePrice() {
        sumPrices = 0;
        String jsonString = jsonReader.read(getActivity(), "data.json");
        JSONObject obj;
        try {
            obj = new JSONObject(jsonString);
            JSONArray array = obj.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                sumPrices += Float.valueOf(array.getJSONObject(i).getJSONArray("prices").getJSONObject( array.getJSONObject(i).getJSONArray("prices").length()-1).getString("prix"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.averagePrice.setText("Prix moyen : "+sumPrices+" €");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}