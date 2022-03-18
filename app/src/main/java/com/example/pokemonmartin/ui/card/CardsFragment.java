package com.example.pokemonmartin.ui.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonmartin.databinding.FragmentCardsBinding;
import com.example.pokemonmartin.models.VerticalSpacingDecoration;


public class CardsFragment extends Fragment {

    private CardsViewModel rankingsViewModel;
    private FragmentCardsBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 24;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rankingsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);

        binding = FragmentCardsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));

        rankingsViewModel.getCard(CardsViewModel.playerTag).observe(getViewLifecycleOwner(), rankingItems -> {
            CardsListItemAdapter adapter = new CardsListItemAdapter(rankingItems);

            recyclerView.setAdapter(adapter);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
