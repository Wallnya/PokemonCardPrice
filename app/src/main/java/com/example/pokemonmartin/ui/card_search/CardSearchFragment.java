package com.example.pokemonmartin.ui.card_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.pokemonmartin.R;

import com.example.pokemonmartin.databinding.FragmentCardsearchBinding;
import com.example.pokemonmartin.ui.card.CardsViewModel;

public class CardSearchFragment extends Fragment {

    private CardsViewModel cardsViewModel;
    private FragmentCardsearchBinding binding;

    public static CardSearchFragment newInstance() {
        return new CardSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cardsViewModel =
                new ViewModelProvider(requireActivity()).get(CardsViewModel.class);

        binding = FragmentCardsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(v -> {
            String playerTag = binding.playerTag.getText().toString();
            cardsViewModel.getCard(playerTag).observe(getViewLifecycleOwner(), cardItem -> {
                if (cardItem != null) {
                    Navigation.findNavController(root).navigate(R.id.action_cardSearchFragment_to_navigation_dashboard);
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}